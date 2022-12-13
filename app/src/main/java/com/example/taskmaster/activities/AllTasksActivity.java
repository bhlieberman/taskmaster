package com.example.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.example.taskmaster.R;
import com.example.taskmaster.adapters.TaskRecyclerViewViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class AllTasksActivity extends AppCompatActivity {
    private final List<Task> tasks = new ArrayList<>();
    TaskRecyclerViewViewAdapter adapter;
    private static final String TAG = "allTasks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);

        Amplify.API.query(
                ModelQuery.list(Task.class),
                success -> {
                    Log.i(TAG, "Read Super Pets successfully");
                    for (Task databaseTask : success.getData()) {
                        tasks.add(databaseTask);
                    }
                    runOnUiThread(() -> adapter.notifyDataSetChanged());

                },
                failure -> Log.e(TAG, "Failed to read Tasks from database")
        );

        setUpRecyclerView();

        AllTasksActivity
                .this
                .findViewById(R.id.allTasksBackButton)
                .setOnClickListener(view -> {
                    Intent goBackToMainActivity = new Intent(this, MainActivity.class);
                    startActivity(goBackToMainActivity);
                });
    }

    private void setUpRecyclerView() {
        RecyclerView taskRV = findViewById(R.id.TaskRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        taskRV.setLayoutManager(layoutManager);
        adapter = new TaskRecyclerViewViewAdapter(tasks, this);
        taskRV.setAdapter(adapter);
    }
}