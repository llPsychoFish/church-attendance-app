package com.example.myfirsttimer.ui.auth;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myfirsttimer.data.AppDatabase;
import com.example.myfirsttimer.data.entity.Usher;
import com.example.myfirsttimer.data.repository.UsherRepository;
import com.example.myfirsttimer.util.PasswordHasher;
import com.example.myfirsttimer.util.SessionManager;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AuthViewModel extends AndroidViewModel {
    private final UsherRepository repo;
    private final SessionManager session;
    private final Executor executor = Executors.newSingleThreadExecutor();

    private final MutableLiveData<AuthResult> authResult = new MutableLiveData<>();
    private final MutableLiveData<Boolean> resetDone = new MutableLiveData<>();

    public LiveData<AuthResult> getAuthResult() {
        return authResult;
    }

    public LiveData<Boolean> getResetDone() {
        return resetDone;
    }

    public AuthViewModel(Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        repo = new UsherRepository(db.usherDao());
        session = new SessionManager(application);
    }

    public void register(String name, String username, String pin) {
        executor.execute(() -> {
            if (repo.countByUsername(username) > 0) {
                authResult.postValue(new AuthResult(false, "Username already taken"));
                return;
            }
            Usher u = new Usher();
            u.name = name;
            u.username = username;
            u.pinHash = PasswordHasher.hash(pin);
            u.createdAt = String.valueOf(System.currentTimeMillis());
            long id = repo.insert(u);
            session.setLoggedInUsherId(id);
            authResult.postValue(new AuthResult(true, "Account created"));
        });
    }

    public void login(String username, String pin) {
        executor.execute(() -> {
            Usher u = repo.getByUsername(username);
            if (u == null) {
                authResult.postValue(new AuthResult(false, "Usher not found"));
                return;
            }
            if (!PasswordHasher.verify(pin, u.pinHash)) {
                authResult.postValue(new AuthResult(false, "Incorrect PIN"));
                return;
            }
            session.setLoggedInUsherId(u.id);
            authResult.postValue(new AuthResult(true, "Welcome back"));
        });
    }

    public void resetCredentials(String username) {
        executor.execute(() -> {
            repo.deleteByUsername(username);
            resetDone.postValue(true);
        });
    }

    public static class AuthResult {
        public final boolean success;
        public final String message;

        AuthResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
    }
}
