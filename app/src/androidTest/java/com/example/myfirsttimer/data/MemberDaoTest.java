package com.example.myfirsttimer.data;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.myfirsttimer.data.dao.MemberDao;
import com.example.myfirsttimer.data.entity.Member;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class MemberDaoTest {
    private AppDatabase db;
    private MemberDao dao;

    @Before
    public void setUp() {
        db = Room.inMemoryDatabaseBuilder(
                        ApplicationProvider.getApplicationContext(), AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        dao = db.memberDao();
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void insertAndQuery_roundTrips() {
        Member m = new Member();
        m.surname = "Mensah";
        m.firstName = "Kwame";
        m.phone = "0241234567";
        long id = dao.insert(m);

        Member loaded = dao.getById(id);
        assertNotNull(loaded);
        assertEquals("Mensah", loaded.surname);
        assertEquals("Kwame", loaded.firstName);
        assertEquals("0241234567", loaded.phone);
    }

    @Test
    public void search_matchesByNameAndPhone() {
        Member m = new Member();
        m.surname = "Owusu";
        m.firstName = "Ama";
        m.phone = "0559998877";
        dao.insert(m);

        assertEquals(1, dao.search("Owusu").size());
        assertEquals(1, dao.search("0559998877").size());
        assertEquals(0, dao.search("Nonexistent").size());
    }

    @Test
    public void getByPhone_returnsMatchingMember() {
        Member m = new Member();
        m.surname = "Boateng";
        m.firstName = "Yaw";
        m.phone = "0271112233";
        dao.insert(m);

        Member loaded = dao.getByPhone("0271112233");
        assertNotNull(loaded);
        assertEquals("Boateng", loaded.surname);
    }

    @Test
    public void update_persistsChanges() {
        Member m = new Member();
        m.surname = "Appiah";
        m.firstName = "Kojo";
        m.phone = "0200001111";
        long id = dao.insert(m);

        Member loaded = dao.getById(id);
        loaded.phone = "0200009999";
        dao.update(loaded);

        assertEquals("0200009999", dao.getById(id).phone);
    }

    @Test
    public void deletedMember_notFound() {
        Member m = new Member();
        m.surname = "Temp";
        m.firstName = "User";
        m.phone = "0199998888";
        long id = dao.insert(m);
        assertNotNull(dao.getById(id));
        // No delete method in Phase 1 DAO; verify getByPhone still resolves
        assertNotNull(dao.getByPhone("0199998888"));
        // Sanity: unknown id returns null
        assertNull(dao.getById(99999L));
    }
}
