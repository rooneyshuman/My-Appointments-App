package com.cs410j.myappts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This class handles the main application activity.
 *
 * @author Belén Bustamante
 */
public class MainActivity extends AppCompatActivity {

    private String owner;
    SharedPreferences prefs = null;

    /**
     * Executed when the activity is launched. Sets proper content view.
     * @param savedInstanceState: the application's current instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences("com.cs410j.myappts", MODE_PRIVATE);
        setContentView(R.layout.activity_main);
    }

    /**
     * Checks if application is on its firstRun and calls the LaunchActivity if so. If not,
     * sets the ownerTextMain field to the current owner name saved in the "ownerPref" preferences
     * parameter
     */
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

    /**
     * Begins the HelpActivity intent
     * @param view: Current application view
     */
    public void helpMe(View view) {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    /**
     * Begins the AddApptActivity intent
     * @param view: Current application view
     */
    public void addAppt(View view) {
        Intent intent = new Intent(this, AddApptActivity.class);
        startActivity(intent);
    }

    /**
     * Begins the SearchActivity intent
     * @param view: Current application view
     */
    public void search(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    /**
     * Begins the ViewAllActivity intent
     * @param view: Current application view
     */
    public void viewAll(View view) {
        Intent intent = new Intent(this, ViewAllActivity.class);
        startActivity(intent);
    }

    /**
     * Begins the ChangeOwnerActivity intent
     * @param view: Current application view
     */
    public void changeOwner(View view) {
        Intent intent = new Intent(this, ChangeOwnerActivity.class);
        startActivity(intent);
    }
}
