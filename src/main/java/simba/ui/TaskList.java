package simba.ui;

import java.util.ArrayList;

import exception.ui.DuplicateTaskException;

/**
 * Represents a list of tasks, providing methods to manipulate and manage tasks.
 * The TaskList class allows adding, deleting, marking tasks as done or undone,
 * and searching for tasks in the list.
 *
 * <p>It supports the following functionalities:
 * <ul>
 *     <li>Adding tasks to the list.</li>
 *     <li>Deleting tasks from the list.</li>
 *     <li>Marking tasks as done or undone.</li>
 *     <li>Searching for tasks containing a specific word.</li>
 * </ul>
 * </p>
 */
public class TaskList {
    private ArrayList<Task> list;

    /**
     * Initializes a new TaskList instance with an empty list of tasks.
     */
    TaskList() {
        this.list = new ArrayList<Task>();
    }

    /**
     * Deletes a task from the list at the specified index.
     *
     * @param idx The index of the task to delete.
     */
    String deleteTaskAsString(int idx) {
        if (idx > this.list.size()) {
            return "Task of this number does not exist";
        }
        String result = "Deleted task:\n"
                + idx + ". " + list.get(idx - 1) + "\n";
        this.list.remove(idx - 1);
        result += "Now you have " + list.size() + " task(s) in the list";
        return result;
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to add.
     */
    String addTaskAsString(Task task) throws DuplicateTaskException {

        for (int i = 0; i < list.size(); i++) {
            if (task.equals(list.get(i))) {
                throw new DuplicateTaskException();
            }
        }

        this.list.add(task);
        String result = "Added task:\n"
                + list.size() + ". " + list.get(list.size() - 1) + "\n";
        result += "Now you have " + list.size() + " task(s) in the list";
        list.sort(new TaskComparator());
        return result;
    }

    /**
     * Marks a task as done at the specified index.
     *
     * @param idx The index of the task to mark as done.
     */
    String markTaskAsString(int idx) {
        if (idx > this.list.size()) {
            return "Task of this number does not exist";
        }
        this.list.get(idx - 1).makeDone();
        return "Alright! This task is done:\n"
                + idx + ". " + list.get(idx - 1);
    }
    /**
     * Marks a task as not done at the specified index.
     *
     * @param idx The index of the task to mark as not done.
     */
    String unmarkTaskAsString(int idx) {
        if (idx > this.list.size()) {
            return "Task of this number does not exist";
        }
        this.list.get(idx - 1).makeUndone();
        return "Okay! This task is not done:\n"
                + idx + ". " + list.get(idx - 1);
    }

    String findTaskAsString(String word) {
        boolean found = false;
        String result = "Here are the matching task(s):\n";
        for (int i = 0; i < this.list.size(); i++) {
            if (this.list.get(i).getName().contains(word)) {
                int idx = i + 1;
                result += idx + ". " + list.get(i) + "\n";
                found = true;
            }
        }
        if (!found) {
            return "There are no matching tasks in the list";
        } else {
            return result;
        }
    }

    /**
     * Returns the list of tasks.
     *
     * @return The list of tasks.
     */
    ArrayList<Task> getList() {
        return this.list;
    }
}
