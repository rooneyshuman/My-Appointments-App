package edu.pdx.cs410J.bbelen.myappts;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;

/**
 * This class holds a list of appointments associated with an owner.
 *
 * @author Belén Bustamante
 */
public class AppointmentBook extends AbstractAppointmentBook {
    private String owner;
    private ArrayList<Appointment> appointments;

    /**
     * Constructor for AppointmentBook class. Initializes the appointment book owner
     * @param owner: String holding the name of the owner
     */
    AppointmentBook(String owner) {
        this.owner = owner;
        this.appointments = new ArrayList<>();
    }

    /**
     * @return Returns the name of the appointment book's owner as String
     */
    @Override
    public String getOwnerName() {
        return owner;
    }

    /**
     * @return Returns all appointments in the appointment book as a collection
     */
    @Override
    public Collection getAppointments() {
        return appointments;
    }

    /**
     * Adds a new appointment to the appointment book in sorted order
     * @param appt: Appointment's information to add
     */
    @Override
    public void addAppointment(AbstractAppointment appt) {
        this.appointments.add((Appointment) appt);
        Collections.sort(appointments);
    }

    /**
     * Searches an owner's appointment book for appointments in a given date range.
     * @param beginTime: The date at the beginning of the search range (inclusive)
     * @param endTime: The date at the end of the search range (inclusive)
     * @return Returns the appointment book of appointments matching the search criteria
     * @throws ParseException : Exception thrown if dates entered are formatted incorrectly
     */
    public  AppointmentBook search(String beginTime, String endTime) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        Date begin = formatter.parse(beginTime);
        Date end = formatter.parse(endTime);
        AppointmentBook foundAppts = new AppointmentBook(owner);

        for (Appointment appt : appointments) {
            if (appt.getBeginTime().compareTo(begin) >= 0 && appt.getEndTime().compareTo(end) <= 0)
                foundAppts.addAppointment(appt);
        }
        return foundAppts;
    }
}