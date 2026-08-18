package com.example.myfirsttimer.data.repository;

import com.example.myfirsttimer.data.dao.UsherDao;
import com.example.myfirsttimer.data.entity.Usher;

public class UsherRepository {
    private final UsherDao dao;

    public UsherRepository(UsherDao dao) {
        this.dao = dao;
    }

    public long insert(Usher usher) {
        return dao.insert(usher);
    }

    public Usher getByUsername(String username) {
        return dao.getByUsername(username);
    }

    public int countByUsername(String username) {
        return dao.countByUsername(username);
    }

    public void deleteByUsername(String username) {
        dao.deleteByUsername(username);
    }
}
