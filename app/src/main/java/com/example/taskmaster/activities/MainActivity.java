package com.example.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.example.taskmaster.R;
import com.example.taskmaster.adapters.TaskRecyclerViewViewAdapter;
import com.example.taskmaster.data.Datasource;
import com.example.taskmaster.data.TaskDao;
import com.example.taskmaster.models.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {

    public static final String TASK_NAME_TAG = "taskName";
    Datasource db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = Room.databaseBuilder(getApplicationContext(),
                Datasource.class,
                "datasource")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        setUpRecyclerView();


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (prefs.contains("username")) {
            TextView loggedInUser = this.findViewById(R.id.loggedInUser);
            String sb = prefs.getString("username", null) +
                    "'s tasks";
            loggedInUser.setText(sb);
        }

        goToAddTasks();
//        goToAllTasks();
        goToSettings();
//        goToTaskDetails();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpRecyclerView();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (prefs.contains("username")) {
            TextView loggedInUser = this.findViewById(R.id.loggedInUser);
            String sb = prefs.getString("username", null) +
                    "'s tasks";
            loggedInUser.setText(sb);
        }
    }

    public void setUpRecyclerView() {
        TaskDao dao = db.taskDao();
        List<Task> tasks = dao.getAllTasks();

        RecyclerView taskRV = findViewById(R.id.TaskRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        taskRV.setLayoutManager(layoutManager);
        TaskRecyclerViewViewAdapter adapter = new TaskRecyclerViewViewAdapter(tasks, this);
        taskRV.setAdapter(adapter);
    }

//    public void goToAllTasks() {
//        MainActivity
//                .this
//                .findViewById(R.id.MainActivityAllTasksButton)
//                .setOnClickListener(view -> {
//                    Intent goToAllTasksActivity = new Intent(MainActivity.this, AllTasksActivity.class);
//                    startActivity(goToAllTasksActivity);
//                });
//    }

    public void goToAddTasks() {
        MainActivity
                .this
                .findViewById(R.id.MainActivityAddTaskButton).setOnClickListener(v -> {
                    Intent goToAddTaskActivity = new Intent(MainActivity.this, AddTaskActivity.class);
                    startActivity(goToAddTaskActivity);
                });
    }

//    public void goToTaskDetails() {
//        MainActivity
//                .this
//                .findViewById(R.id.MainTaskDetailsButton)
//                .setOnClickListener(view -> {
//                    Intent goToTaskDetailsActivity = new Intent(MainActivity.this, TaskDetailActivity.class);
//                    startActivity(goToTaskDetailsActivity);
//                });
//    }

    public void goToSettings() {
        this.findViewById(R.id.SettingsButton)
                .setOnClickListener(view -> {
                    Intent goToSettingsActivity = new Intent(this, SettingsActivity.class);
                    startActivity(goToSettingsActivity);
                });
    }

}