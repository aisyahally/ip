package simba.ui;

import java.io.IOException;

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

    String generateGreeting() {
        return "Hello I am Simba!\n"
                + "How can I help you?";
    }

    /**
     * Reads and processes a user command.
     *
     * @param command The user command to process.
     */
    String readCommand(String command) {
        try {
            this.storage.writeToFile(this.tasks.getList());
            if (command.equals("hello")) {
                return this.hello();
            } else if (command.equals("help")) {
                return this.commands();
            } else if (command.equals("bye")) {
                return this.bye();
            } else if (command.equals("list")) {
                return this.storage.fileToString();
            } else if (command.substring(0, 4).equals("mark")) {
                int i = Integer.parseInt(command.substring(command.length() - 1));
                if (command.substring(0, 6).equals("unmark")) {
                    return this.tasks.unmarkTask(i);
                } else {
                    return this.tasks.markTask(i);
                }
            } else if (command.substring(0, 6).equals("delete")) {
                return this.tasks.deleteTask(new Parser(command).idxToDelete());
            } else if (command.substring(0, 4).equals("find")) {
                return this.tasks.findTask(new Parser(command).wordToFind());
            } else {
                return this.tasks.addTask(new Parser(command).taskToAdd());
            }
        } catch (EmptyException e) {
            return "Oh no! " + e.getMessage() + " description is wrong";
        } catch (InvalidCommandException e) {
            return "Oh dear :( I don't understand you";
        } catch (IOException e) {
            return "Something went wrong with the file: " + e.getMessage();
        } catch (Exception e) {
            return "Please refer to the proper command formats";
        }
    }

    private String hello() {
        return "Hello! What would you like me to do today?";
    }

    private String commands() {
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

    private String bye() {
        return "Bye-bye!";
    }

}
