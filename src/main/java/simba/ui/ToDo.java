package simba.ui;

import java.time.LocalDateTime;

/**
 * Represents a ToDo task, which is a type of task that doesn't have a specific deadline or time range.
 * Inherits from the Task class and adds functionality to display the task as a ToDo item.
 */
public class ToDo extends Task {

    /**
     * Initializes a new ToDo instance with the specified name.
     *
     * @param name The name of the ToDo task.
     */
    ToDo(String name) {
        super(name);
    }

    String getType() {
        return "ToDo";
    }

    LocalDateTime getDate() {
        return null;
    }

    LocalDateTime getEndDate() {
        return null;
    }

    /**
     * Returns a string representation of the ToDo task.
     *
     * @return The string representation of the ToDo task.
     */
    public String toString() {
        return "[T] " + super.toString();
    }
}
