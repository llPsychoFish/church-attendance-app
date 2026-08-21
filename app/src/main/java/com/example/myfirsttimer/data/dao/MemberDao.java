package com.example.myfirsttimer.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myfirsttimer.data.entity.Member;

import java.util.List;

@Dao
public interface MemberDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Member member);

    @Update
    void update(Member member);

    @Query("SELECT * FROM members WHERE id = :id LIMIT 1")
    Member getById(long id);

    @Query("SELECT * FROM members WHERE phone = :phone LIMIT 1")
    Member getByPhone(String phone);

    @Query("SELECT * FROM members WHERE surname LIKE :q OR firstName LIKE :q OR phone LIKE :q ORDER BY surname ASC")
    List<Member> search(String q);

    @Query("SELECT * FROM members WHERE surname LIKE '%' || :q || '%' OR firstName LIKE '%' || :q || '%' OR phone LIKE '%' || :q || '%' ORDER BY surname ASC LIMIT 10")
    List<Member> searchAutocomplete(String q);

    @Query("SELECT * FROM members ORDER BY surname ASC")
    List<Member> getAll();
}
