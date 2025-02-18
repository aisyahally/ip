package simba.ui;

import java.time.LocalDateTime;

/**
 * Represents a Deadline task that extends the {@link Task} class.
 * A Deadline task has a name and a deadline by which it must be completed.
 *
 * <p>For example, a Deadline task might look like:
 * <pre>
 *     Deadline myTask = new Deadline("Submit report", "2025-02-10");
 * </pre>
 * The task would be considered a Deadline task with the name "Submit report" and a deadline of "2025-02-10".
 *
 * <p>This class provides a method to return a string representation of the task in the format:
 * {@code [D] <taskName> (by: <deadline>)}.
 */
public class Deadline extends Task {
    private final LocalDateTime deadline;

    /**
     * Initializes a new Deadline instance with the specified name and deadline.
     *
     * @param name The name of the Deadline task.
     * @param deadline The deadline of the task.
     */
    Deadline(String name, LocalDateTime deadline) {
        super(name);
        this.deadline = deadline;
    }

    String getType() {
        return "Deadline";
    }

    LocalDateTime getDate() {
        return this.deadline;
    }

    LocalDateTime getEndDate() {
        return null;
    }

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
     * @return The string representation of the Deadline task.
     */
    public String toString() {
        return "[D] " + super.toString() + " (by: " + super.stringDate(this.deadline) + ")";
    }
}
