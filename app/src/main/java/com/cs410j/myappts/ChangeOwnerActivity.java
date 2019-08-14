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

public class ChangeOwnerActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    String ownerText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_owner);
        prefs = getSharedPreferences("com.cs410j.myappts", MODE_PRIVATE);
    }


    public void submit(View view) {
        if (processChangeOwner()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

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

    public void goBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
