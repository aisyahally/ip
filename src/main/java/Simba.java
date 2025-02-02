import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Simba {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Simba(String filePath) {
        this.storage = new Storage(filePath);
        this.tasks = new TaskList(new ArrayList<Task>());
        this.ui = new Ui(this.storage, this.tasks);
    }

    public static void main(String[] args) {
        new Simba("simba.txt").run();
    }

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

    Storage(String filePath) {
        this.filePath = filePath;
    }

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

    TaskList(ArrayList<Task> list) {
        this.list = list;
    }

    void deleteTask(int idx) {
        System.out.println("\tDeleted task: " + list.get(idx-1));
        this.list.remove(idx-1);
        System.out.println("\tNow you have " + list.size() + " task(s) in the list");
    }

    void addTask(Task task) {
        this.list.add(task);
        System.out.println("\tAdded: " + list.get(list.size() - 1));
        System.out.println("\tNow you have " + list.size() + " task(s) in the list");
    }

    void markTask(int idx) {
        this.list.get(idx - 1).makeDone();
        System.out.println("\tAlright! This task is done: " + list.get(idx - 1));
    }

    void unmarkTask(int idx) {
        this.list.get(idx - 1).makeUndone();
        System.out.println("\tOkay! This task is not done: " + list.get(idx - 1));
    }

    ArrayList<Task> getList() {
        return this.list;
    }
}

class Parser {
    private String command;

    Parser(String command) {
        this.command = command;
    }

    private static LocalDateTime readDate(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
        return LocalDateTime.parse(input, formatter);
    }

    private static String stringDate(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
        return date.format(formatter);
    }

    int idxToDelete() {
        return Integer.parseInt(this.command.substring(this.command.length() - 1));
    }

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

    Ui(Storage storage, TaskList tasks) {
        this.bye = false;
        this.storage = storage;
        this.tasks = tasks;
    }

    void readCommand(String command) {
        try {
            this.storage.writeToFile(this.tasks.getList());
            if (command.equals("hello")) {
                System.out.println("\tHello! What would you like me to do today?");
            } else if (command.equals("help")) {
                System.out.println("\t Here are the list of commands:");
                System.out.println("\t \t - hello");
                System.out.println("\t \t - list");
                System.out.println("\t \t - todo [task description]");
                System.out.println("\t \t - deadline [task description] /by [dd-mm-yyyy hhmm]");
                System.out.println("\t \t - event [task description] /from [dd-mm-yyyy hhmm] /to [dd-mm-yyyy hhmm]");
                System.out.println("\t \t - mark [task number] / unmark [task number]");
                System.out.println("\t \t - delete [task number]");
                System.out.println("\t \t - bye");
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
            System.out.println("\t Something went wrong with the file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\t Please refer to the proper command formats");
        }
    }

    boolean isBye() {
        return this.bye;
    }
}

class Task {
    private boolean isDone;
    private String taskName;

    Task(String name) {
        this.isDone = false;
        this.taskName = name;
    }

    void makeDone() {
        this.isDone = true;
    }

    void makeUndone() {
        this.isDone = false;
    }

    public String toString() {
        if (this.isDone) {
            return "[X] " + this.taskName;
        } else {
            return "[ ] " + this.taskName;
        }
    }
}

class ToDo extends Task {
    ToDo(String name) {
        super(name);
    }

    public String toString() {
        return "[T] " + super.toString();
    }
}

class Deadline extends Task {
    private String deadline;

    Deadline(String name, String deadline) {
        super(name);
        this.deadline = deadline;
    }

    public String toString() {
        return "[D] " + super.toString() + " (by: " + this.deadline + ")";
    }
}

class Event extends Task {
    private String start;
    private String end;

    Event(String name, String start, String end) {
        super(name);
        this.start = start;
        this.end = end;
    }

    public String toString() {
        return "[E] " + super.toString() + " (from: " + this.start + " to: " + this.end + ")";
    }
}

class EmptyException extends Exception {
    public EmptyException(String msg) {
        super(msg);
    }
}

class NonsenseException extends Exception {
}