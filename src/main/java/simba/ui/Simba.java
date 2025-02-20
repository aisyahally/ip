package simba.ui;

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
    private static final String FILE_PATH = "simba.txt";

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    /**
     * Initializes a new Simba instance.
     */
    public Simba() {
        this.storage = new Storage(FILE_PATH);
        this.tasks = new TaskList(this.storage);
        this.ui = new Ui(this.storage, this.tasks);
    }

    /**
     * Generates a greeting as a string.
     */
    public String greet() {
        return ui.generateGreeting();
    }

    /**
     * Generates a response for the user's chat message as a string.
     */
    public String getResponse(String input) {
        return ui.readCommand(input);
    }
}
