package com.slothrop.taskmaster.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.TaskStatus;
import com.amplifyframework.datastore.generated.model.Team;
import com.slothrop.taskmaster.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AddTaskActivity extends AppCompatActivity {

    private final String TAG = "addTask";
    private Spinner teamSpinner;
    private final List<String> teamNames = new ArrayList<>();
    private List<Team> teamsInfo = new ArrayList<>();
    private final CompletableFuture<List<Team>> query = new CompletableFuture<>();
    private String s3ImageKey;
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityResultLauncher = getImagePickingActivityResultLauncher();
        setContentView(R.layout.activity_add_task);
        setupAddImageBttn();


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

    private void addTask(View v) {
        updateTaskList();
        Toast.makeText(getApplicationContext(), "task added!", Toast.LENGTH_LONG).show();
    }

    private void setupAddImageBttn(){
        findViewById(R.id.AddImageButton).setOnClickListener(v -> {
            launchImageSelectionIntent();
        });
    }

    private void launchImageSelectionIntent(){
        Intent imageFilePickingIntent = new Intent(Intent.ACTION_GET_CONTENT); // one of several file picking activities built into Android
        //TODO play around with setTypes
        imageFilePickingIntent.setType("*/*");  // only allow one kind or category of file; if you don't have this, you get a very cryptic error about "No activity found to handle Intent
        imageFilePickingIntent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/jpeg", "image/png"}); // only pick JPEG and PNG images

        // Launch Android's built-in file picking activity using our newly created ActivityResultLauncher below
        activityResultLauncher.launch(imageFilePickingIntent);
    }

    private ActivityResultLauncher<Intent> getImagePickingActivityResultLauncher(){
        return registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Uri pickedImageFileUri = result.getData().getData();
                    try{
                        // take in the file URI and turn it into a inputStream
                        InputStream pickedImageInputStream = getContentResolver().openInputStream(pickedImageFileUri);
                        String pickedImageFilename = DocumentFile.fromSingleUri(this, pickedImageFileUri).getName();
                        Log.i(TAG, "Succeeded in getting input stream from file on phone! Filename is: " + pickedImageFilename);
                        uploadInputStreamToS3(pickedImageInputStream, pickedImageFilename, pickedImageFileUri);
                    } catch (FileNotFoundException fnfe){
                        Log.e(TAG, "Could not get file from file picker" + fnfe.getMessage());
                    }
                }
        );
    }

    private void uploadInputStreamToS3(InputStream pickedImageInputStream, String pickedImageFilename, Uri pickedImageFileUri){
        Amplify.Storage.uploadInputStream(
                pickedImageFilename,
                pickedImageInputStream,
                success -> {
                    Log.i(TAG, "Succeeded in getting file uploaded to S3! Key is: " + success.getKey());
                    s3ImageKey = success.getKey();
//          saveSuperPet();
                    ImageView superPetImage = findViewById(R.id.AddImageView);
                    InputStream pickedImageInputStreamCopy = null; // need to make a copy because InputStreams cannot be reused!
                    try {
                        pickedImageInputStreamCopy = getContentResolver().openInputStream(pickedImageFileUri);
                    } catch (FileNotFoundException fnfe) {
                        Log.e(TAG, "Could not get file stream from URI! " + fnfe.getMessage(), fnfe);
                    }
                    superPetImage.setImageBitmap(BitmapFactory.decodeStream(pickedImageInputStreamCopy));
                },
                failure -> Log.e(TAG, "Failure in uploading file to S3 with filename: " + pickedImageFilename + " with error: " + failure.getMessage())
        );
    }
}