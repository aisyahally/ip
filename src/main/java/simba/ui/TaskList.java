package simba.ui;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> list;

    /**
     * Initializes a new TaskList instance with the specified list of tasks.
     *
     * @param list The list of tasks.
     */
    TaskList(ArrayList<Task> list) {
        this.list = list;
    }

    /**
     * Deletes a task from the list at the specified index.
     *
     * @param idx The index of the task to delete.
     */
    void deleteTask(int idx) {
        if (idx >= this.list.size()) {
            System.out.println("\tTask of this number does not exist");
            return;
        }
        System.out.println("\tDeleted task: " + list.get(idx-1));
        this.list.remove(idx-1);
        System.out.println("\tNow you have " + list.size() + " task(s) in the list");
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to add.
     */
    void addTask(Task task) {
        this.list.add(task);
        System.out.println("\tAdded: " + list.get(list.size() - 1));
        System.out.println("\tNow you have " + list.size() + " task(s) in the list");
    }

    /**
     * Marks a task as done at the specified index.
     *
     * @param idx The index of the task to mark as done.
     */
    void markTask(int idx) {
        if (idx > this.list.size()) {
            System.out.println("\tTask of this number does not exist");
            return;
        }
        this.list.get(idx - 1).makeDone();
        System.out.println("\tAlright! This task is done: " + list.get(idx - 1));
    }
    /**
     * Marks a task as not done at the specified index.
     *
     * @param idx The index of the task to mark as not done.
     */
    void unmarkTask(int idx) {
        if (idx > this.list.size()) {
            System.out.println("\tTask of this number does not exist");
            return;
        }
        this.list.get(idx - 1).makeUndone();
        System.out.println("\tOkay! This task is not done: " + list.get(idx - 1));
    }

    void findTask(String word) {
        ArrayList<Task> listToPrint = new ArrayList<Task>();
        for (int i = 0; i < this.list.size(); i++) {
            if (this.list.get(i).getName().contains(word)) {
                listToPrint.add(this.list.get(i));
            }
        }
        if (listToPrint.size() == 0) {
            System.out.println("\tThere are no matching tasks in the list");
        } else {
            System.out.println("\tHere are the matching task(s):");
            for (int i = 0; i < listToPrint.size(); i++) {
                int idx = i + 1;
                System.out.println("\t" + idx + ". " + listToPrint.get(i));
            }
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