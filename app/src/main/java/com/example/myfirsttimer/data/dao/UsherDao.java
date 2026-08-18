package com.example.myfirsttimer.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myfirsttimer.data.entity.Usher;

import java.util.List;

@Dao
public interface UsherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Usher usher);

    @Update
    void update(Usher usher);

    @Query("DELETE FROM ushers WHERE username = :username")
    void deleteByUsername(String username);

    @Query("SELECT COUNT(*) FROM ushers WHERE username = :username")
    int countByUsername(String username);

    @Query("SELECT * FROM ushers WHERE username = :username LIMIT 1")
    Usher getByUsername(String username);

    @Query("SELECT * FROM ushers WHERE id = :id LIMIT 1")
    Usher getById(long id);

    @Query("SELECT * FROM ushers ORDER BY name ASC")
    List<Usher> getAll();
}
