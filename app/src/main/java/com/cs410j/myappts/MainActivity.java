package com.cs410j.myappts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
        SharedPreferences prefs = getSharedPreferences("com.cs410j.myappts", MODE_PRIVATE);
        owner = prefs.getString("ownerPref", null);

        TextView ownerTextMain = findViewById(R.id.ownerTextMain);
        ownerTextMain.setText(String.format("%s", owner));
    }

    public void helpMe(View view) {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    public void addAppt(View view) {
        Intent intent = new Intent(this, AddApptActivity.class);
        startActivity(intent);
    }

    public void search(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void viewAll(View view) {
        Intent intent = new Intent(this, ViewAllActivity.class);
        startActivity(intent);
    }

    public void changeOwner(View view) {
        Intent intent = new Intent(this, ChangeOwnerActivity.class);
        startActivity(intent);
    }
}
