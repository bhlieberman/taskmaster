package com.example.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;


import com.example.taskmaster.R;
import com.example.taskmaster.adapters.TaskRecyclerViewViewAdapter;
import com.amplifyframework.datastore.generated.model.Task;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "mainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpRecyclerView();


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (prefs.contains("username")) {
            TextView loggedInUser = this.findViewById(R.id.loggedInUser);
            String sb = prefs.getString("username", null) +
                    "'s tasks";
            loggedInUser.setText(sb);
        }

        goToAddTasks();
        goToAllTasks();
        goToSettings();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpRecyclerView();
    }


    public void setUpRecyclerView() {
        List<Task> tasks = new ArrayList<>();

        RecyclerView taskRV = findViewById(R.id.TaskRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        taskRV.setLayoutManager(layoutManager);
        TaskRecyclerViewViewAdapter adapter = new TaskRecyclerViewViewAdapter(tasks);
        taskRV.setAdapter(adapter);
    }

    public void goToAllTasks() {
        MainActivity
                .this
                .findViewById(R.id.MainActivityAllTasksButton)
                .setOnClickListener(view -> {
                    Intent goToAllTasksActivity = new Intent(MainActivity.this, AllTasksActivity.class);
                    startActivity(goToAllTasksActivity);
                });
    }

    public void goToAddTasks() {
        MainActivity
                .this
                .findViewById(R.id.MainActivityAddTaskButton).setOnClickListener(v -> {
                    Intent goToAddTaskActivity = new Intent(MainActivity.this, AddTaskActivity.class);
                    startActivity(goToAddTaskActivity);
                });
    }


    public void goToSettings() {
        this.findViewById(R.id.SettingsButton)
                .setOnClickListener(view -> {
                    Intent goToSettingsActivity = new Intent(this, SettingsActivity.class);
                    startActivity(goToSettingsActivity);
                });
    }

}