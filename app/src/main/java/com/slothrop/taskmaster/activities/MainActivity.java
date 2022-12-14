package com.slothrop.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.amplifyframework.core.Amplify;
import com.slothrop.taskmaster.R;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "mainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Amplify.Auth.getCurrentUser(
                success -> {
                    findViewById(R.id.MainActivityAddTaskButton).setVisibility(View.VISIBLE);
                    findViewById(R.id.MainActivityAllTasksButton).setVisibility(View.VISIBLE);
                    findViewById(R.id.LogoutButton).setVisibility(View.VISIBLE);
                    findViewById(R.id.signInBttnSignInMain).setVisibility(View.GONE);
                    findViewById(R.id.signUpbttnSignUpMain).setVisibility(View.GONE);
                    ((TextView) findViewById(R.id.loggedInUser)).setText(success.getUsername());
                    findViewById(R.id.loggedInUser).setVisibility(View.VISIBLE);
                    goToAddTasks();
                    goToAllTasks();
                    logOut();
                },
                failure -> {
                    findViewById(R.id.MainActivityAddTaskButton).setVisibility(View.GONE);
                    findViewById(R.id.MainActivityAllTasksButton).setVisibility(View.GONE);
                    findViewById(R.id.LogoutButton).setVisibility(View.GONE);
                    runOnUiThread(() -> {
                        findViewById(R.id.signInBttnSignInMain).setVisibility(View.VISIBLE);
                        findViewById(R.id.signUpbttnSignUpMain).setVisibility(View.VISIBLE);
                    });

                    goToLogIn();
                    goToSignUp();
                });


        goToSettings();
    }

    private void logOut() {
        findViewById(R.id.LogoutButton).setOnClickListener(v -> {
            Amplify.Auth.signOut(done -> {
                Log.i(TAG, "logged out");
                findViewById(R.id.loggedInUser).setVisibility(View.INVISIBLE);
                findViewById(R.id.MainActivityAddTaskButton).setVisibility(View.INVISIBLE);
                findViewById(R.id.MainActivityAllTasksButton).setVisibility(View.INVISIBLE);
                findViewById(R.id.LogoutButton).setVisibility(View.INVISIBLE);
                runOnUiThread(() -> {
                    findViewById(R.id.signInBttnSignInMain).setVisibility(View.VISIBLE);
                    findViewById(R.id.signUpbttnSignUpMain).setVisibility(View.VISIBLE);
                        });

            });
            goToLogIn();
        });
    }

    private void goToLogIn() {
        MainActivity
                .this
                .findViewById(R.id.signInBttnSignInMain)
                .setOnClickListener(view -> {
                    Intent goToSignInActivity = new Intent(this, SignInActivity.class);
                    startActivity(goToSignInActivity);
                });
    }

    private void goToSignUp() {
        MainActivity
                .this
                .findViewById(R.id.signUpbttnSignUpMain)
                .setOnClickListener(view -> {
                    Intent gotoSignUpActivity = new Intent(this, SignUpActivity.class);
                    startActivity(gotoSignUpActivity);
                });
    }

    private void goToAllTasks() {
        MainActivity
                .this
                .findViewById(R.id.MainActivityAllTasksButton)
                .setOnClickListener(view -> {
                    Intent goToAllTasksActivity = new Intent(MainActivity.this, AllTasksActivity.class);
                    startActivity(goToAllTasksActivity);
                });
    }

    private void goToAddTasks() {
        MainActivity
                .this
                .findViewById(R.id.MainActivityAddTaskButton).setOnClickListener(v -> {
                    Intent goToAddTaskActivity = new Intent(MainActivity.this, AddTaskActivity.class);
                    startActivity(goToAddTaskActivity);
                });
    }


    private void goToSettings() {
        this.findViewById(R.id.SettingsButton)
                .setOnClickListener(view -> {
                    Intent goToSettingsActivity = new Intent(this, SettingsActivity.class);
                    startActivity(goToSettingsActivity);
                });
    }


}