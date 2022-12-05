package com.example.taskmaster;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goToAddTasks();
        goToAllTasks();
        goToSettings();
        goToTaskDetails();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (prefs.contains("username")) {
            TextView loggedInUser = this.findViewById(R.id.loggedInUser);
            String sb = prefs.getString("username", null) +
                    "'s tasks";
            loggedInUser.setText(sb);
        }

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

//    @Override
//    protected void onStart() {
//        super.onStart();
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        if (prefs.contains("username")) {
//            TextView loggedInUser = this.findViewById(R.id.loggedInUser);
//            String sb = prefs.getString("username", null) +
//                    "'s tasks";
//            loggedInUser.setText(sb);
//        }
//    }

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
                .findViewById(R.id.MainActivityAddTaskButton)
                .setOnClickListener(view -> {
                    Intent goToAddTasksActivity = new Intent(MainActivity.this, AddTaskActivity.class);
                    startActivity(goToAddTasksActivity);
                });
    }

    public void goToTaskDetails() {
        MainActivity
                .this
                .findViewById(R.id.MainTaskDetailsButton)
                .setOnClickListener(view -> {
                    Intent goToTaskDetailsActivity = new Intent(MainActivity.this, TaskDetailActivity.class);
                    startActivity(goToTaskDetailsActivity);
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