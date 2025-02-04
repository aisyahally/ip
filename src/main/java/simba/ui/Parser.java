package simba.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Parser {
    private String command;

    /**
     * Initializes a new Parser instance with the specified command.
     *
     * @param command The command in a String.
     */
    Parser(String command) {
        this.command = command;
    }

    /**
     * Parses a date string into a LocalDateTime object.
     *
     * @param input The date string to parse.
     * @return The parsed LocalDateTime object.
     */
    private static LocalDateTime readDate(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
        return LocalDateTime.parse(input, formatter);
    }

    /**
     * Formats a LocalDateTime object into a string.
     *
     * @param date The LocalDateTime object to format.
     * @return The formatted date string.
     */
    private static String stringDate(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
        return date.format(formatter);
    }

    /**
     * Extracts the index of the task to delete from the command.
     *
     * @return The index of the task to delete.
     */
    int idxToDelete() {
        return Integer.parseInt(this.command.substring(this.command.length() - 1));
    }

    /**
     * Parses the command and creates a new task to add.
     *
     * @return The task to add.
     * @throws Exception If the command is invalid or the task description is empty.
     */
    Task taskToAdd() throws Exception {
        if (this.command.substring(0, 4).equals("todo")) {
            if (this.command.length() < 5) {
                throw new EmptyException("ToDo");
            }
            return new ToDo(this.command.substring(5));
        } else if (this.command.substring(0, 8).equals("deadline")) {
            if (this.command.length() < 10) {
                throw new EmptyException("Deadline");
            }
            for (int i = 0; i < this.command.length(); i++) {
                if (this.command.substring(i, i + 1).equals("/")) {
                    return new Deadline(command.substring(9, i), stringDate(readDate(command.substring(i + 4))));
                }
            }
            throw new EmptyException("Deadline");
        } else if (this.command.substring(0, 5).equals("event")) {
            if (this.command.length() < 7) {
                throw new EmptyException("Event");
            }
            int startIdx = 0;
            int endIdx = 0;
            for (int i = 0; i < this.command.length(); i++) {
                if (this.command.substring(i, i + 1).equals("/")) {
                    if (this.command.substring(i + 1, i + 2).equals("f")) {
                        startIdx = i + 6;
                    } else {
                        endIdx = i + 4;
                    }
                }
            }
            if (startIdx == 0 | endIdx == 0) {
                throw new EmptyException("Event");
            }
            return new Event(command.substring(6, startIdx - 6),
                    stringDate(readDate(command.substring(startIdx, endIdx - 5))),
                    stringDate(readDate(command.substring(endIdx))));
        } else {
            throw new NonsenseException();
        }
    }

    String wordToFind() {
        return this.command.substring(5);
    }
}