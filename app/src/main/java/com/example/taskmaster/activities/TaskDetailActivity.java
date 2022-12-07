package com.example.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum;

import android.os.Bundle;
import android.widget.EditText;

import com.example.taskmaster.R;

public class TaskDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

//        EditText view = TaskDetailActivity
//                .this
//                .findViewById(R.id.LoremTextBox);
//
//        view.setText(new LoremIpsum().toString());
    }
}