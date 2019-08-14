package com.cs410j.myappts;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import edu.pdx.cs410J.ParserException;

public class AddApptActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private String description;
    private int year;
    private int month;
    private int day;
    boolean begin;
    String beginTime;
    String endTime;

    public AddApptActivity() {
        description = "";
        endTime = "";
        beginTime = "";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appt);

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


    public void setDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddApptActivity.this,
                AddApptActivity.this, year, month, day);
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

        TimePickerDialog timePickerDialog = new TimePickerDialog(AddApptActivity.this,
                AddApptActivity.this, hour, minute, false);
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
        }
        else
            am_pm = "am";

        if (hour == 0)
            hour = 12;

        dateBuilder.append(month).append("/").append(day).append("/").append(year).append(" ");
        dateBuilder.append(hour).append(":").append(minute).append(" ").append(am_pm);

        if (begin) {
            beginTime = dateBuilder.toString();
            TextView beginTimeText = findViewById(R.id.beginTimeText);
            beginTimeText.setText(beginTime);
        }
        else {
            endTime = dateBuilder.toString();
            TextView endTimeText = findViewById(R.id.endTimeText);
            endTimeText.setText(endTime);
        }
    }

    public void submitAppt(View view) {
        EditText descText = findViewById(R.id.descText);
        description = descText.getText().toString();

        if (description.equals("") || beginTime.equals("") || endTime.equals("")) {
            toast("Please complete filling out the appointment information");
        }
        else {
            Appointment appt = new Appointment(description, beginTime, endTime);
            AppointmentBook appointmentBook = null;

            File file = new File(getApplicationContext().getFilesDir(), "myAppts.txt");
            TextParser textParser = new TextParser(file);

            try {
                appointmentBook = (AppointmentBook) textParser.parse();
            } catch (ParserException e) {
                toast(e.getMessage());
            }
            if (appointmentBook != null) {
                appointmentBook.addAppointment(appt);
                TextDumper textDumper = new TextDumper(file);
                try {
                    textDumper.dump(appointmentBook);
                } catch (IOException e) {
                    toast(e.getMessage());
                }
                description = "";
                beginTime = "";
                endTime = "";
                toast("Appointment added: " + appt.toString());
            }
        }

    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
