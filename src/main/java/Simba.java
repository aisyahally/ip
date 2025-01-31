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
                System.out.println("added: " + command);
                list.add(new Task(command));
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