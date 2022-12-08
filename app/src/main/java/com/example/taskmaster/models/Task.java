package com.example.taskmaster.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task {
    @PrimaryKey
    public int id;
    @ColumnInfo(name= "title")
    public String title;
    @ColumnInfo(name = "body")
    public String body;
    @ColumnInfo(name = "state")
    public State state;

    public String getTitle() {
        return this.title;
    }

    public enum State {
        IN_PROGRESS,
        NEW,
        ASSIGNED,
        COMPLETE
    }

    public Task(int id, String title, String body, State state) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.state = state;
    }
}
