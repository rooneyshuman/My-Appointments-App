package com.cs410j.myappts;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Collection;

import edu.pdx.cs410J.ParserException;

public class SearchActivity extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private int year;
    private int month;
    private int day;
    boolean begin;
    String beginTime;
    String endTime;

    public SearchActivity() {
        endTime = "";
        beginTime = "";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        findViewById(R.id.setBeginBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                begin = true;
                setDate();
            }
        });

        findViewById(R.id.setEndBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                begin = false;
                setDate();
            }
        });
    }


    public void goBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void searchAppt(View view) {
        if (!beginTime.equals("") || !endTime.equals("")) {

            try {
                new Appointment("desc", beginTime, endTime);
            } catch (NumberFormatException e) {
                toast("Begin date cannot occur after end date. Re-enter the appointment dates.");
                return;
            }

            SharedPreferences prefs = getSharedPreferences("com.cs410j.myappts", MODE_PRIVATE);
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
                toast("No appointments have been added. Nothing to search.");
            } else {
                Collection appointments = null;
                ListView listView = findViewById(R.id.results_list_view);
                try {
                    appointments = appointmentBook.search(beginTime, endTime).getAppointments();
                } catch (ParseException e) {
                    toast(e.getMessage());
                }
                if (appointments != null && appointments.size() != 0) {
                    listView.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, appointments.toArray()));
                } else {
                    String[] arr = new String[1];
                    arr[0] = "No matching appointments found. Nothing to display.";
                    listView.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, arr));
                }
            }
        }

        else
            toast("Please complete filling out the search criteria.");

    }

    public void setDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(SearchActivity.this,
                SearchActivity.this, year, month, day);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        year = i;
        month = i1 + 1;
        day = i2;

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(SearchActivity.this,
                SearchActivity.this, hour, minute, false);
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        StringBuilder dateBuilder = new StringBuilder();
        int hour = i;
        int minute = i1;
        String am_pm;

        if (hour > 12) {
            am_pm = "pm";
            hour = hour - 12;
        } else
            am_pm = "am";

        if (hour == 0)
            hour = 12;

        dateBuilder.append(month).append("/").append(day).append("/").append(year).append(" ");
        dateBuilder.append(hour).append(":").append(minute).append(" ").append(am_pm);

        if (begin) {
            beginTime = dateBuilder.toString();
            TextView beginTimeText = findViewById(R.id.beginTimeText);
            beginTimeText.setText(beginTime);
        } else {
            endTime = dateBuilder.toString();
            TextView endTimeText = findViewById(R.id.endTimeText);
            endTimeText.setText(endTime);
        }
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
