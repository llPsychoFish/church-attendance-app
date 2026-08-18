package com.example.myfirsttimer.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "members")
public class Member {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String surname;
    public String firstName;
    public String phone;
    public String email;              // optional
    public String courseOfStudy;      // optional
    public String level;              // optional
    public String hallHostel;         // optional
    public String roomNo;             // optional
    public String dateOfBirth;        // optional, ISO date string

    public String joinDate;           // set on first registration
    public boolean isFirstTimerOrigin;
    public String cellZone;           // optional, future follow-up use

    public boolean synced;            // used from Phase 8 onward
}
