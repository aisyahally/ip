package simba.ui;

import java.time.LocalDateTime;


/**
 * Represents an Event task that extends the {@link Task} class.
 * An Event task has a name, a start time, and an end time.
 *
 * <p>For example, an Event task might look like:
 * <pre>
 *     Event meeting = new Event("Team Meeting",
 *                                LocalDateTime.of(2025, 2, 5, 10, 0),
 *                                LocalDateTime.of(2025, 2, 5, 11, 0));
 * </pre>
 * This creates an Event task with the name "Team Meeting", a start time of "2025-02-05 10:00",
 * and an end time of "2025-02-05 11:00".
 *
 * <p>This class provides methods to retrieve task details and overrides {@code toString()} for display purposes.
 */
public class Event extends Task {
    private final LocalDateTime start;
    private final LocalDateTime end;

    /**
     * Initializes a new Event instance with the specified name, start time, and end time.
     *
     * @param name  The name of the Event task.
     * @param start The start time of the event as a {@code LocalDateTime} object.
     * @param end   The end time of the event as a {@code LocalDateTime} object.
     */
    Event(String name, LocalDateTime start, LocalDateTime end) {
        super(name);
        this.start = start;
        this.end = end;
    }

    /**
     * Returns the type of this task, which is "Event".
     *
     * @return The string "Event".
     */
    String getType() {
        return "Event";
    }

    /**
     * Retrieves the start time of the event.
     *
     * @return The start time as a {@code LocalDateTime} object.
     */
    LocalDateTime getDate() {
        return this.start;
    }


    /**
     * Retrieves the end time of the event.
     *
     * @return The end time as a {@code LocalDateTime} object.
     */
    LocalDateTime getEndDate() {
        return this.end;
    }

    /**
     * Checks if this Event task is equal to another object.
     * Two Event tasks are considered equal if they have the same name, start time, and end time.
     *
     * @param obj The object to compare.
     * @return {@code true} if the given object is an Event with the same name, start time, and end time,
     *         otherwise {@code false}.
     */
    @Override
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
     * @return A formatted string in the format {@code [E] <taskName> (from: <start> to: <end>)}.
     */
    @Override
    public String toString() {
        return "[E] " + super.toString() + " (from: " + super.stringDate(this.start)
                + " to: " + super.stringDate(this.end) + ")";
    }
}
