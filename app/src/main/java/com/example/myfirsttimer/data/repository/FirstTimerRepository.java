package com.example.myfirsttimer.data.repository;

import com.example.myfirsttimer.data.dao.FirstTimerDao;
import com.example.myfirsttimer.data.dao.FirstTimerDepartmentDao;
import com.example.myfirsttimer.data.entity.FirstTimer;
import com.example.myfirsttimer.data.entity.FirstTimerDepartment;

import java.util.List;

public class FirstTimerRepository {
    private final FirstTimerDao dao;
    private final FirstTimerDepartmentDao deptDao;

    public FirstTimerRepository(FirstTimerDao dao, FirstTimerDepartmentDao deptDao) {
        this.dao = dao;
        this.deptDao = deptDao;
    }

    public long insert(FirstTimer firstTimer) {
        return dao.insert(firstTimer);
    }

    public void update(FirstTimer firstTimer) {
        dao.update(firstTimer);
    }

    public FirstTimer getById(long id) {
        return dao.getById(id);
    }

    public FirstTimer getByMember(long memberId) {
        return dao.getByMember(memberId);
    }

    public void updateFollowUpStatus(long id, String status) {
        dao.updateFollowUpStatus(id, status);
    }

    public List<FirstTimer> getAll() {
        return dao.getAll();
    }

    public long insertDepartment(FirstTimerDepartment dept) {
        return deptDao.insert(dept);
    }

    public List<FirstTimerDepartment> getDepartmentsByFirstTimer(long firstTimerId) {
        return deptDao.getByFirstTimer(firstTimerId);
    }
}
