package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity
                .this
                .findViewById(R.id.MainActivityAllTasksButton)
                .setOnClickListener(view -> {
                    Intent goToAllTasksActivity = new Intent(MainActivity.this, AllTasksActivity.class);
                    startActivity(goToAllTasksActivity);
                });

        MainActivity
                .this
                .findViewById(R.id.MainActivityAddTaskButton)
                .setOnClickListener(view -> {
                    Intent goToAddTasksActivity = new Intent(MainActivity.this, AddTaskActivity.class);
                    startActivity(goToAddTasksActivity);
                });
    }

}