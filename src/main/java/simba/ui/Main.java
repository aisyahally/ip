package simba.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Main extends Application {
    private static final String DEFAULT_FILE_PATH = "simba.example.txt";

    public Main(String filePath) {}

    public Main() {
        this(DEFAULT_FILE_PATH);
    }

    public void start(Stage stage) {
        Label helloWorld = new Label("Hello World!");
        Scene scene = new Scene(helloWorld);

        stage.setScene(scene);
        stage.show();
    }
}
