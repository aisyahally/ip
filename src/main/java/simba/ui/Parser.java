package simba.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Parses commands to create different task types (ToDo, Deadline, Event) or extract task-related information.
 *
 * <p>This class handles parsing of various task-related commands, such as "todo", "deadline", and "event",
 * and converting them into corresponding task objects. It also provides utilities to extract task indices and
 * format or parse date information from command strings.</p>
 *
 * <p>For example, parsing a command like "event Meeting /from 2025-02-10 0900 /to 2025-02-10 1100" will return
 * an Event task object with start and end times.</p>
 */
public class Parser {
    private final String command;

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
     * This method uses a specific date-time pattern to parse the input string.
     *
     * @param input The date string to parse, in the format "dd-MM-yyyy HHmm".
     * @return The parsed LocalDateTime object representing the provided date and time.
     */
    private static LocalDateTime readDate(String input) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
        return LocalDateTime.parse(input, formatter);
    }

    /**
     * Formats a LocalDateTime object into a string.
     * The formatted string will follow the pattern "dd MMM yyyy HH:mm".
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
     * @throws EmptyException If the command is invalid or the task description is empty.
     */
    Task taskToAdd() throws EmptyException {
        if (this.command.substring(0, 4).equals("todo")) {
            return this.parseToDo();
        } else if (this.command.substring(0, 5).equals("event")) {
            return this.parseEvent();
        } else {
            return this.parseDeadline();
        }
    }

    private ToDo parseToDo() throws EmptyException {
        if (this.command.length() < 6) {
            throw new EmptyException("ToDo");
        }
        return new ToDo(this.command.substring(5));
    }

    private Deadline parseDeadline() throws EmptyException, DateTimeParseException {
        if (this.command.length() < 10) {
            throw new EmptyException("Deadline");
        }
        for (int i = 0; i < this.command.length(); i++) {
            if (this.command.substring(i, i + 1).equals("/")) {
                return new Deadline(command.substring(9, i), stringDate(readDate(command.substring(i + 4))));
            }
        }
        throw new EmptyException("Deadline");
    }

    private Event parseEvent() throws EmptyException, DateTimeParseException {
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
    }

    String wordToFind() {
        return this.command.substring(5);
    }
}
