package simba.ui;

import java.io.IOException;
import java.time.format.DateTimeParseException;

import exception.ui.DuplicateTaskException;
import exception.ui.EmptyException;
import exception.ui.InvalidCommandException;
import exception.ui.InvalidEventDateException;

/**
 * Represents the User Interface (UI) of the Simba application.
 * It handles reading and processing user commands and
 * interacting with the storage and task list.
 */
class Ui {
    private final Storage storage;
    private final TaskList tasks;

    /**
     * Initializes a new Ui instance with the specified storage and task list.
     *
     * @param storage The storage instance.
     * @param tasks The task list instance.
     */
    Ui(Storage storage, TaskList tasks) {
        this.storage = storage;
        this.tasks = tasks;
    }

    /**
     * Generates a greeting message for the user.
     *
     * @return A string message greeting the user and asking how they can be helped.
     */
    String generateGreeting() {
        return "Hello I am Simba!\n"
                + "How can I help you?";
    }

    /**
     * Reads and processes a user command and returns a response based on the command.
     * It checks the command and performs the appropriate action, such as marking tasks,
     * deleting tasks, adding tasks, or displaying a list of commands.
     *
     * @param command The user command to process.
     * @return A string response based on the processed command or an error message if the command is invalid.
     * @throws IOException If an error occurs while writing or reading from a file.
     */
    String readCommand(String command) {
        assert command != null && !command.isEmpty() : "Command should not be null or empty";
        try {
            this.storage.writeToFile(this.tasks.getList());
            if (command.equals("hello") || command.equals("hi")) {
                return this.helloAsString();
            } else if (command.equals("help")) {
                return this.commandsAsString();
            } else if (command.equals("bye")) {
                return this.byeAsString();
            } else if (command.equals("list")) {
                return this.storage.fileToString();
            } else if (command.equals("thanks")) {
                return this.npAsString();
            } else if (this.isMark(command)) {
                int i = Integer.parseInt(command.substring(command.length() - 1));
                return this.tasks.markTaskAsString(i);
            } else if (this.isUnmark(command)) {
                int i = Integer.parseInt(command.substring(command.length() - 1));
                return this.tasks.unmarkTaskAsString(i);
            } else if (this.isDelete(command)) {
                return this.tasks.deleteTaskAsString(new Parser(command).idxToDelete());
            } else if (this.isFind(command)) {
                return this.tasks.findTaskAsString(new Parser(command).wordToFind());
            } else if (this.isTask(command)){
                return this.tasks.addTaskAsString(new Parser(command).taskToAdd());
            } else {
                throw new InvalidCommandException(command);
            }
        } catch (InvalidCommandException e) {
            return e.getMessage();
        } catch (EmptyException e) {
            return "Oh no! " + e.getMessage() + " description is wrong";
        } catch (DateTimeParseException e) {
            return "Valid date and time should be written as DD-MM-YYYY HHMM";
        } catch (InvalidEventDateException e) {
            return "Start date should be before end date";
        } catch (DuplicateTaskException e) {
            return "This task already exists";
        } catch (IOException e) {
            return "Something went wrong with the file: " + e.getMessage();
        }
    }

    /**
     * Returns a greeting message to prompt the user for an action.
     *
     * @return A string message asking what the user would like to do.
     */
    private String helloAsString() {
        return "Hello! What would you like me to do today?";
    }

    /**
     * Provides a list of available commands that the user can issue to interact with the application.
     *
     * @return A string listing all the commands available for the user.
     */
    private String commandsAsString() {
        return "Here are the list of commands:\n"
                + "\t- hello / hi\n"
                + "\t- list\n"
                + "\t- todo [task description]\n"
                + "\t- deadline [task description] /by [dd-mm-yyyy hhmm]\n"
                + "\t- event [task description] /from [dd-mm-yyyy hhmm] /to [dd-mm-yyyy hhmm]\n"
                + "\t- mark [task number] / unmark [task number]\n"
                + "\t- delete [task number]\n"
                + "\t- find [keyword in task]\n"
                + "\t- bye";
    }

    /**
     * Returns a farewell message when the user exits the application.
     *
     * @return A string message bidding farewell to the user.
     */
    private String byeAsString() {
        return "Bye-bye!";
    }

    private String npAsString() {
        return "No problem!";
    }

    /**
     * Checks if the command is a "mark" command.
     *
     * @param command The command string to check.
     * @return True if the command starts with "mark ", false otherwise.
     */
    private boolean isMark(String command) {
        if (command.length() > 4) {
            boolean isMark = command.substring(0, 5).equals("mark ");
            return isMark;
        }
        return false;
    }

    /**
     * Checks if the command is an "unmark" command.
     *
     * @param command The command string to check.
     * @return True if the command starts with "unmark ", false otherwise.
     */
    private boolean isUnmark(String command) {
        if (command.length() > 6) {
            boolean isUnmark = command.substring(0, 7).equals("unmark ");
            return isUnmark;
        }
        return false;
    }

    /**
     * Checks if the command is a "delete" command.
     *
     * @param command The command string to check.
     * @return True if the command starts with "delete ", false otherwise.
     */
    private boolean isDelete(String command) {
        if (command.length() > 6) {
            boolean isDelete = command.substring(0, 7).equals("delete ");
            return isDelete;
        }
        return false;
    }

    /**
     * Checks if the command is a "find" command.
     *
     * @param command The command string to check.
     * @return True if the command starts with "find ", false otherwise.
     */
    private boolean isFind(String command) {
        if (command.length() > 4) {
            boolean isFind = command.substring(0, 5).equals("find ");
            return isFind;
        }
        return false;
    }

    /**
     * Checks if the command is a task-related command (todo, deadline, or event).
     *
     * @param command The command string to check.
     * @return True if the command starts with "todo ", "deadline ", or "event ", false otherwise.
     */
    private boolean isTask(String command) {
        boolean isDeadline = false;
        boolean isEvent = false;
        boolean isToDo = false;
        if (command.length() > 4) {
            isToDo = command.substring(0, 5).equals("todo ");
            if (command.length() > 5) {
                isEvent = command.substring(0, 6).equals("event ");
                if (command.length() > 8) {
                    isDeadline = command.substring(0, 9).equals("deadline ");
                }
            }
        }
        return isDeadline || isEvent || isToDo;
    }

}
