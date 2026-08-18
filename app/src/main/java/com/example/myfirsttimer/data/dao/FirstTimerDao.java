package com.example.myfirsttimer.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myfirsttimer.data.entity.FirstTimer;

import java.util.List;

@Dao
public interface FirstTimerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(FirstTimer firstTimer);

    @Update
    void update(FirstTimer firstTimer);

    @Query("SELECT * FROM first_timers WHERE id = :id LIMIT 1")
    FirstTimer getById(long id);

    @Query("SELECT * FROM first_timers WHERE memberId = :memberId LIMIT 1")
    FirstTimer getByMember(long memberId);

    @Query("UPDATE first_timers SET followUpStatus = :status WHERE id = :id")
    void updateFollowUpStatus(long id, String status);

    @Query("SELECT * FROM first_timers ORDER BY followUpStatus ASC")
    List<FirstTimer> getAll();
}
