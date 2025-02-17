package simba.ui;

import java.time.LocalDateTime;

/**
 * Represents an Event task that extends the {@link Task} class.
 * An Event task has a name, a start time, and an end time.
 *
 * <p>For example, an Event task might look like:
 * <pre>
 *     Event meeting = new Event("Team Meeting", "2025-02-05 10:00", "2025-02-05 11:00");
 * </pre>
 * The task would be considered an Event task with the name "Team Meeting", a start time of "2025-02-05 10:00"
 * and an end time of "2025-02-05 11:00".
 *
 * <p>This class provides a method to return a string representation of the task in the format:
 * {@code [E] <taskName> (from: <start> to: <end>)}.
 */
public class Event extends Task {
    private final LocalDateTime start;
    private final LocalDateTime end;

    /**
     * Initializes a new Event instance with the specified name, start time, and end time.
     *
     * @param name The name of the Event task.
     * @param start The start time of the event.
     * @param end The end time of the event.
     */
    Event(String name, LocalDateTime start, LocalDateTime end) {
        super(name);
        this.start = start;
        this.end = end;
    }

    String getType() {
        return "Event";
    }

    LocalDateTime getDate() {
        return this.start;
    }

    LocalDateTime getEndDate() {
        return this.end;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Event objAsEvent = (Event) obj;
        return this.getName().equals(objAsEvent.getName())
                && this.getDate().equals(objAsEvent.getDate())
                && this.getEndDate().equals(objAsEvent.getDate());
    }

    /**
     * Returns a string representation of the Event task.
     *
     * @return The string representation of the Event task.
     */
    public String toString() {
        return "[E] " + super.toString() + " (from: " + super.stringDate(this.start)
                + " to: " + super.stringDate(this.end) + ")";
    }
}
