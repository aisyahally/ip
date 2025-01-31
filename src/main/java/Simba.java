import java.util.Scanner;
import java.util.ArrayList;

public class Simba {
    public static void main(String[] args) {
        System.out.println("Hello I am Simba.");
        System.out.println("How can I help you?");
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> list = new ArrayList<Task>();
        while (true) {
            String command = sc.nextLine();
            if (command.equals("bye")) {
                System.out.println("Bye-bye!");
                break;
            } else if (command.equals("list")) {
                for (int i = 0; i < list.size(); i++) {
                    int idx = i + 1;
                    System.out.println(idx + ": " + list.get(i));
                }
            } else if (command.contains("mark")) {
                int i = Integer.parseInt(command.substring(command.length() - 1));
                if (command.contains("unmark")) {
                    list.get(i-1).makeUndone();
                    System.out.println("Okay! This task is not done:");
                    System.out.println(list.get(i-1));
                } else {
                    list.get(i-1).makeDone();
                    System.out.println("Alright! This task is done:");
                    System.out.println(list.get(i-1));
                }
            } else {
                if (command.contains("todo")) {
                    list.add(new ToDo(command.substring(5)));
                } else if (command.contains("deadline")) {
                    for (int i = 0; i < command.length(); i++) {
                        if (command.substring(i, i+1).equals("/")) {
                            list.add(new Deadline(command.substring(9,i), command.substring(i+4)));
                        }
                    }
                } else {
                    int startIdx = 0;
                    int endIdx = 0;
                    for (int i = 0; i < command.length(); i++) {
                        if (command.substring(i, i+1).equals("/")) {
                            if (command.substring(i+1, i+2).equals("f")) {
                                startIdx = i + 6;
                            } else {
                                endIdx = i + 4;
                            }
                        }
                    }
                    list.add(new Event(command.substring(6, startIdx - 6), command.substring(startIdx, endIdx-4),
                            command.substring(endIdx)));
                }
                System.out.println("Added: " + list.get(list.size() -1));
                System.out.println("Now you have " + list.size() + " task(s) in the list");
            }
        }
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