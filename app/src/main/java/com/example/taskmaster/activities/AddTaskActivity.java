package com.example.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taskmaster.R;
import com.example.taskmaster.data.Datasource;
import com.example.taskmaster.data.TaskDao;
import com.example.taskmaster.models.Task;

public class AddTaskActivity extends AppCompatActivity {

    TaskDao db;
    int tasks = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        db = Room.databaseBuilder(getApplicationContext(), Datasource.class,
                "datasource")
                .allowMainThreadQueries()
                .build().taskDao();

        AddTaskActivity
                .this
                .findViewById(R.id.AddTaskActivityAddTaskButton)
                .setOnClickListener(view -> {
                    String title = String.valueOf(((EditText) findViewById(R.id.AddTaskActivityTaskNameEditText)).getText());
                    String body = String.valueOf(((EditText) findViewById(R.id.AddTaskActivityTaskDescriptionEditText)).getText());
                    Task.State status = Task.State.NEW;
                    db.insertTask(new Task(tasks+1, title, body, status));
                    Toast.makeText(getApplicationContext(), "task added!", Toast.LENGTH_LONG).show();
                });
    }
}