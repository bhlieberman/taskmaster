package com.example.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.example.taskmaster.R;
import com.example.taskmaster.adapters.TaskRecyclerViewViewAdapter;
import com.example.taskmaster.models.Task;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TASK_NAME_TAG = "taskName";

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
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("finish code fellows", "don't die", Task.State.IN_PROGRESS));
        tasks.add(new Task("get a job", "hopefully it pays", Task.State.NEW));
        tasks.add(new Task("move back to NYC", "the best city on earth", Task.State.ASSIGNED));
        tasks.add(new Task("finish reading David Copperfield", "It's a novel by Charles Dickens", Task.State.IN_PROGRESS));
        tasks.add(new Task("finish code fellows", "don't die", Task.State.IN_PROGRESS));
        tasks.add(new Task("get a job", "hopefully it pays", Task.State.NEW));
        tasks.add(new Task("move back to NYC", "the best city on earth", Task.State.ASSIGNED));
        tasks.add(new Task("finish reading David Copperfield", "It's a novel by Charles Dickens", Task.State.IN_PROGRESS));

        RecyclerView taskRV = findViewById(R.id.TaskRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        taskRV.setLayoutManager(layoutManager);
        TaskRecyclerViewViewAdapter adapter = new TaskRecyclerViewViewAdapter(tasks, this);
        taskRV.setAdapter(adapter);
    }

}