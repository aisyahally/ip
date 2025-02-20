package simba.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import exception.ui.EmptyException;
import exception.ui.InvalidEventDateException;

/**
 * The {@code Parser} class is responsible for interpreting user commands
 * and converting them into task objects such as {@link ToDo}, {@link Deadline},
 * and {@link Event}. It also extracts relevant task-related information.
 *
 * <p>This class supports:
 * <ul>
 *     <li>Creating tasks from user input</li>
 *     <li>Extracting task indices for deletion</li>
 *     <li>Parsing date strings into {@link LocalDateTime} objects</li>
 * </ul>
 */
public class Parser {
    private final String command;

    /**
     * Constructs a {@code Parser} object with the given command string.
     *
     * @param command The command to be parsed.
     */
    Parser(String command) {
        this.command = command;
    }

    /**
     * Parses a date string into a {@link LocalDateTime} object.
     *
     * @param input The date string in the format "dd-MM-yyyy HHmm".
     * @return A {@link LocalDateTime} object representing the parsed date and time.
     * @throws DateTimeParseException If the input string is not in the expected format.
     */
    private static LocalDateTime readDate(String input) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
        return LocalDateTime.parse(input, formatter);
    }

    /**
     * Extracts the index of the task to be marked, unmarked or deleted from the command.
     *
     * @return The task index as an integer.
     */
    int idxToUse() {
        return Integer.parseInt(this.command.substring(this.command.length() - 1));
    }

    /**
     * Parses the command and creates a corresponding task object.
     *
     * @return A task object of type {@link ToDo}, {@link Deadline}, or {@link Event}.
     * @throws EmptyException If the command is invalid or lacks a description.
     * @throws DateTimeParseException If the date format is incorrect.
     * @throws InvalidEventDateException If an event's start date is after the end date.
     */
    Task taskToAdd() throws EmptyException, DateTimeParseException, InvalidEventDateException {
        if (this.command.substring(0, 4).equals("todo")) {
            return this.parseToDo();
        } else if (this.command.substring(0, 5).equals("event")) {
            return this.parseEvent();
        } else {
            return this.parseDeadline();
        }
    }

    /**
     * Parses a "todo" command and creates a {@link ToDo} task.
     *
     * @return A {@link ToDo} task instance.
     * @throws EmptyException If the task description is empty.
     */
    private ToDo parseToDo() throws EmptyException {
        if (this.command.length() < 6) {
            throw new EmptyException("ToDo");
        }
        return new ToDo(this.command.substring(5));
    }

    /**
     * Parses a "deadline" command and creates a {@link Deadline} task.
     *
     * @return A {@link Deadline} task instance.
     * @throws EmptyException If the task description or deadline is missing.
     * @throws DateTimeParseException If the deadline format is incorrect.
     */
    private Deadline parseDeadline() throws EmptyException, DateTimeParseException {
        if (this.command.length() < 10) {
            throw new EmptyException("Deadline");
        }
        for (int i = 0; i < this.command.length(); i++) {
            if (this.command.substring(i, i + 1).equals("/")) {
                return new Deadline(command.substring(9, i), readDate(command.substring(i + 4)));
            }
        }
        throw new EmptyException("Deadline");
    }

    /**
     * Parses an "event" command and creates an {@link Event} task.
     *
     * @return An {@link Event} task instance.
     * @throws EmptyException If the task description or event times are missing.
     * @throws DateTimeParseException If the date format is incorrect.
     * @throws InvalidEventDateException If the event's start time is after the end time.
     */
    private Event parseEvent() throws EmptyException, DateTimeParseException, InvalidEventDateException {
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

        LocalDateTime startDate = readDate(command.substring(startIdx, endIdx - 5));
        LocalDateTime endDate = readDate(command.substring(endIdx));

        if (startDate.isAfter(endDate)) {
            throw new InvalidEventDateException();
        }

        return new Event(command.substring(6, startIdx - 6), startDate, endDate);
    }

    /**
     * Extracts the search keyword from a "find" command.
     *
     * @return The keyword to search for.
     */
    String wordToFind() {
        return this.command.substring(5);
    }
}
