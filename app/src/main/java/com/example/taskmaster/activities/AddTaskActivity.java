package com.example.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.TaskStatus;
import com.amplifyframework.datastore.generated.model.Team;
import com.example.taskmaster.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class AddTaskActivity extends AppCompatActivity {

    private final String TAG = "addTask";
    private Spinner teamSpinner;
    private final List<String> teamNames = new ArrayList<>();
    private List<Team> teamsInfo = new ArrayList<>();
    private final CompletableFuture<List<Team>> query = new CompletableFuture<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);


        getData();
//        runOnUiThread(() -> {
////            createTeams();
//            getData();
//
//        });

        AddTaskActivity
                .this
                .findViewById(R.id.AddTaskActivityAddTaskButton)
                .setOnClickListener(this::addTask);

    }

    private void setUpTeamNameSpinner() {
        teamSpinner = findViewById(R.id.TeamNameSpinner);
        teamSpinner.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                teamNames
        ));
    }

    private void getData() {
        Amplify.API.query(
                ModelQuery.list(Team.class),
                success -> {
                    Log.i(TAG, "read from Team DB successfully!");
                    success.getData().iterator().forEachRemaining(team -> {
                        teamNames.add(team.getName());
                        teamsInfo.add(team);
                    });
                    query.complete(teamsInfo);
                    runOnUiThread(this::setUpTeamNameSpinner);
                    },
                failure -> {
                    Log.w(TAG, "couldn't read from Team DB");
                    query.complete(null);
                }
        );

    }

    private void updateTaskList() {
        try {
            teamsInfo = query.get();
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
        Team selectedTeam = teamsInfo.stream()
                .filter(team -> team
                        .getName()
                        .equals(teamSpinner.getSelectedItem().toString()))
                .findAny()
                .orElseThrow(RuntimeException::new);
        Task newTask = Task.builder()
                .name(((EditText)findViewById(R.id.AddTaskActivityTaskNameEditText)).getText().toString())
                .description(((EditText) findViewById(R.id.AddTaskActivityTaskDescriptionEditText)).getText().toString())
                .status(TaskStatus.NEW)
                .team(selectedTeam)
                .build();

        Amplify.API.mutate(
                ModelMutation.create(newTask),
                success -> Log.i(TAG, "added new task"),
                failure -> Log.w(TAG, "couldn't add task because: ", failure)
        );
    }

//    private void createTeams() {
//        String[] teamNames = { "team one", "team two", "team three" };
//        Stream.of(teamNames).forEach(team -> {
//            Team t = Team.builder().name(team).build();
//            Amplify.API.mutate(ModelMutation.create(t),
//                    succ -> Log.i(TAG, "succeeded"),
//                    fail -> Log.w(TAG, "failed"));
//        });
//    }

    private void addTask(View v) {
        updateTaskList();
        Toast.makeText(getApplicationContext(), "task added!", Toast.LENGTH_LONG).show();
    }
}