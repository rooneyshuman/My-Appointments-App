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

public class LaunchActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    private String owner = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        prefs = getSharedPreferences("com.cs410j.myappts", MODE_PRIVATE);
    }

    public void submit(View view) {
        if (createApptBook()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

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

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
