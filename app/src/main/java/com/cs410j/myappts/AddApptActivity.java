package com.cs410j.myappts;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

/**
 * This class handles the activity of adding a new appointment
 *
 * @author Belén Bustamante
 */
public class AddApptActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private String description;
    private int year;
    private int month;
    private int day;
    boolean begin;
    String beginTime;
    String endTime;

    /**
     * Class constructor. Initialized private date members.
     */
    public AddApptActivity() {
        description = "";
        endTime = "";
        beginTime = "";
    }

    /**
     * Executed when the activity is launched. Executes setDate() when setBeginBtn or setEndBtn
     * are clicked.
     * @param savedInstanceState: the application's current instance
     */
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

    /**
     * Returns the view back to the main activity page.
     * @param view: current application view
     */
    public void goBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Initializes the DatePicker dialog box to allow the user to set the dates and times.
     */
    public void setDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddApptActivity.this,
                AddApptActivity.this, year, month, day);
        datePickerDialog.show();
    }

    /**
     * Called by setDate() to retrieve the date information selected. Initializes TimePicker.
     * @param datePicker: current datePicker object
     * @param i: year selected
     * @param i1: month selected
     * @param i2: day selected
     */
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

    /**
     * Called by onDateSet() to retrieve the time information selected. Saves the entered date and
     * time to either beginDateTime or endDateTime.
     * @param timePicker: timePicker object
     * @param i: hour selected
     * @param i1: minute selected
     */
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

    /**
     * Allows the user to add the appointment to their appointment book. Verifies that all necessary
     * fields have been filed.
     * @param view: current application view.
     */
    public void submitAppt(View view) {
        EditText descText = findViewById(R.id.descText);
        description = descText.getText().toString();

        if (description.equals("") || beginTime.equals("") || endTime.equals("")) {
            toast("Please complete filling out the appointment information");
        }

        else {
            Appointment appt = null;
            try {
                appt = new Appointment(description, beginTime, endTime);
            } catch (NumberFormatException e) {
                toast("Begin date cannot occur after end date. Re-enter the appointment dates.");
                return;
            }

            AppointmentBook appointmentBook = null;
            SharedPreferences prefs = getSharedPreferences("com.cs410j.myappts", MODE_PRIVATE);
            String owner = prefs.getString("ownerPref", null);
            String filename = owner + ".txt";

            File file = new File(getApplicationContext().getFilesDir(), filename);
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

    /**
     * Utility method used to display toast messages to the user
     * @param message: Message to be displayed
     */
    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
