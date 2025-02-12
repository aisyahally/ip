package simba.ui;

import java.util.Comparator;

public class TaskComparator implements Comparator<Task> {

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

    private int compareType(Task task1, Task task2) {
        return task1.getType().compareTo(task2.getType());
    }

    private int compareName(Task task1, Task task2) {
        return task1.getName().compareTo(task2.getName());
    }

    private int compareDate(Task task1, Task task2) {
        return task1.getDate().compareTo(task2.getDate());
    }

    private int compareEndDate (Task task1, Task task2) {
        return task1.getEndDate().compareTo(task2.getEndDate());
    }
}
