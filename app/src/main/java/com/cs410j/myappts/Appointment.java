package com.cs410j.myappts;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import edu.pdx.cs410J.AbstractAppointment;

/**
 * This class holds all the information relating to an appointment.
 * This information includes: description, beginTime, and endTime.
 *
 * @author Belén Bustamante
 */
public class Appointment extends AbstractAppointment implements Comparable<Appointment> {

    private String description;
    private Date beginTime;
    private Date endTime;
    private DateFormat dateFormatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.US);
    private SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

    /**
     * Constructor with arguments. Populates Appointment data members with info passed in.
     * @param description: a description of the appointment
     * @param beginTime: when the appointment begins
     * @param endTime: when the appointment ends
     */
    Appointment(String description, String beginTime, String endTime) throws NumberFormatException {
        try {
            this.beginTime = formatter.parse(beginTime);
            this.endTime = formatter.parse(endTime);
            getDuration();
        } catch (ParseException | IllegalArgumentException e) {
            throw new NumberFormatException("Invalid date time format.");
        }
        this.description = description;
    }

    /**
     * @return Returns appointment's begin time as String
     */
    @Override
    public String getBeginTimeString() {
        return formatter.format(beginTime);
    }

    /**
     * @return Returns appointment's end time as String
     */
    @Override
    public String getEndTimeString() {
        return formatter.format(endTime);
    }

    /**
     * @return Returns appointment's description as String
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * @return Returns appointment's begin time Date object
     */
    @Override
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * @return Returns appointment's end time Date object
     */
    @Override
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Returns < 0 if this appointment should come before the one it is being compared to,
     * > 0 if it should come after, or 0 if they are equal
     * @param to_compare the appointment we are comparing the current one against
     * @return a number denoting the order between the two appointments
     */
    @Override
    public int compareTo(Appointment to_compare) {
        if (this.beginTime.compareTo(to_compare.beginTime) == 0) {    // begin times are equal, compare end times
            if (this.endTime.compareTo(to_compare.endTime) == 0) {      // end times are equal, compare descriptions
                return this.description.compareTo(to_compare.description);
            }
            return this.endTime.compareTo((to_compare.endTime));
        }
        return this.beginTime.compareTo(to_compare.beginTime);
    }

    /**
     * Calculates the duration of an appointment in minutes
     * @return an appointments duration in minutes as a long
     */
    public long getDuration(){
        long millisec = endTime.getTime() - beginTime.getTime();
        long duration = TimeUnit.MINUTES.convert(millisec, TimeUnit.MILLISECONDS);
        if (duration < 0)
            throw new IllegalArgumentException("Improper dates. End time happens before begin time");
        else
            return duration;
    }

    /**
     * Returns appointment's end time as a String formatted with DateFormat.SHORT
     * @return appointment's String end time as SHORT
     */
    public String getShortEndTimeString() {
        return dateFormatter.format(endTime);
    }

    /**
     * Returns appointment's begin time as a String formatted with DateFormat.SHORT
     * @return appointment's String begin time as SHORT
     */
    public String getShortBeginTimeString() {
        return dateFormatter.format(beginTime);
    }
}