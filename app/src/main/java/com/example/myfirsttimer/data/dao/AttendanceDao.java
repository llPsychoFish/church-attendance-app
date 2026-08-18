package com.example.myfirsttimer.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myfirsttimer.data.entity.Attendance;

import java.util.List;

@Dao
public interface AttendanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Attendance attendance);

    @Query("SELECT * FROM attendance WHERE id = :id LIMIT 1")
    Attendance getById(long id);

    @Query("SELECT * FROM attendance WHERE memberId = :memberId ORDER BY serviceDate DESC")
    List<Attendance> getByMember(long memberId);

    @Query("SELECT * FROM attendance WHERE serviceDate = :date ORDER BY serviceType ASC")
    List<Attendance> getByDate(String date);

    @Query("SELECT * FROM attendance WHERE serviceDate = :date AND serviceType = :type")
    List<Attendance> getByDateAndService(String date, String type);

    @Query("SELECT COUNT(*) FROM attendance WHERE serviceDate = :date AND serviceType = :type")
    int countByDateAndService(String date, String type);

    @Query("SELECT * FROM attendance ORDER BY serviceDate DESC")
    List<Attendance> getAll();
}
