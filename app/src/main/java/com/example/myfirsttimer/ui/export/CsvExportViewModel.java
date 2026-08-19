package com.example.myfirsttimer.ui.export;

import android.app.Application;
import android.content.ContentValues;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CsvExportViewModel extends AndroidViewModel {

    private final AttendanceRepository attendanceRepo;
    private final MemberRepository memberRepo;
    private final FirstTimerRepository firstTimerRepo;
    private final ExecutorService executor;

    private final MutableLiveData<ExportResult> exportResult = new MutableLiveData<>();

    public CsvExportViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        attendanceRepo = new AttendanceRepository(db.attendanceDao());
        memberRepo = new MemberRepository(db.memberDao());
        firstTimerRepo = new FirstTimerRepository(db.firstTimerDao(), db.firstTimerDepartmentDao());
        executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<ExportResult> getExportResult() {
        return exportResult;
    }

    public void exportAttendance(String date, String serviceType) {
        executor.execute(() -> {
            try {
                List<Attendance> records;
                if (serviceType != null && !serviceType.isEmpty()) {
                    records = attendanceRepo.getByDateAndService(date, serviceType);
                } else {
                    records = attendanceRepo.getByDate(date);
                }

                StringBuilder sb = new StringBuilder();
                sb.append("S/N,Full Name,Phone,Email,Course,Level,Hall/Hostel,Room No,Service Type,Date\n");

                int sn = 1;
                for (Attendance a : records) {
                    Member m = memberRepo.getById(a.memberId);
                    if (m == null) continue;
                    sb.append(sn++).append(",");
                    sb.append(escape(m.surname + " " + m.firstName)).append(",");
                    sb.append(escape(m.phone)).append(",");
                    sb.append(escape(m.email)).append(",");
                    sb.append(escape(m.courseOfStudy)).append(",");
                    sb.append(escape(m.level)).append(",");
                    sb.append(escape(m.hallHostel)).append(",");
                    sb.append(escape(m.roomNo)).append(",");
                    sb.append(escape(a.serviceType)).append(",");
                    sb.append(escape(a.serviceDate)).append("\n");
                }

                String filename = "attendance_" + date
                        + (serviceType != null && !serviceType.isEmpty() ? "_" + serviceType : "")
                        + ".csv";
                File file = saveToCache(filename, sb.toString());
                exportResult.postValue(new ExportResult(true, file, filename, "Attendance CSV exported"));
            } catch (Exception e) {
                exportResult.postValue(new ExportResult(false, null, null, "Export failed: " + e.getMessage()));
            }
        });
    }

    public void exportFirstTimers(String date) {
        executor.execute(() -> {
            try {
                List<FirstTimer> allFirstTimers = firstTimerRepo.getAll();

                StringBuilder sb = new StringBuilder();
                sb.append("S/N,Full Name,Phone,Email,Course,Level,Hall/Hostel,Room No,");
                sb.append("Invited By,Born Again,Speaks in Tongues,Wants Membership,");
                sb.append("Prayer Request,Departments,Follow-Up Status,Date Registered\n");

                int sn = 1;
                for (FirstTimer ft : allFirstTimers) {
                    Member m = memberRepo.getById(ft.memberId);
                    if (m == null) continue;

                    boolean registeredOnDate = false;
                    List<Attendance> attList = attendanceRepo.getByMember(ft.memberId);
                    for (Attendance a : attList) {
                        if (date.equals(a.serviceDate)) {
                            registeredOnDate = true;
                            break;
                        }
                    }
                    if (!registeredOnDate) continue;

                    List<FirstTimerDepartment> depts = firstTimerRepo.getDepartmentsByFirstTimer(ft.id);
                    StringBuilder deptStr = new StringBuilder();
                    for (int i = 0; i < depts.size(); i++) {
                        if (i > 0) deptStr.append("; ");
                        deptStr.append(depts.get(i).department);
                    }

                    sb.append(sn++).append(",");
                    sb.append(escape(m.surname + " " + m.firstName)).append(",");
                    sb.append(escape(m.phone)).append(",");
                    sb.append(escape(m.email)).append(",");
                    sb.append(escape(m.courseOfStudy)).append(",");
                    sb.append(escape(m.level)).append(",");
                    sb.append(escape(m.hallHostel)).append(",");
                    sb.append(escape(m.roomNo)).append(",");
                    sb.append(escape(ft.invitedBy)).append(",");
                    sb.append(escape(boolToYesNo(ft.isBornAgain))).append(",");
                    sb.append(escape(boolToYesNo(ft.speaksInTongues))).append(",");
                    sb.append(escape(boolToYesNo(ft.wantsMembership))).append(",");
                    sb.append(escape(ft.prayerRequest)).append(",");
                    sb.append(escape(deptStr.toString())).append(",");
                    sb.append(escape(ft.followUpStatus)).append(",");
                    sb.append(escape(date)).append("\n");
                }

                String filename = "first_timers_" + date + ".csv";
                File file = saveToCache(filename, sb.toString());
                exportResult.postValue(new ExportResult(true, file, filename, "First Timers CSV exported"));
            } catch (Exception e) {
                exportResult.postValue(new ExportResult(false, null, null, "Export failed: " + e.getMessage()));
            }
        });
    }

    public void exportFullAttendance() {
        executor.execute(() -> {
            try {
                List<Attendance> records = attendanceRepo.getAll();

                StringBuilder sb = new StringBuilder();
                sb.append("S/N,Full Name,Phone,Email,Course,Level,Hall/Hostel,Room No,Service Type,Date\n");

                int sn = 1;
                for (Attendance a : records) {
                    Member m = memberRepo.getById(a.memberId);
                    if (m == null) continue;
                    sb.append(sn++).append(",");
                    sb.append(escape(m.surname + " " + m.firstName)).append(",");
                    sb.append(escape(m.phone)).append(",");
                    sb.append(escape(m.email)).append(",");
                    sb.append(escape(m.courseOfStudy)).append(",");
                    sb.append(escape(m.level)).append(",");
                    sb.append(escape(m.hallHostel)).append(",");
                    sb.append(escape(m.roomNo)).append(",");
                    sb.append(escape(a.serviceType)).append(",");
                    sb.append(escape(a.serviceDate)).append("\n");
                }

                String filename = "attendance_full.csv";
                File file = saveToCache(filename, sb.toString());
                exportResult.postValue(new ExportResult(true, file, filename, "Full attendance CSV exported"));
            } catch (Exception e) {
                exportResult.postValue(new ExportResult(false, null, null, "Export failed: " + e.getMessage()));
            }
        });
    }

    private File saveToCache(String filename, String content) throws IOException {
        File dir = new File(getApplication().getCacheDir(), "exports");
        if (!dir.exists()) dir.mkdirs();
        File file = new File(dir, filename);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(content.getBytes("UTF-8"));
        fos.close();
        return file;
    }

    private String escape(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    private String boolToYesNo(Boolean value) {
        if (value == null) return "";
        return value ? "Yes" : "No";
    }

    public static class ExportResult {
        public final boolean success;
        public final File file;
        public final String filename;
        public final String message;

        public ExportResult(boolean success, File file, String filename, String message) {
            this.success = success;
            this.file = file;
            this.filename = filename;
            this.message = message;
        }
    }
}
