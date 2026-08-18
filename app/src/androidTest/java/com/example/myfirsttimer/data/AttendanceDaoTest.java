package com.example.myfirsttimer.data;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.myfirsttimer.data.dao.AttendanceDao;
import com.example.myfirsttimer.data.dao.MemberDao;
import com.example.myfirsttimer.data.entity.Attendance;
import com.example.myfirsttimer.data.entity.Member;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AttendanceDaoTest {
    private AppDatabase db;
    private AttendanceDao dao;
    private long memberId;

    @Before
    public void setUp() {
        db = Room.inMemoryDatabaseBuilder(
                        ApplicationProvider.getApplicationContext(), AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        dao = db.attendanceDao();
        Member m = new Member();
        m.surname = "Test";
        m.firstName = "Member";
        m.phone = "0123456789";
        memberId = db.memberDao().insert(m);
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void insertAndQuery_roundTrips() {
        Attendance a = new Attendance();
        a.memberId = memberId;
        a.serviceDate = "2026-08-16";
        a.serviceType = "SUN";
        a.registeredBy = 1;
        a.timestamp = 1000L;
        long id = dao.insert(a);

        Attendance loaded = dao.getById(id);
        assertEquals("2026-08-16", loaded.serviceDate);
        assertEquals("SUN", loaded.serviceType);
        assertEquals(memberId, loaded.memberId);
    }

    @Test
    public void countByDateAndService_countsCorrectly() {
        Attendance a1 = new Attendance();
        a1.memberId = memberId;
        a1.serviceDate = "2026-08-16";
        a1.serviceType = "SUN";
        a1.registeredBy = 1;
        a1.timestamp = 1L;
        dao.insert(a1);

        Attendance a2 = new Attendance();
        a2.memberId = memberId;
        a2.serviceDate = "2026-08-16";
        a2.serviceType = "SUN";
        a2.registeredBy = 1;
        a2.timestamp = 2L;
        dao.insert(a2);

        assertEquals(2, dao.countByDateAndService("2026-08-16", "SUN"));
        assertEquals(0, dao.countByDateAndService("2026-08-16", "WED"));
    }
}
