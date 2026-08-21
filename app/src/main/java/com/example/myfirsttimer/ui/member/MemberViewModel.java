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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MemberViewModel extends AndroidViewModel {

    private final MemberRepository memberRepo;
    private final AttendanceRepository attendanceRepo;
    private final ExecutorService executor;

    private final MutableLiveData<List<Member>> suggestions = new MutableLiveData<>();
    private final MutableLiveData<Member> selectedMember = new MutableLiveData<>();
    private final MutableLiveData<Boolean> success = new MutableLiveData<>();

    public MemberViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        memberRepo = new MemberRepository(db.memberDao());
        attendanceRepo = new AttendanceRepository(db.attendanceDao());
        executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Member>> getSuggestions() {
        return suggestions;
    }

    public LiveData<Member> getSelectedMember() {
        return selectedMember;
    }

    public LiveData<Boolean> getSuccess() {
        return success;
    }

    public void searchSuggestions(String query) {
        executor.execute(() -> {
            List<Member> results = memberRepo.searchAutocomplete(query);
            suggestions.postValue(results);
        });
    }

    public void clearSuggestions() {
        suggestions.postValue(new ArrayList<>());
        selectedMember.postValue(null);
    }

    public void selectMember(Member member) {
        selectedMember.postValue(member);
        suggestions.postValue(new ArrayList<>());
    }

    public void submitRegistration(Member member, long usherId, String serviceType) {
        executor.execute(() -> {
            Member existing = null;
            if (member.id > 0) {
                existing = memberRepo.getById(member.id);
            } else if (member.phone != null && !member.phone.isEmpty()) {
                existing = memberRepo.getByPhone(member.phone);
            }

            long memberId;
            if (existing != null) {
                existing.surname = member.surname;
                existing.firstName = member.firstName;
                existing.phone = member.phone;
                existing.email = member.email;
                existing.courseOfStudy = member.courseOfStudy;
                existing.level = member.level;
                existing.hallHostel = member.hallHostel;
                existing.roomNo = member.roomNo;
                existing.dateOfBirth = member.dateOfBirth;
                memberRepo.update(existing);
                memberId = existing.id;
            } else {
                member.joinDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
                member.isFirstTimerOrigin = false;
                member.synced = false;
                memberId = memberRepo.insert(member);
            }

            String today = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
            Attendance attendance = new Attendance();
            attendance.memberId = memberId;
            attendance.serviceDate = today;
            attendance.serviceType = serviceType;
            attendance.registeredBy = usherId;
            attendance.timestamp = System.currentTimeMillis();
            attendance.synced = false;
            attendanceRepo.insert(attendance);

            success.postValue(true);
        });
    }
}