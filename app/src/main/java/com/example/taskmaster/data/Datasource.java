package com.example.taskmaster.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.taskmaster.models.Task;

@Database(entities={Task.class}, version=1)
public abstract class Datasource extends RoomDatabase {
    public abstract TaskDao taskDao();
}
