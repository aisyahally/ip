package simba.ui;

import java.util.Scanner;
import java.util.ArrayList;

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
        System.out.println("\t  /\\_/\\");
        System.out.println("\t ( o.o )");
        System.out.println("\t  > ^ <");
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