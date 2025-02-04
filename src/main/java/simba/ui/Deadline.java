package simba.ui;

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
    private final String deadline;

    /**
     * Initializes a new Deadline instance with the specified name and deadline.
     *
     * @param name The name of the Deadline task.
     * @param deadline The deadline of the task.
     */
    Deadline(String name, String deadline) {
        super(name);
        this.deadline = deadline;
    }

    /**
     * Returns a string representation of the Deadline task.
     *
     * @return The string representation of the Deadline task.
     */
    public String toString() {
        return "[D] " + super.toString() + " (by: " + this.deadline + ")";
    }
}
