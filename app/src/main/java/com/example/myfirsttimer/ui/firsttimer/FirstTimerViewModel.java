package com.example.myfirsttimer.ui.firsttimer;

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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FirstTimerViewModel extends AndroidViewModel {

    private final MemberRepository memberRepo;
    private final FirstTimerRepository firstTimerRepo;
    private final AttendanceRepository attendanceRepo;
    private final ExecutorService executor;

    private final MutableLiveData<Boolean> success = new MutableLiveData<>();

    public FirstTimerViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        memberRepo = new MemberRepository(db.memberDao());
        firstTimerRepo = new FirstTimerRepository(db.firstTimerDao(), db.firstTimerDepartmentDao());
        attendanceRepo = new AttendanceRepository(db.attendanceDao());
        executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<Boolean> getSuccess() {
        return success;
    }

    public void registerFirstTimer(String surname, String firstName, String phone,
                                    String email, String courseOfStudy, String level,
                                    String hallHostel, String roomNo, String dateOfBirth,
                                    String invitedBy, Boolean isBornAgain,
                                    Boolean speaksInTongues, Boolean wantsMembership,
                                    String prayerRequest, List<String> departments,
                                    long usherId, String serviceType) {
        executor.execute(() -> {
            // 1. Create Member
            Member member = new Member();
            member.surname = surname;
            member.firstName = firstName;
            member.phone = phone;
            member.email = email;
            member.courseOfStudy = courseOfStudy;
            member.level = level;
            member.hallHostel = hallHostel;
            member.roomNo = roomNo;
            member.dateOfBirth = dateOfBirth;
            member.joinDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
            member.isFirstTimerOrigin = true;
            member.synced = false;
            long memberId = memberRepo.insert(member);

            // 2. Create FirstTimer
            FirstTimer firstTimer = new FirstTimer();
            firstTimer.memberId = memberId;
            firstTimer.invitedBy = invitedBy;
            firstTimer.isBornAgain = isBornAgain;
            firstTimer.speaksInTongues = speaksInTongues;
            firstTimer.wantsMembership = wantsMembership;
            firstTimer.prayerRequest = prayerRequest;
            firstTimer.followUpStatus = Constants.FOLLOW_UP_NEW;
            long firstTimerId = firstTimerRepo.insert(firstTimer);

            // 3. Create Department entries
            if (departments != null) {
                for (String dept : departments) {
                    FirstTimerDepartment ftd = new FirstTimerDepartment();
                    ftd.firstTimerId = firstTimerId;
                    ftd.department = dept;
                    firstTimerRepo.insertDepartment(ftd);
                }
            }

            // 4. Create Attendance
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
