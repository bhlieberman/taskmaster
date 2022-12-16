package com.slothrop.taskmaster.models;

public class Task {
    public int id;
    public String name;
    public String description;
    public State state;

    public String getTitle() {
        return this.name;
    }

    public enum State {
        IN_PROGRESS,
        NEW,
        ASSIGNED,
        COMPLETE
    }

    public Task(int id, String title, String description, State state) {
        this.id = id;
        this.name = title;
        this.description = description;
        this.state = state;
    }
}
