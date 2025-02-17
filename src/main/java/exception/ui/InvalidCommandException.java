package exception.ui;

/**
 * Thrown when an invalid command is entered.
 */
public class InvalidCommandException extends Exception {
    private String command;

    public InvalidCommandException(String command) {
        this.command = command;
    }

    public String getMessage() {
        if (this.containsCaps(this.command)) {
            return "No need to shout at me :( Only lowercase please";
        } else {
            return "Oh dear :( I don't understand you";
        }
    }

    private boolean containsCaps(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (Character.isUpperCase(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

}
