package com.example.myfirsttimer.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "first_timer_departments",
        foreignKeys = @ForeignKey(
                entity = FirstTimer.class,
                parentColumns = "id",
                childColumns = "firstTimerId"
        )
)
public class FirstTimerDepartment {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public long firstTimerId;         // FK -> FirstTimer
    public String department;         // Ushering / Choir / Technical / Creative Art / Media & New Media / Innovations
}
