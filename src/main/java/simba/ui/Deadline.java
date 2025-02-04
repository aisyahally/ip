package simba.ui;

public class Deadline extends Task {
    private String deadline;

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