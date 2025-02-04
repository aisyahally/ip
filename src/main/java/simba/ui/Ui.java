package simba.ui;

import java.io.IOException;

/**
 * Represents the User Interface (UI) of the Simba application.
 * It handles reading and processing user commands and
 * interacting with the storage and task list.
 */
class Ui {
    private boolean bye;
    private final Storage storage;
    private final TaskList tasks;

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
                System.out.println("\t\t- find [keyword in task]");
                System.out.println("\t\t- bye");
            } else if (command.equals("bye")) {
                System.out.println("\tBye-bye!");
                System.out.println("\t  /\\_/\\");
                System.out.println("\t ( o.o )");
                System.out.println("\t  > ^ <");
                this.bye = true;
            } else if (command.equals("list")) {
                this.storage.printFile();
            } else if (command.substring(0, 4).equals("mark")) {
                int i = Integer.parseInt(command.substring(command.length() - 1));
                if (command.substring(0, 6).equals("unmark")) {
                    this.tasks.unmarkTask(i);
                } else {
                    this.tasks.markTask(i);
                }
            } else if (command.substring(0, 6).equals("delete")) {
                this.tasks.deleteTask(new Parser(command).idxToDelete());
            } else if (command.substring(0, 4).equals("find")) {
                this.tasks.findTask(new Parser(command).wordToFind());
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
