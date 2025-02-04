package simba.ui;

public class Task {
    private boolean isDone;
    private String taskName;

    /**
     * Initializes a new Task instance with the specified name.
     *
     * @param name The name of the task.
     */
    Task(String name) {
        this.isDone = false;
        this.taskName = name;
    }

    /**
     * Marks the task as done.
     */
    void makeDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    void makeUndone() {
        this.isDone = false;
    }

    /**
     * Returns a string representation of the task.
     *
     * @return The string representation of the task.
     */
    String getName() {
        return this.taskName;
    }

    public String toString() {
        if (this.isDone) {
            return "[X] " + this.taskName;
        } else {
            return "[ ] " + this.taskName;
        }
    }
}