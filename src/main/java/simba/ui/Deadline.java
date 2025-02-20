package simba.ui;

import java.time.LocalDateTime;

/**
 * Represents a Deadline task that extends the {@link Task} class.
 * A Deadline task has a name and a deadline by which it must be completed.
 *
 * <p>For example, a Deadline task might look like:
 * <pre>
 *     Deadline myTask = new Deadline("Submit report", LocalDateTime.of(2025, 2, 10, 23, 59));
 * </pre>
 * The task would be considered a Deadline task with the name "Submit report" and a deadline of "2025-02-10 23:59".
 *
 * <p>This class provides methods to retrieve task details and override {@code toString()} for display purposes.
 */
public class Deadline extends Task {
    private final LocalDateTime deadline;

    /**
     * Initializes a new Deadline instance with the specified name and deadline.
     *
     * @param name     The name of the Deadline task.
     * @param deadline The deadline of the task as a {@code LocalDateTime} object.
     */
    Deadline(String name, LocalDateTime deadline) {
        super(name);
        this.deadline = deadline;
    }

    /**
     * Returns the type of this task, which is "Deadline".
     *
     * @return The string "Deadline".
     */
    String getType() {
        return "Deadline";
    }

    /**
     * Retrieves the deadline of the task.
     *
     * @return The deadline as a {@code LocalDateTime} object.
     */
    LocalDateTime getDate() {
        return this.deadline;
    }

    /**
     * Returns {@code null} since a Deadline task does not have an explicit end date.
     *
     * @return {@code null}, as only the deadline is relevant.
     */
    LocalDateTime getEndDate() {
        return null;
    }

    /**
     * Checks if this Deadline task is equal to another object.
     * Two Deadline tasks are considered equal if they have the same name and deadline.
     *
     * @param obj The object to compare.
     * @return {@code true} if the given object is a Deadline with the same name and deadline, otherwise {@code false}.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Deadline objAsDeadline = (Deadline) obj;
        return this.getName().equals(objAsDeadline.getName())
                && this.getDate().equals(objAsDeadline.getDate());
    }

    /**
     * Returns a string representation of the Deadline task.
     *
     * @return A formatted string in the format {@code [D] <taskName> (by: <deadline>)}.
     */
    @Override
    public String toString() {
        return "[D] " + super.toString() + " (by: " + super.stringDate(this.deadline) + ")";
    }
}
