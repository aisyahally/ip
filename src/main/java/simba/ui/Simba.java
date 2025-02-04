package simba.ui;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The main class for the Simba task management application.
 * Simba manages tasks, allows users to add, view, and delete tasks, and saves them to a file.
 * It interacts with the user through the command line interface.
 *
 * <p>Simba integrates with other components such as:
 * <ul>
 *     <li>{@link Storage} - for saving and loading tasks from a file.</li>
 *     <li>{@link TaskList} - for managing the list of tasks.</li>
 *     <li>{@link Ui} - for interacting with the user and processing commands.</li>
 * </ul>
 * </p>
 * <p>For example, running the application will display a welcome message
 * and prompt the user to input commands. Commands
 * such as "todo" or "deadline" will be processed and corresponding tasks will be added to the task list.</p>
 */
public class Simba {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

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
