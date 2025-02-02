package simba.ui;

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Simba {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Initializes a new Simba instance with the specified file path.
     *
     * @param filePath The path to the file where tasks are stored.
     */
    public Simba(String filePath) {
        this.storage = new Storage(filePath);
        this.tasks = new TaskList(new ArrayList<Task>());
        this.ui = new Ui(this.storage, this.tasks);
    }

    /**
     * The main entry point for the Simba application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new Simba("simba.txt").run();
    }

    /**
     * Runs the Simba application, displaying a welcome message and processing user commands.
     */
    public void run() {
        System.out.println("\tHello I am Simba :D");
        System.out.println("\tHow can I help you?");
        Scanner sc = new Scanner(System.in);
        while (true) {
            String command = sc.nextLine();
            this.ui.readCommand(command);
            if (this.ui.isBye()) {
                break;
            }
        }
    }
}

class Storage {
    private String filePath;

    /**
     * Initializes a new Storage instance with the specified file path.
     *
     * @param filePath The path to the file where tasks are stored.
     */
    Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Prints the contents of the file to the console.
     *
     * @throws FileNotFoundException If the file does not exist.
     */
    void printFile() throws FileNotFoundException {
        File file = new File(this.filePath);
        Scanner sc = new Scanner(file);
        if (!sc.hasNext()) {
            System.out.println("\tTask list is empty");
        }
        while (sc.hasNext()) {
            System.out.println("\t" + sc.nextLine());
        }
    }

    /**
     * Writes the list of tasks to the file.
     *
     * @param list The list of tasks to write to the file.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    void writeToFile(ArrayList<Task> list) throws IOException {
        FileWriter fw = new FileWriter(this.filePath);
        for (int i = 0; i < list.size(); i++) {
            int idx = i + 1;
            fw.write(idx + ": " + list.get(i) + "\n");
        }
        fw.close();
    }

}


class TaskList {
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
        if (idx >= this.list.size()) {
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
        if (idx >= this.list.size()) {
            System.out.println("\tTask of this number does not exist");
            return;
        }
        this.list.get(idx - 1).makeUndone();
        System.out.println("\tOkay! This task is not done: " + list.get(idx - 1));
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

class Parser {
    private String command;

    /**
     * Initializes a new Parser instance with the specified command.
     *
     * @param command The command in a String.
     */
    Parser(String command) {
        this.command = command;
    }

    /**
     * Parses a date string into a LocalDateTime object.
     *
     * @param input The date string to parse.
     * @return The parsed LocalDateTime object.
     */
    private static LocalDateTime readDate(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
        return LocalDateTime.parse(input, formatter);
    }

    /**
     * Formats a LocalDateTime object into a string.
     *
     * @param date The LocalDateTime object to format.
     * @return The formatted date string.
     */
    private static String stringDate(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
        return date.format(formatter);
    }

    /**
     * Extracts the index of the task to delete from the command.
     *
     * @return The index of the task to delete.
     */
    int idxToDelete() {
        return Integer.parseInt(this.command.substring(this.command.length() - 1));
    }

    /**
     * Parses the command and creates a new task to add.
     *
     * @return The task to add.
     * @throws Exception If the command is invalid or the task description is empty.
     */
    Task taskToAdd() throws Exception {
        if (this.command.contains("todo")) {
            if (this.command.length() < 5) {
                throw new EmptyException("ToDo");
            }
            return new ToDo(this.command.substring(5));
        } else if (this.command.contains("deadline")) {
            if (this.command.length() < 9) {
                throw new EmptyException("Deadline");
            }
            for (int i = 0; i < this.command.length(); i++) {
                if (this.command.substring(i, i + 1).equals("/")) {
                    return new Deadline(command.substring(9, i), stringDate(readDate(command.substring(i + 4))));
                }
            }
            throw new EmptyException("Deadline");
        } else if (this.command.contains("event")) {
            if (this.command.length() < 6) {
                throw new EmptyException("Event");
            }
            int startIdx = 0;
            int endIdx = 0;
            for (int i = 0; i < this.command.length(); i++) {
                if (this.command.substring(i, i + 1).equals("/")) {
                    if (this.command.substring(i + 1, i + 2).equals("f")) {
                        startIdx = i + 6;
                    } else {
                        endIdx = i + 4;
                    }
                }
            }
            if (startIdx == 0 | endIdx == 0) {
                throw new EmptyException("Event");
            }
            return new Event(command.substring(6, startIdx - 6),
                    stringDate(readDate(command.substring(startIdx, endIdx - 5))),
                    stringDate(readDate(command.substring(endIdx))));
        } else {
            throw new NonsenseException();
        }
    }
}

class Ui {
    private boolean bye;
    private Storage storage;
    private TaskList tasks;

    /**
     * Initializes a new Ui instance with the specified storage and task list.
     *
     * @param storage The storage instance.
     * @param tasks The task list instance.
     */
    Ui(Storage storage, TaskList tasks) {
        this.bye = false;
        this.storage = storage;
        this.tasks = tasks;
    }

    /**
     * Reads and processes a user command.
     *
     * @param command The user command to process.
     */
    void readCommand(String command) {
        try {
            this.storage.writeToFile(this.tasks.getList());
            if (command.equals("hello")) {
                System.out.println("\tHello! What would you like me to do today?");
            } else if (command.equals("help")) {
                System.out.println("\tHere are the list of commands:");
                System.out.println("\t\t- hello");
                System.out.println("\t\t- list");
                System.out.println("\t\t- todo [task description]");
                System.out.println("\t\t- deadline [task description] /by [dd-mm-yyyy hhmm]");
                System.out.println("\t\t- event [task description] /from [dd-mm-yyyy hhmm] /to [dd-mm-yyyy hhmm]");
                System.out.println("\t\t- mark [task number] / unmark [task number]");
                System.out.println("\t\t- delete [task number]");
                System.out.println("\t\t- bye");
            } else if (command.equals("bye")) {
                System.out.println("\tBye-bye!");
                this.bye = true;
            } else if (command.equals("list")) {
                this.storage.printFile();
            } else if (command.contains("mark")) {
                int i = Integer.parseInt(command.substring(command.length() - 1));
                if (command.contains("unmark")) {
                    this.tasks.unmarkTask(i);
                } else {
                    this.tasks.markTask(i);
                }
            } else if (command.contains("delete")) {
                this.tasks.deleteTask(new Parser(command).idxToDelete());
            } else {
                this.tasks.addTask(new Parser(command).taskToAdd());
            }
        } catch (EmptyException e) {
            System.out.println("\tOh no! " + e.getMessage() + " description is wrong");
        } catch (NonsenseException e) {
            System.out.println("\tOh dear :( I don't understand you");
        } catch (IOException e) {
            System.out.println("\tSomething went wrong with the file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\tPlease refer to the proper command formats");
        }
    }

    /**
     * Checks if the user has entered the "bye" command.
     *
     * @return True if the user has entered the "bye" command, false otherwise.
     */
    boolean isBye() {
        return this.bye;
    }
}

class Task {
    private boolean isDone;
    private String taskName;

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
    public String toString() {
        if (this.isDone) {
            return "[X] " + this.taskName;
        } else {
            return "[ ] " + this.taskName;
        }
    }
}

class ToDo extends Task {

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

class Deadline extends Task {
    private String deadline;

    /**
     * Initializes a new Deadline instance with the specified name and deadline.
     *
     * @param name The name of the Deadline task.
     * @param deadline The deadline of the task.
     */
    Deadline(String name, String deadline) {
        super(name);
        this.deadline = deadline;
    }

    /**
     * Returns a string representation of the Deadline task.
     *
     * @return The string representation of the Deadline task.
     */
    public String toString() {
        return "[D] " + super.toString() + " (by: " + this.deadline + ")";
    }
}

class Event extends Task {
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

/**
 * Thrown when a task description is empty.
 */
class EmptyException extends Exception {
    public EmptyException(String msg) {
        super(msg);
    }
}

/**
 * Thrown when an invalid command is entered.
 */
class NonsenseException extends Exception {
}