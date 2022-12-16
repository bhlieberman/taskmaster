package com.slothrop.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.slothrop.taskmaster.R;

public class SettingsActivity extends AppCompatActivity {

    SharedPreferences preferences;
    public static final String USERNAME_TAG = "username";
    public static final String USER_PHONE_TAG = "userPhone";
    public static final String USER_ADDRESS_TAG = "userAddress";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        saveValuesToSharedPrefs();

    }

    public void saveValuesToSharedPrefs(){
        SharedPreferences.Editor preferenceEditor = preferences.edit();
        Button saveButton = SettingsActivity.this.findViewById(R.id.SettingsSaveButton);
        saveButton.setOnClickListener(v -> {
            String usernameText = ((EditText) findViewById(R.id.SettingsActivityEditUsename)).getText().toString();
            preferenceEditor.putString("username", usernameText);
            preferenceEditor.apply();

            Toast.makeText(this, "Settings Saved!", Toast.LENGTH_SHORT).show();
        });
    }
}