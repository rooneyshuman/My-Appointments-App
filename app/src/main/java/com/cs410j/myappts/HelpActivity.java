package com.cs410j.myappts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HelpActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        TextView helpText = findViewById(R.id.helpText);
        helpText.setText(readMe());
    }



    public void goBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private String readMe() {
        return
        "This application will allow you to save appointment information such as" +
                " description, begin time, and end time.\n\n" +
                "You can save your new appointment to the app, search for appointments within a " +
                "date range and view all your appointments.\n\n" +
                "Click 'BACK' on any page to return to the app's home page\n\n" +
                "From the home page, the following features are available:\n" +
                "\n\t\u2022 Click 'ADD' to create a new appointment" +
                "\n\t\u2022 Click 'SEARCH' to search for an appointment" +
                "\n\t\u2022 Click 'VIEW ALL' to view all of your\n\t\tappointments" +
                "\n\t\u2022 Click 'HELP' to return to this page";
    }
}
