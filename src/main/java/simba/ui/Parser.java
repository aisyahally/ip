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
     * @throws DateTimeParseException If the date string cannot be parsed into a LocalDateTime object.
     */
    private static LocalDateTime readDate(String input) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
        return LocalDateTime.parse(input, formatter);
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
     * The method checks if the command is related to "todo", "event", or "deadline"
     * and calls the respective method to create the corresponding task.
     *
     * @return The task to add (ToDo, Deadline, or Event).
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

    /**
     * Parses a "todo" command and creates a ToDo task.
     *
     * @return A new ToDo task created from the command description.
     * @throws EmptyException If the "todo" command does not contain a description.
     */
    private ToDo parseToDo() throws EmptyException {
        if (this.command.length() < 6) {
            throw new EmptyException("ToDo");
        }
        return new ToDo(this.command.substring(5));
    }

    /**
     * Parses a "deadline" command and creates a Deadline task.
     * The method extracts the task description and the deadline date/time.
     *
     * @return A new Deadline task created from the command description and deadline time.
     * @throws EmptyException If the "deadline" command does not contain a description or a date.
     * @throws DateTimeParseException If the date string cannot be parsed into a LocalDateTime object.
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
     * Parses an "event" command and creates an Event task.
     * The method extracts the task description and the event's start and end times.
     *
     * @return A new Event task created from the command description and event times.
     * @throws EmptyException If the "event" command does not contain a description or time information.
     * @throws DateTimeParseException If the date string cannot be parsed into a LocalDateTime object.
     */
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
                readDate(command.substring(startIdx, endIdx - 5)),
                readDate(command.substring(endIdx)));
    }

    /**
     * Extracts the word or keyword to search for in the "find" command.
     * The method assumes that the "find" command starts with the keyword "find" followed by the search word.
     *
     * @return The keyword to search for, which is the portion of the command after "find ".
     */
    String wordToFind() {
        return this.command.substring(5);
    }
}
