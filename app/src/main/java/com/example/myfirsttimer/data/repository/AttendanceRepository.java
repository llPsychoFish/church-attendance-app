package com.example.myfirsttimer.data.repository;

import com.example.myfirsttimer.data.dao.AttendanceDao;
import com.example.myfirsttimer.data.entity.Attendance;

import java.util.List;

public class AttendanceRepository {
    private final AttendanceDao dao;

    public AttendanceRepository(AttendanceDao dao) {
        this.dao = dao;
    }

    public long insert(Attendance attendance) {
        return dao.insert(attendance);
    }

    public Attendance getById(long id) {
        return dao.getById(id);
    }

    public List<Attendance> getByMember(long memberId) {
        return dao.getByMember(memberId);
    }

    public List<Attendance> getByDate(String date) {
        return dao.getByDate(date);
    }

    public List<Attendance> getByDateAndService(String date, String type) {
        return dao.getByDateAndService(date, type);
    }

    public int countByDateAndService(String date, String type) {
        return dao.countByDateAndService(date, type);
    }

    public List<Attendance> getAll() {
        return dao.getAll();
    }
}
