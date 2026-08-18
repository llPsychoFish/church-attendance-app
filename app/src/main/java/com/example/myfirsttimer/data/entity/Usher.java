package com.example.myfirsttimer.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ushers")
public class Usher {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;
    public String username;           // unique
    public String pinHash;            // hashed PIN/password (BCrypt or MessageDigest)

    public String createdAt;          // ISO timestamp, set on registration
}
