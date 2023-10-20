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
    private ImageView error;

    /**
     * Инициализация {@link #numbersCountryTextArea} - Объект заполняется данными стран с номерами, но если приходит null то вызывается ошибка, TextArea получает visible="true" и появляется {@link #error}
     * @throws RuntimeException Если данные не были найдены
     */
    @FXML
    public void initialize() {
        CustomLogger.getInstance().info("Инициализация поля - TextArea");

        Thread thread = new Thread(() -> {
            try {
                Map<String, List<Number>> numbersCountry = numberAggregator.getNumbersCountry();

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
                    numbersCountryTextArea.setVisible(false);
                    error.setVisible(true);
                });

                throw new RuntimeException("Error, you have don't current data", e);
            }
        });
        thread.start();
    }
}
