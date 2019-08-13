package com.cs410j.myappts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

/**
 * This class implements the parse() method in the AppointmentBookParser interface.
 * Has a private data member, filePath, which is set through the class constructor, to
 * determine the file that needs to be parsed into an appointment book.
 *
 * @author Belén Bustamante
 */
public class TextParser implements AppointmentBookParser {
    private String filePath;

    /**
     * Constructor for TextParser class. Initializes the file path name
     * @param fileName: String holding the name of the file
     */
    TextParser(String fileName) {
        this.filePath = System.getProperty("user.dir") + "/" + fileName;
    }

    /**
     * Parses appointments from a file and returns an appointment book containing them
     * @return Appointment book holding the appointments parsed from the text file
     * @throws ParserException
     */
    @Override
    public AbstractAppointmentBook parse() throws ParserException {
        Scanner scanner;
        File file;
        FileReader fr;
        String contents = "";

        try {
            file = new File(filePath);
            scanner = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            throw new ParserException("File not found");
        }

        scanner.useDelimiter("[|\\n]");

        try {
            contents = new String(Files.readAllBytes(Paths.get(filePath)));

        } catch (IOException e) {
            throw new ParserException("Error parsing contents");
        }

        if (!contents.contains("|"))
            throw new ParserException("Improperly formatted file");


        String owner, description, beginTime, endTime;

        // File exists and is not empty, begin parsing contents
        owner = scanner.next();
        AbstractAppointmentBook abstractAppointmentBook = new AppointmentBook(owner);
        AppointmentBook appointmentBook = (AppointmentBook) abstractAppointmentBook;

        while (scanner.hasNext()) {
            try {
                description = scanner.next();
                beginTime = scanner.next();
                endTime = scanner.next();

                AbstractAppointment appointment = new Appointment(description, beginTime, endTime);

                appointmentBook.addAppointment(appointment);
            } catch (NoSuchElementException | NumberFormatException e) {
                throw new ParserException("Improperly formatted file. " + e.getMessage() + "Acceptable format is: mm/dd/yyyy hh:mm AM");
            }
        }
        scanner.close();

        return appointmentBook;
    }

}