package org.keepcode.task1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.keepcode.task1.aggregator.requester.RequesterException;
import org.keepcode.task1.logger.CustomLogger;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    public static void main(String[] args) {
        CustomLogger.getInstance().info("Запуск программы");
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/formCountryNumbers.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Country with numbers");
        stage.show();
    }
}