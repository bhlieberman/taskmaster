package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class AllTasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);

        AllTasksActivity
                .this
                .findViewById(R.id.allTasksBackButton)
                .setOnClickListener(view -> {
                    Intent goBackToMainActivity = new Intent(this, MainActivity.class);
                    startActivity(goBackToMainActivity);
                });
    }
}