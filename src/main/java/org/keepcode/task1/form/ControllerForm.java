package org.keepcode.task1.form;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import org.keepcode.task1.aggregator.NumberAggregator;
import org.keepcode.task1.aggregator.parser.Number;
import org.keepcode.task1.aggregator.requester.RequesterException;
import org.keepcode.task1.logger.CustomLogger;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ControllerForm {
    private final NumberAggregator numberAggregator = new NumberAggregator();

    @FXML
    private TextArea numbersCountryTextArea;

    @FXML
    private ImageView img404;

    @FXML
    public void initialize() {
        CustomLogger.getInstance().info("Инициализация поля - TextArea");

        Thread thread = new Thread(() -> {
            try {
                Map<String, List<Number>> numbersCountry = numberAggregator.getNumbersCountry();
//                Map<String, List<Number>> numbersCountry = null;
//                numbersCountry = numberAggregator.getNumbersCountry();

                if (numbersCountry == null) {
                    Platform.runLater(() -> {
                        numbersCountryTextArea.appendText("Error 404");
                    });
                }

                for (Map.Entry<String, List<Number>> entry : numbersCountry.entrySet()) {
                    String country = entry.getKey();
                    String stringNumbers = entry.getValue().stream().map(Number::toString).collect(Collectors.joining("\n"));

                    Platform.runLater(() -> {
                        numbersCountryTextArea.appendText("%s\n%s\n".formatted(country, stringNumbers));
                    });
                }
            } catch (RequesterException e) {
                CustomLogger.getInstance().error("Ошибка данные не найдены");

                Platform.runLater(() -> {
//                    numbersCountryTextArea.setDisable(true);
                    numbersCountryTextArea.setVisible(false);
                    img404.setVisible(true);
                    /*numbersCountryTextArea.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
                    numbersCountryTextArea.appendText("404\ndata not found");*/
                });

                throw new RuntimeException("Error, you have don't current data", e);
            }
        });
        thread.start();
    }
}
