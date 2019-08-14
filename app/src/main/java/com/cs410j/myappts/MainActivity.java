package com.cs410j.myappts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private String owner;
    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences("com.cs410j.myappts", MODE_PRIVATE);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstRun", true)) {
            // Set owner name
            prefs.edit().putBoolean("firstRun", false).apply();
            Intent intent = new Intent(this, LaunchActivity.class);
            startActivity(intent);
        }

        // Get owner name
        else {
            TextView myApptsText = findViewById(R.id.myApptsText);
            try {
                File file = new File(getApplicationContext().getFilesDir(), "myAppts.txt");
                FileInputStream fileInputStream = new FileInputStream (file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                owner = bufferedReader.readLine();
                fileInputStream.close();
                bufferedReader.close();
            }
            catch(IOException ex) {
                Log.d("Error", Objects.requireNonNull(ex.getMessage()));
            }

            myApptsText.setText(String.format("%s's Appointments", owner));
        }
    }

    public void helpMe(View view) {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    public void addAppt(View view) {
    }

    public void search(View view) {
    }

    public void viewAll(View view) {
    }
}
