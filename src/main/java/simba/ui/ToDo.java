package simba.ui;

import java.time.LocalDateTime;

/**
 * Represents a ToDo task, which is a type of task that doesn't have a specific deadline or time range.
 * Inherits from the {@code Task} class and adds functionality to display the task as a ToDo item.
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

    /**
     * Returns the type of the task as a ToDo task.
     *
     * @return A string indicating the task type, which is "ToDo".
     */
    String getType() {
        return "ToDo";
    }

    /**
     * Returns the start date of the task.
     * Since ToDo tasks do not have a specific start date, this method returns {@code null}.
     *
     * @return {@code null}, as ToDo tasks do not have a specific start date.
     */
    LocalDateTime getDate() {
        return null;
    }

    /**
     * Returns the end date of the task.
     * Since ToDo tasks do not have a specific end date, this method returns {@code null}.
     *
     * @return {@code null}, as ToDo tasks do not have a specific end date.
     */
    LocalDateTime getEndDate() {
        return null;
    }

    /**
     * Compares this ToDo task to another object.
     * Two ToDo tasks are considered equal if they have the same name.
     *
     * @param obj The object to compare to this ToDo task.
     * @return {@code true} if the two tasks are equal, {@code false} otherwise.
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        ToDo objAsToDo = (ToDo) obj;
        return this.getName().equals(objAsToDo.getName());
    }

    /**
     * Returns a string representation of the ToDo task.
     * The string representation includes the task type and its completion status.
     *
     * @return The string representation of the ToDo task in the format "[T] [ ] TaskName" or "[T] [X] TaskName".
     */
    public String toString() {
        return "[T] " + super.toString();
    }
}
