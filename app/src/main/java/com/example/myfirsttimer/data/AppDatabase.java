package com.example.myfirsttimer.data;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.myfirsttimer.data.dao.AttendanceDao;
import com.example.myfirsttimer.data.dao.FirstTimerDao;
import com.example.myfirsttimer.data.dao.FirstTimerDepartmentDao;
import com.example.myfirsttimer.data.dao.MemberDao;
import com.example.myfirsttimer.data.dao.UsherDao;
import com.example.myfirsttimer.data.entity.Attendance;
import com.example.myfirsttimer.data.entity.FirstTimer;
import com.example.myfirsttimer.data.entity.FirstTimerDepartment;
import com.example.myfirsttimer.data.entity.Member;
import com.example.myfirsttimer.data.entity.Usher;

@Database(entities = {
        Usher.class,
        Member.class,
        Attendance.class,
        FirstTimer.class,
        FirstTimerDepartment.class
}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UsherDao usherDao();
    public abstract MemberDao memberDao();
    public abstract AttendanceDao attendanceDao();
    public abstract FirstTimerDao firstTimerDao();
    public abstract FirstTimerDepartmentDao firstTimerDepartmentDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "church_attendance.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
