package com.example.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

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
    TaskRecyclerViewViewAdapter adapter = new TaskRecyclerViewViewAdapter(tasks);
    public static final String TAG = "allTasks";

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
                    runOnUiThread(() -> adapter.notifyDataSetChanged()); // since this runs asynchronously, the adapter may already have rendered, so we have to tell it to update

                },
                failure -> Log.e(TAG, "Failed to read Tasks from database")
        );

        AllTasksActivity
                .this
                .findViewById(R.id.allTasksBackButton)
                .setOnClickListener(view -> {
                    Intent goBackToMainActivity = new Intent(this, MainActivity.class);
                    startActivity(goBackToMainActivity);
                });
    }
}