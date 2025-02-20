package exception.ui;

/**
 * Thrown when an invalid command is entered.
 */
public class InvalidCommandException extends Exception {
    private String command;

    /**
     * Constructs an InvalidCommandException with the specified invalid command.
     *
     * @param command the invalid command entered by the user
     */
    public InvalidCommandException(String command) {
        this.command = command;
    }

    /**
     * Returns an error message based on the command entered.
     * If the command contains uppercase letters, it suggests using lowercase.
     * Otherwise, it states that the command is not understood.
     *
     * @return the error message describing the issue with the command
     */
    @Override
    public String getMessage() {
        if (this.containsCaps(this.command)) {
            return "No need to shout at me :( Only lowercase please";
        } else {
            return "Oh dear :( I don't understand you";
        }
    }

    /**
     * Checks if the given string contains any uppercase letters.
     *
     * @param str the string to check
     * @return {@code true} if the string contains uppercase letters, {@code false} otherwise
     */
    private boolean containsCaps(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (Character.isUpperCase(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

}
