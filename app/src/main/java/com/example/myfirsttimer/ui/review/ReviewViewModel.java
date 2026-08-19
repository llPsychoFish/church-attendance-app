package com.example.myfirsttimer.ui.review;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myfirsttimer.data.AppDatabase;
import com.example.myfirsttimer.data.entity.Attendance;
import com.example.myfirsttimer.data.entity.FirstTimer;
import com.example.myfirsttimer.data.entity.FirstTimerDepartment;
import com.example.myfirsttimer.data.entity.Member;
import com.example.myfirsttimer.data.repository.AttendanceRepository;
import com.example.myfirsttimer.data.repository.FirstTimerRepository;
import com.example.myfirsttimer.data.repository.MemberRepository;
import com.example.myfirsttimer.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReviewViewModel extends AndroidViewModel {

    private final AttendanceRepository attendanceRepo;
    private final MemberRepository memberRepo;
    private final FirstTimerRepository firstTimerRepo;
    private final ExecutorService executor;

    private final MutableLiveData<Map<String, Integer>> serviceCounts = new MutableLiveData<>();
    private final MutableLiveData<List<AttendanceWithMember>> attendanceList = new MutableLiveData<>();
    private final MutableLiveData<List<FirstTimerWithMember>> firstTimerList = new MutableLiveData<>();

    public ReviewViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        attendanceRepo = new AttendanceRepository(db.attendanceDao());
        memberRepo = new MemberRepository(db.memberDao());
        firstTimerRepo = new FirstTimerRepository(db.firstTimerDao(), db.firstTimerDepartmentDao());
        executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<Map<String, Integer>> getServiceCounts() {
        return serviceCounts;
    }

    public LiveData<List<AttendanceWithMember>> getAttendanceList() {
        return attendanceList;
    }

    public LiveData<List<FirstTimerWithMember>> getFirstTimerList() {
        return firstTimerList;
    }

    public void loadDataForDate(String date) {
        executor.execute(() -> {
            Map<String, Integer> counts = new HashMap<>();
            for (String serviceType : Constants.SERVICE_TYPES) {
                counts.put(serviceType, attendanceRepo.countByDateAndService(date, serviceType));
            }
            serviceCounts.postValue(counts);

            List<Attendance> records = attendanceRepo.getByDate(date);
            List<AttendanceWithMember> attendanceWithMembers = new ArrayList<>();
            for (Attendance a : records) {
                Member m = memberRepo.getById(a.memberId);
                if (m != null) {
                    attendanceWithMembers.add(new AttendanceWithMember(a, m));
                }
            }
            attendanceList.postValue(attendanceWithMembers);

            List<FirstTimer> allFirstTimers = firstTimerRepo.getAll();
            List<FirstTimerWithMember> firstTimerWithMembers = new ArrayList<>();
            for (FirstTimer ft : allFirstTimers) {
                Member m = memberRepo.getById(ft.memberId);
                if (m != null) {
                    List<Attendance> ftAttendance = attendanceRepo.getByMember(ft.memberId);
                    boolean registeredOnDate = false;
                    for (Attendance a : ftAttendance) {
                        if (date.equals(a.serviceDate)) {
                            registeredOnDate = true;
                            break;
                        }
                    }
                    if (registeredOnDate) {
                        List<FirstTimerDepartment> depts = firstTimerRepo.getDepartmentsByFirstTimer(ft.id);
                        firstTimerWithMembers.add(new FirstTimerWithMember(ft, m, depts));
                    }
                }
            }
            firstTimerList.postValue(firstTimerWithMembers);
        });
    }

    public void updateFollowUpStatus(long firstTimerId, String status) {
        executor.execute(() -> firstTimerRepo.updateFollowUpStatus(firstTimerId, status));
    }

    public static class AttendanceWithMember {
        public final Attendance attendance;
        public final Member member;

        public AttendanceWithMember(Attendance attendance, Member member) {
            this.attendance = attendance;
            this.member = member;
        }
    }

    public static class FirstTimerWithMember {
        public final FirstTimer firstTimer;
        public final Member member;
        public final List<FirstTimerDepartment> departments;

        public FirstTimerWithMember(FirstTimer firstTimer, Member member, List<FirstTimerDepartment> departments) {
            this.firstTimer = firstTimer;
            this.member = member;
            this.departments = departments;
        }
    }
}
