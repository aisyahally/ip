package main.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import simba.ui.Simba;

/**
 * A GUI for Simba using FXML.
 */
public class Main extends Application {

    private final Simba simba = new Simba();

    @Override
    public void start(Stage stage) {
        try {
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            stage.setTitle("Simba");
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setSimba(simba);
            stage.show();
            fxmlLoader.<MainWindow>getController().initialGreeting();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
