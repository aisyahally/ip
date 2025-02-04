package simba.ui;

/**
 * Thrown when a task description is empty.
 */
public class EmptyException extends Exception {
    public EmptyException(String msg) {
        super(msg);
    }
}
