package main.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import simba.ui.Simba;

/**
 * Controller for the main GUI, handling user interactions and chatbot responses.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Simba simba;

    private final Image simbaImage = new Image(this.getClass().getResourceAsStream("/images/simba.png"));
    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));

    /**
     * Initializes the MainWindow. Binds the ScrollPane's vertical value to the height of the dialog container,
     * ensuring automatic scrolling to the latest message.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Simba instance into the controller.
     *
     * @param simba The Simba chatbot instance.
     */
    public void setSimba(Simba simba) {
        this.simba = simba;
    }

    /**
     * Displays Simba's initial greeting in the dialog container.
     */
    void initialGreeting() {
        dialogContainer.getChildren().addAll(
                DialogBox.getSimbaDialog(simba.greet(), simbaImage)
        );
        userInput.clear();
    }

    /**
     * Handles user input by displaying the user's message and Simba's response in the dialog container.
     * If the user enters "bye", the application exits.
     * If the input is blank, no action is taken.
     * After processing, the user input field is cleared.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();

        if (input.equals("bye")) {
            System.exit(0);
        }
        if (input.isBlank()) {
            return;
        }

        String response = simba.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getSimbaDialog(response, simbaImage)
        );
        userInput.clear();
    }
}
