package simba.ui;

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
     * Returns a string representation of the ToDo task.
     *
     * @return The string representation of the ToDo task.
     */
    public String toString() {
        return "[T] " + super.toString();
    }
}