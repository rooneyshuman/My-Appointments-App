package com.cs410j.myappts;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class LaunchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
    }

    public void submit(View view) {
        createApptBook();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void createApptBook() {
        EditText owner = findViewById(R.id.ownerText);
        String ownerText = owner.getText().toString();

        try {
            File file = new File(getApplicationContext().getFilesDir(), "myAppts.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file,true);
            fileOutputStream.write((ownerText + System.getProperty("line.separator")).getBytes());

        }  catch(IOException ex) {
            Log.d("Error", Objects.requireNonNull(ex.getMessage()));
        }

    }
}
