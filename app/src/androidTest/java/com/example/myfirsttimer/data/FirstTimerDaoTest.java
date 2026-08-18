package com.example.myfirsttimer.data;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.myfirsttimer.data.dao.FirstTimerDao;
import com.example.myfirsttimer.data.dao.MemberDao;
import com.example.myfirsttimer.data.entity.FirstTimer;
import com.example.myfirsttimer.data.entity.Member;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FirstTimerDaoTest {
    private AppDatabase db;
    private FirstTimerDao dao;
    private long memberId;

    @Before
    public void setUp() {
        db = Room.inMemoryDatabaseBuilder(
                        ApplicationProvider.getApplicationContext(), AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        dao = db.firstTimerDao();
        Member m = new Member();
        m.surname = "New";
        m.firstName = "Visitor";
        m.phone = "0987654321";
        m.isFirstTimerOrigin = true;
        memberId = db.memberDao().insert(m);
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void insertAndQuery_roundTrips() {
        FirstTimer ft = new FirstTimer();
        ft.memberId = memberId;
        ft.invitedBy = "Jane";
        ft.isBornAgain = true;
        ft.wantsMembership = true;
        ft.prayerRequest = "Thank you";
        ft.followUpStatus = "New";
        long id = dao.insert(ft);

        FirstTimer loaded = dao.getById(id);
        assertNotNull(loaded);
        assertEquals("Jane", loaded.invitedBy);
        assertEquals("New", loaded.followUpStatus);
    }

    @Test
    public void getByMember_returnsLinkedRecord() {
        FirstTimer ft = new FirstTimer();
        ft.memberId = memberId;
        ft.followUpStatus = "New";
        dao.insert(ft);

        FirstTimer loaded = dao.getByMember(memberId);
        assertNotNull(loaded);
        assertEquals(memberId, loaded.memberId);
    }

    @Test
    public void updateFollowUpStatus_persists() {
        FirstTimer ft = new FirstTimer();
        ft.memberId = memberId;
        ft.followUpStatus = "New";
        long id = dao.insert(ft);

        dao.updateFollowUpStatus(id, "Contacted");
        assertEquals("Contacted", dao.getById(id).followUpStatus);
    }
}
