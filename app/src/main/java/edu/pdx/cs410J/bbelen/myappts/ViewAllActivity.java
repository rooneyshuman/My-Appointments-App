package edu.pdx.cs410J.bbelen.myappts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import edu.pdx.cs410J.ParserException;

/**
 * This class handles the activity of displaying all of the current owner's appointments
 *
 * @author Belén Bustamante
 */
public class ViewAllActivity extends AppCompatActivity {

    /**
     * Executed when the activity is launched. Executes display() and sets the proper content view.
     * @param savedInstanceState: the application's current instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        display();
    }

    /**
     * Displays the current owner's appointments. If the appointment book is empty, an error message
     * is displayed
     */
    private void display() {
        SharedPreferences prefs = getSharedPreferences("edu.pdx.cs410J.bbelen.myappts", MODE_PRIVATE);
        String owner = prefs.getString("ownerPref", null);
        String filename = owner + ".txt";
        File file = new File(getApplicationContext().getFilesDir(), filename);
        TextParser textParser = new TextParser(file);
        AppointmentBook appointmentBook = null;
        try {
            appointmentBook = (AppointmentBook) textParser.parse();
        } catch (ParserException e) {
            toast(e.getMessage());
        }

        if (appointmentBook.getAppointments().size() == 0) {
            toast("No appointments have been added yet.");
        }

        else {
            ListView listView = findViewById(R.id.appts_list_view);
            PrettyPrinter prettyPrinter = new PrettyPrinter();
            Collection appointments = appointmentBook.getAppointments();
            ArrayList apptArr = prettyPrinter.buildOutput(appointments);
            listView.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, apptArr.toArray()));
        }
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
