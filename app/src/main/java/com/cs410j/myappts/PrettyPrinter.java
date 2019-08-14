package com.cs410j.myappts;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class pretty prints the appointments in an appointment book
 *
 * @author Belén Bustamante
 */
public class PrettyPrinter {

    public PrettyPrinter(){
    }

    /**
     * Utility method to build a string containing the appointment book info to be pretty printed
     * @param appointments: The appointment to be pretty printer
     * @return Returns the built String containing the info in a pretty printed format
     */
    public ArrayList<String> buildOutput(Collection<Appointment> appointments) {
        ArrayList<String> arr = new ArrayList<>();

        for (Appointment appointment: appointments) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Description:\t");
            stringBuilder.append(appointment.getDescription()+"\n");
            stringBuilder.append("Begin time:\t");
            stringBuilder.append(appointment.getShortBeginTimeString()+"\n");
            stringBuilder.append("End time:\t");
            stringBuilder.append(appointment.getShortEndTimeString()+"\n");
            stringBuilder.append("Duration:\t");
            stringBuilder.append(appointment.getDuration() + " minutes\n");

            arr.add(stringBuilder.toString());
        }

        return arr;
    }
}