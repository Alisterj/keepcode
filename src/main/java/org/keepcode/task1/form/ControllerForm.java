package org.keepcode.task1.form;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.keepcode.task1.aggregator.NumberAggregator;
import org.keepcode.task1.aggregator.parser.Number;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ControllerForm {

    private final NumberAggregator numberAggregator = new NumberAggregator();


    @FXML
    private TextArea numbersCountryTextArea;

    @FXML
    public void initialize() {
        Thread thread = new Thread(() -> {
            Map<String, List<Number>> numbersCountry = numberAggregator.getNumbersCountry();

            for (Map.Entry<String, List<Number>> entry : numbersCountry.entrySet()) {
                String country = entry.getKey();
                String stringNumbers = entry.getValue().stream().map(Number::toString).collect(Collectors.joining("\n"));

                Platform.runLater(() -> {
                    numbersCountryTextArea.appendText("%s\n%s\n".formatted(country, stringNumbers));
                });
            }
        });
        thread.start();
    }
}
