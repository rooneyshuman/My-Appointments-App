package com.cs410j.myappts;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!canLoad()) {
            // Display error - no appointment book found on device. Enter your name to create a new appointment book.
        }

    }

    private boolean canLoad() {
        File file = new File("myAppts.txt");
        // File does not exist, create new file
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                Log.v("Error" ,"Unable to create new file");
            }
        }

        // Check if file is empty
        if (file.length() != 0)
            return true;

        return false;
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
