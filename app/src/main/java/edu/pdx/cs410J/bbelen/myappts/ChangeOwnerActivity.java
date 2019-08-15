package edu.pdx.cs410J.bbelen.myappts;

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
 * This class handles the activity of changing the appointment book in use
 *
 * @author Belén Bustamante
 */
public class ChangeOwnerActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    String ownerText = "";

    /**
     * Executed when the activity is launched. Retrieves current application preferences.
     * @param savedInstanceState: the application's current instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_owner);
        prefs = getSharedPreferences("edu.pdx.cs410J.bbelen.myappts", MODE_PRIVATE);
    }

    /**
     * Calls processChangeOwner() and changes the intent back to the main activity if there
     * was a successful owner change.
     * @param view: current application view
     */
    public void submit(View view) {
        if (processChangeOwner()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Ensures an owner name was entered before setting the app preferences' "ownerPref" field to
     * the new name. Creates a file with the newly entered owner name if one doesn't already exist.
     * @return: True if it was able to change the owner
     */
    private boolean processChangeOwner() {
        EditText owner = findViewById(R.id.ownerText);
        ownerText = owner.getText().toString();

        if (!ownerText.equals("")) {
            String filename = ownerText + ".txt";
            prefs.edit().putString("ownerPref", ownerText).apply();
            try {
                File file = new File(getApplicationContext().getFilesDir(), filename);
                if (!file.exists()) {
                    file.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(file, true);
                    fileOutputStream.write((ownerText + System.getProperty("line.separator")).getBytes());
                }

            } catch (IOException ex) {
                Log.d("Error", Objects.requireNonNull(ex.getMessage()));
            }
            return true;
        }
        else {
            toast("Cannot submit without entering a name first.");
        }

        ownerText = "";
        return false;

    }

    /**
     * Returns the view back to the main activity page.
     * @param view: current application view
     */
    public void goBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Utility method used to display toast messages to the user
     * @param message: Message to be displayed
     */
    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
