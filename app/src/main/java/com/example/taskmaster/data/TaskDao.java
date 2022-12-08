package com.example.taskmaster.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.taskmaster.models.Task;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    List<Task> getAllTasks();

    @Query("SELECT * FROM Task WHERE state = :state")
    Single<List<Task>> getAllTasksByStatus(Task.State state);

    @Insert
    Completable insertAllTasks(Task... tasks);

    @Insert
    void insertTask(Task task);

    @Delete
    void deleteTask(Task task);
}
