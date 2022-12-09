package com.example.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.TaskStatus;
import com.example.taskmaster.R;

public class AddTaskActivity extends AppCompatActivity {

    private final String TAG = "addTask";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        AddTaskActivity
                .this
                .findViewById(R.id.AddTaskActivityAddTaskButton)
                .setOnClickListener(view -> {
                    Task newTask = Task.builder()
                            .name(((EditText)findViewById(R.id.AddTaskActivityTaskNameEditText)).getText().toString())
                            .description(((EditText) findViewById(R.id.AddTaskActivityTaskDescriptionEditText)).getText().toString())
                            .build();

                    Amplify.API.mutate(
                            ModelMutation.create(newTask),
                            success -> Log.i(TAG, "AddASuperPetActivity.onCreate(): made a super pet successfully!"),
                            failure -> Log.w(TAG, "AddASuperPetActivity.onCreate(): failed to make a super pet", failure)
                    );
                    Toast.makeText(getApplicationContext(), "task added!", Toast.LENGTH_LONG).show();
                });
    }
}