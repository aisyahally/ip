package simba.ui;

import java.util.Comparator;

/**
 * A comparator for comparing {@link Task} objects.
 * The {@code TaskComparator} class compares tasks first by type,
 * then by start date, followed by end date (if applicable),
 * and finally by task name.
 *
 * <p>This comparator is useful for sorting tasks in a way that
 * respects the hierarchy of task type, date, and name.</p>
 *
 * <p>For example, tasks can be sorted first by type ("ToDo", "Deadline", "Event"),
 * then by their associated date and end date (if available),
 * and if those are equal, they are sorted by name.</p>
 */
public class TaskComparator implements Comparator<Task> {

    /**
     * Compares two {@code Task} objects.
     * The comparison is done first by task type,
     * then by the task's date, followed by the end date if applicable,
     * and finally by task name.
     *
     * @param task1 The first task to compare.
     * @param task2 The second task to compare.
     * @return A negative integer, zero, or a positive integer
     *         as the first task is less than, equal to, or greater than the second task.
     */
    public int compare(Task task1, Task task2) {
        if (compareType(task1, task2) != 0) {
            return compareType(task1, task2);
        }
        if (task1.getDate() != null) {
            if (compareDate(task1, task2) != 0) {
                return compareDate(task1, task2);
            }
            if (task1.getEndDate() != null) {
                return compareEndDate(task1, task2);
            }
        }
        return compareName(task1, task2);
    }

    /**
     * Compares the type of two tasks.
     *
     * @param task1 The first task to compare.
     * @param task2 The second task to compare.
     * @return A negative integer, zero, or a positive integer
     *         as the type of the first task is less than, equal to, or greater than the second task's type.
     */
    private int compareType(Task task1, Task task2) {
        return task1.getType().compareTo(task2.getType());
    }

    /**
     * Compares the names of two tasks.
     *
     * @param task1 The first task to compare.
     * @param task2 The second task to compare.
     * @return A negative integer, zero, or a positive integer
     *         as the name of the first task is less than, equal to, or greater than the second task's name.
     */
    private int compareName(Task task1, Task task2) {
        return task1.getName().compareTo(task2.getName());
    }

    /**
     * Compares the start date of two tasks.
     *
     * @param task1 The first task to compare.
     * @param task2 The second task to compare.
     * @return A negative integer, zero, or a positive integer
     *         as the start date of the first task is less than, equal to, or greater than the second task's start date.
     */
    private int compareDate(Task task1, Task task2) {
        return task1.getDate().compareTo(task2.getDate());
    }

    /**
     * Compares the end date of two tasks.
     *
     * @param task1 The first task to compare.
     * @param task2 The second task to compare.
     * @return A negative integer, zero, or a positive integer
     *         as the end date of the first task is less than, equal to, or greater than the second task's end date.
     */
    private int compareEndDate(Task task1, Task task2) {
        return task1.getEndDate().compareTo(task2.getEndDate());
    }
}
