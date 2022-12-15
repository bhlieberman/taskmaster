package com.example.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.taskmaster.R;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.core.Amplify;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {
    public static final String TAG = "signInActivity";
    Intent callingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        callingIntent = getIntent();

        setUpSignInForm();
    }

    public void setUpSignInForm(){
        findViewById(R.id.signInBttnSignIn).setOnClickListener(v -> {
            // gather email and password
            String userEmail = ((TextView) findViewById(R.id.signInETEmail)).getText().toString();
            String userPassword = ((TextView) findViewById(R.id.signInETPassword)).getText().toString();

            // make a login call to Cognito
            Amplify.Auth.signIn(
                    userEmail,
                    userPassword,
                    success -> {
                        Log.i(TAG, "Login succeeded: " + success);
                        Intent goToProductListIntent = new Intent(this, MainActivity.class);
                        startActivity(goToProductListIntent);
                    },
                    failure -> {
                        Log.i(TAG, "Login failed: " + failure);
                        runOnUiThread(() -> Toast.makeText(this, "Sign In failed!", Toast.LENGTH_SHORT).show());
                    }
            );
        });
    }
}