package com.example.myfirsttimer.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "first_timers",
        foreignKeys = @ForeignKey(
                entity = Member.class,
                parentColumns = "id",
                childColumns = "memberId"
        )
)
public class FirstTimer {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public long memberId;             // FK -> Member

    public String invitedBy;          // optional, free text
    public Boolean isBornAgain;       // nullable
    public Boolean speaksInTongues;   // nullable
    public Boolean wantsMembership;   // nullable
    public String prayerRequest;      // optional, multi-line

    public String followUpStatus;     // New / Contacted / Attended again / Integrated
    public String assignedTo;         // optional, future use
}
