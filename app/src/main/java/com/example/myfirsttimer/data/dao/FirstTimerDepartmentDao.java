package com.example.myfirsttimer.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myfirsttimer.data.entity.FirstTimerDepartment;

import java.util.List;

@Dao
public interface FirstTimerDepartmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(FirstTimerDepartment department);

    @Query("SELECT * FROM first_timer_departments WHERE firstTimerId = :firstTimerId")
    List<FirstTimerDepartment> getByFirstTimer(long firstTimerId);

    @Query("SELECT * FROM first_timer_departments")
    List<FirstTimerDepartment> getAll();
}
