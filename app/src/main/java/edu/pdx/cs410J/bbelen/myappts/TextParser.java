package edu.pdx.cs410J.bbelen.myappts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

/**
 * This class implements the parse() method in the AppointmentBookParser interface.
 * Has a private data member, file, which is set through the class constructor, to
 * determine the file that needs to be parsed into an appointment book.
 *
 * @author Belén Bustamante
 */
public class TextParser implements AppointmentBookParser {
    private File file;

    /**
     * Constructor for TextParser class. Initializes the file object
     * @param file: File object
     */
    TextParser(File file) {
        this.file = file;
    }

    /**
     * Parses appointments from a file and returns an appointment book containing them
     * @return Appointment book holding the appointments parsed from the text file
     * @throws ParserException
     */
    @Override
    public AbstractAppointmentBook parse() throws ParserException {
        String owner, description, beginTime, endTime;

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new ParserException("Error parsing file contents");
        }
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        try {
            owner = bufferedReader.readLine();
        } catch (IOException e) {
            throw new ParserException("Error parsing file contents");
        }

        AbstractAppointmentBook abstractAppointmentBook = new AppointmentBook(owner);
        AppointmentBook appointmentBook = (AppointmentBook) abstractAppointmentBook;

        String line;

        try {
            while ((line = bufferedReader.readLine()) != null) {
                description = line;
                beginTime = bufferedReader.readLine();
                endTime = bufferedReader.readLine();

                AbstractAppointment appointment = new Appointment(description, beginTime, endTime);
                appointmentBook.addAppointment(appointment);
            }
            fileInputStream.close();
            bufferedReader.close();

        } catch (IOException e) {
            throw new ParserException("Error parsing file contents");
        }

        return appointmentBook;
    }

}