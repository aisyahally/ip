package simba.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a general task with a name and completion status.
 * The {@code Task} class is the base class for specific task types,
 * eg. {@link ToDo}, {@link Deadline}, and {@link Event}.
 * It provides methods for task management, such as marking the task as done or undone,
 * and formatting its name and date.
 *
 * <p>Example usage:</p>
 * <pre>
 *     Task task = new Task("Example Task");
 *     task.makeDone(); // Marks the task as done
 * </pre>
 */
public abstract class Task {
    private boolean isDone;
    private final String taskName;

    /**
     * Constructs a new Task with the specified name.
     *
     * @param name The name of the task.
     */
    Task(String name) {
        this.isDone = false;
        this.taskName = name;
    }

    /**
     * Marks the task as completed.
     */
    void makeDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    void makeUndone() {
        this.isDone = false;
    }

    /**
     * Retrieves the name of the task.
     *
     * @return The name of the task.
     */
    String getName() {
        return this.taskName;
    }

    /**
     * Returns the type of the task (e.g., "ToDo", "Deadline", "Event").
     *
     * @return The type of the task.
     */
    abstract String getType();

    /**
     * Returns the date associated with the task.
     *
     * @return The date associated with the task.
     */
    abstract LocalDateTime getDate();

    /**
     * Returns the end date associated with the task.
     * This is particularly useful for tasks like events with a start and end time.
     *
     * @return The end date of the task.
     */
    abstract LocalDateTime getEndDate();

    /**
     * Compares this task with another object for equality.
     * The comparison is based on task name and date.
     *
     * @param obj The object to compare with.
     * @return {@code true} if the tasks are equal, {@code false} otherwise.
     */
    public abstract boolean equals(Object obj);

    /**
     * Formats a {@code LocalDateTime} object into a string.
     * The formatted string follows the pattern "dd MMM yyyy HH:mm".
     *
     * @param date The {@code LocalDateTime} object to format.
     * @return A string representing the formatted date.
     */
    protected String stringDate(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
        return date.format(formatter);
    }

    /**
     * Returns a string representation of the task, including its completion status.
     * If the task is completed, it will be prefixed with "[X]", otherwise it will be "[ ]".
     *
     * @return A string representing the task.
     */
    public String toString() {
        if (this.isDone) {
            return "[X] " + this.taskName;
        } else {
            return "[ ] " + this.taskName;
        }
    }
}
