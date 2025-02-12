package simba.ui;

import java.io.IOException;
import java.time.format.DateTimeParseException;

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
            if (command.equals("hello")) {
                return this.helloAsString();
            } else if (command.equals("help")) {
                return this.commandsAsString();
            } else if (command.equals("bye")) {
                return this.byeAsString();
            } else if (command.equals("list")) {
                return this.storage.fileToString();
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
                throw new InvalidCommandException();
            }
        } catch (EmptyException e) {
            return "Oh no! " + e.getMessage() + " description is wrong";
        } catch (InvalidCommandException e) {
            return "Oh dear :( I don't understand you";
        } catch (DateTimeParseException e) {
            return "Date and time should be written as DD-MM-YYYY HHMM";
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
                + "\t- hello\n"
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

    private boolean isMark(String command) {
        if (command.length() > 4) {
            boolean isMark = command.substring(0, 5).equals("mark ");
            return isMark;
        }
        return false;
    }

    private boolean isUnmark(String command) {
        if (command.length() > 6) {
            boolean isUnmark = command.substring(0, 7).equals("unmark ");
            return isUnmark;
        }
        return false;
    }

    private boolean isDelete(String command) {
        if (command.length() > 6) {
            boolean isDelete = command.substring(0, 7).equals("delete ");
            return isDelete;
        }
        return false;
    }

    private boolean isFind(String command) {
        if (command.length() > 4) {
            boolean isFind = command.substring(0, 5).equals("find ");
            return isFind;
        }
        return false;
    }

    private boolean isTask(String command) {
        if (command.length() > 8) {
            boolean isDeadline = command.substring(0, 9).equals("deadline ");
            return isDeadline;
        } else if (command.length() > 5) {
            boolean isEvent = command.substring(0, 6).equals("event ");
            return isEvent;
        } else if (command.length() > 4) {
            boolean isToDo = command.substring(0, 5).equals("todo ");
            return isToDo;
        } else {
            return false;
        }
    }

}
