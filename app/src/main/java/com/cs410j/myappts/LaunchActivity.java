package com.cs410j.myappts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * This class handles the activity of setting up the app's owner when first launched
 *
 * @author Belén Bustamante
 */
public class LaunchActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    private String owner = "";

    /**
     * Executed when the activity is launched. Sets the proper content view.
     * @param savedInstanceState: the application's current instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        prefs = getSharedPreferences("com.cs410j.myappts", MODE_PRIVATE);
    }

    /**
     * Calls createApptBook() and changes the intent back to the main activity if there
     * was a successful owner set up.
     * @param view: current application view
     */
    public void submit(View view) {
        if (createApptBook()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Ensures an owner name was entered before setting the app preferences' "ownerPref" field to
     * the new name. Creates a file with the newly entered owner name if one doesn't already exist.
     * @return: True if it was able to change the owner
     */
    private boolean createApptBook() {
        EditText ownerText = findViewById(R.id.ownerText);
        owner = ownerText.getText().toString();
        if (!owner.equals("")) {
            String filename = owner + ".txt";
            prefs.edit().putString("ownerPref", owner).apply();

            try {
                File file = new File(getApplicationContext().getFilesDir(), filename);
                if (!file.exists()) {
                    file.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(file, true);
                    fileOutputStream.write((owner + System.getProperty("line.separator")).getBytes());
                }


            } catch (IOException ex) {
                Log.d("Error", Objects.requireNonNull(ex.getMessage()));
            }
            return true;
        }

        else {
            toast("Cannot submit without entering a name first.");
        }

        owner = "";
        return false;
    }

    /**
     * Utility method used to display toast messages to the user
     * @param message: Message to be displayed
     */
    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
