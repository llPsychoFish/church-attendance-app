package com.example.myfirsttimer.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "attendance")
public class Attendance {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public long memberId;             // FK -> Member
    public String serviceDate;
    public String serviceType;        // SUN / WED / FRI / CELL
    public long registeredBy;         // FK -> Usher
    public long timestamp;
    public boolean synced;            // used from Phase 8 onward
}
