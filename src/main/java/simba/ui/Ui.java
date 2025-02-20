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
     * Processes a user command and returns an appropriate response.
     * It identifies the command type and performs corresponding actions such as adding, deleting,
     * marking, unmarking tasks, or displaying a list of tasks or available commands.
     * If the command is invalid or causes an error, an error message is returned.
     *
     * <p>The method handles the following commands:</p>
     * <ul>
     *     <li>"hello" or "hi" - Returns a greeting message.</li>
     *     <li>"help" - Displays a list of available commands.</li>
     *     <li>"list" - Shows the list of tasks in storage.</li>
     *     <li>"thanks" - Acknowledges the user's gratitude.</li>
     *     <li>"mark [task number]" - Marks the specified task as done.</li>
     *     <li>"unmark [task number]" - Marks the specified task as not done.</li>
     *     <li>"delete [task number]" - Deletes the specified task from the list.</li>
     *     <li>"find [keyword]" - Searches for tasks containing the specified keyword.</li>
     *     <li>"todo [task description]" - Adds a new "ToDo" task to the list.</li>
     *     <li>"deadline [task description] /by [dd-mm-yyyy hhmm]" - Adds a new "Deadline" task to the list.</li>
     *     <li>"event [task description] /from [dd-mm-yyyy hhmm] /to [dd-mm-yyyy hhmm]"
     *     - Adds a new "Event" task to the list.</li>
     * </ul>
     *
     * <p>If the command is unrecognized or invalid, an error message will be returned.
     * Specific exceptions such as empty description, invalid date formats,
     * or duplicate tasks will also return tailored error messages.</p>
     *
     * @param command The user input command to be processed.
     * @return A string representing the response to the user's command, or an error message if the command is invalid.
     */
    String readCommand(String command) {
        assert command != null && !command.isEmpty() : "Command should not be null or empty";
        Parser parser = new Parser(command);
        try {
            String response = "";
            if (command.equals("hello") || command.equals("hi")) {
                response = this.helloAsString();
            } else if (command.equals("help")) {
                response = this.commandsAsString();
            } else if (command.equals("list")) {
                response = this.storage.fileToString();
            } else if (command.equals("thanks")) {
                response = this.npAsString();
            } else if (this.isMark(command)) {
                response = this.tasks.markTaskAsString(parser.idxToUse());
            } else if (this.isUnmark(command)) {
                response = this.tasks.unmarkTaskAsString(parser.idxToUse());
            } else if (this.isDelete(command)) {
                response = this.tasks.deleteTaskAsString(parser.idxToUse());
            } else if (this.isFind(command)) {
                response = this.tasks.findTaskAsString(parser.wordToFind());
            } else if (this.isTask(command)) {
                response = this.tasks.addTaskAsString(parser.taskToAdd());
            }
            this.storage.writeToFile(this.tasks.getList());
            if (response.equals("")) {
                throw new InvalidCommandException(command);
            }
            return response;
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
     * Returns a response message for when the user thanks the application.
     *
     * @return A string message acknowledging the user's gratitude.
     */
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
