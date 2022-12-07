package com.example.taskmaster.models;

public class Task {
    String title;
    String body;
    State state;

    public String getTitle() {
        return this.title;
    }

    public enum State {
        IN_PROGRESS,
        NEW,
        ASSIGNED,
        COMPLETE
    }

    public Task(String title, String body, State state) {
        this.title = title;
        this.body = body;
        this.state = state;
    }
}
