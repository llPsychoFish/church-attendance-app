package com.example.myfirsttimer.ui.member;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myfirsttimer.data.AppDatabase;
import com.example.myfirsttimer.data.entity.Attendance;
import com.example.myfirsttimer.data.entity.Member;
import com.example.myfirsttimer.data.repository.AttendanceRepository;
import com.example.myfirsttimer.data.repository.MemberRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MemberViewModel extends AndroidViewModel {

    private final MemberRepository memberRepo;
    private final AttendanceRepository attendanceRepo;
    private final ExecutorService executor;

    private final MutableLiveData<List<Member>> searchResults = new MutableLiveData<>();
    private final MutableLiveData<Member> markedMember = new MutableLiveData<>();
    private final MutableLiveData<Boolean> notFound = new MutableLiveData<>();

    public MemberViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        memberRepo = new MemberRepository(db.memberDao());
        attendanceRepo = new AttendanceRepository(db.attendanceDao());
        executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Member>> getSearchResults() {
        return searchResults;
    }

    public LiveData<Member> getMarkedMember() {
        return markedMember;
    }

    public LiveData<Boolean> getNotFound() {
        return notFound;
    }

    public void search(String query) {
        executor.execute(() -> {
            List<Member> results = memberRepo.search(query);
            searchResults.postValue(results);
            notFound.postValue(results.isEmpty());
        });
    }

    public void markPresent(Member member, long usherId, String serviceType) {
        executor.execute(() -> {
            String today = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
            Attendance attendance = new Attendance();
            attendance.memberId = member.id;
            attendance.serviceDate = today;
            attendance.serviceType = serviceType;
            attendance.registeredBy = usherId;
            attendance.timestamp = System.currentTimeMillis();
            attendance.synced = false;
            attendanceRepo.insert(attendance);
            markedMember.postValue(member);
        });
    }
}
