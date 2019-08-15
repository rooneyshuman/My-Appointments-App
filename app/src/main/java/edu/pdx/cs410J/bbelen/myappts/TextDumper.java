package edu.pdx.cs410J.bbelen.myappts;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookDumper;

/**
 * This class implements the dump() method in the AppointmentBookDumper interface.
 * Has a private data member, file, which is set through the class constructor, to
 * determine the file to which the contents of the appointment book need to be saved.
 *
 * @author Belén Bustamante
 */
public class TextDumper implements AppointmentBookDumper {
    private File file;

    /**
     * Constructor for TextDumper class. Initializes the file object
     * @param file: File object
     */
    TextDumper(File file) {
        this.file = file;
    }

    /**
     * Dumps appointment information with a newline as delimeter from an appointment book into file
     * @param appointmentBook: The appointment book whose contents are being dumped to file
     * @throws IOException
     */
    @Override
    public void dump(AbstractAppointmentBook appointmentBook) throws IOException {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file,false);
            Collection<Appointment>  appointments = appointmentBook.getAppointments();
            fileOutputStream.write((appointmentBook.getOwnerName() + System.getProperty("line.separator")).getBytes());
            for (Appointment appointment : appointments) {
                fileOutputStream.write((appointment.getDescription() + System.getProperty("line.separator")).getBytes());
                fileOutputStream.write((appointment.getBeginTimeString() + System.getProperty("line.separator")).getBytes());
                fileOutputStream.write((appointment.getEndTimeString() + System.getProperty("line.separator")).getBytes());
            }
            fileOutputStream.close();
        }  catch(IOException ex) {
            Log.d("Error", Objects.requireNonNull(ex.getMessage()));
        }

    }
}