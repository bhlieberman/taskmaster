package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class AddTaskActivity extends AppCompatActivity {

    private int tasks = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        AddTaskActivity
                .this
                .findViewById(R.id.AddTaskActivityAddTaskButton)
                .setOnClickListener(view -> {
                    tasks += 1;
                    Log.d("", "total tasks: " + tasks);
                    ((EditText) findViewById(R.id.AddTaskActivityTaskNameEditText)).setText("");
                    ((EditText) findViewById(R.id.AddTaskActivityTaskDescriptionEditText)).setText("");

                    Toast.makeText(getApplicationContext(), "task added!", Toast.LENGTH_LONG).show();
                });
    }
}