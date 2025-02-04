package simba.ui;

public class Event extends Task {
    private String start;
    private String end;

    /**
     * Initializes a new Event instance with the specified name, start time, and end time.
     *
     * @param name The name of the Event task.
     * @param start The start time of the event.
     * @param end The end time of the event.
     */
    Event(String name, String start, String end) {
        super(name);
        this.start = start;
        this.end = end;
    }

    /**
     * Returns a string representation of the Event task.
     *
     * @return The string representation of the Event task.
     */
    public String toString() {
        return "[E] " + super.toString() + " (from: " + this.start + " to: " + this.end + ")";
    }
}