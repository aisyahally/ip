package simba.ui;

/**
 * Manages task storage, including reading from and writing to a file.
 * The Storage class handles operations related to saving tasks to a file
 * and loading tasks from the file.
 *
 * <p>It supports the following functionalities:
 * <ul>
 *     <li>Reading task data from a specified file path.</li>
 *     <li>Writing task data (e.g., task list) to a specified file.</li>
 * </ul>
 * </p>
 *
 * <p>For example, a task list can be printed to the console or saved
 * to the file by using the methods in this class.</p>
 */
public class Task {
    private boolean isDone;
    private final String taskName;

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

    /**
     * Returns a string representation of the task.
     * The string includes the task's completion status and its name.
     *
     * @return A string representing the task, with "[X]" for done tasks and "[ ]" for pending tasks.
     */
    public String toString() {
        if (this.isDone) {
            return "[X] " + this.taskName;
        } else {
            return "[ ] " + this.taskName;
        }
    }
}
