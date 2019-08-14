package com.cs410j.myappts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.Collection;

import edu.pdx.cs410J.ParserException;

public class ViewAllActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        display();
    }

    private void display() {
        File file = new File(getApplicationContext().getFilesDir(), "myAppts.txt");
        TextParser textParser = new TextParser(file);
        AppointmentBook appointmentBook = null;
        try {
            appointmentBook = (AppointmentBook) textParser.parse();
        } catch (ParserException e) {
            toast(e.getMessage());
        }

        if (appointmentBook.getAppointments().size() == 0) {

        }

        else {
            Collection<Appointment> appointments = appointmentBook.getAppointments();
            ListView listView = findViewById(R.id.appts_list_view);
            listView.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, appointments.toArray()));
        }

    }

    public void goBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
