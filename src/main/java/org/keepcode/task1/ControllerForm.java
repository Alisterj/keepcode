package org.keepcode.task1;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.keepcode.task1.aggregator.NumberAggregator;
import org.keepcode.task1.aggregator.http.ConnectionException;
import org.keepcode.task1.aggregator.parser.Country;
import org.keepcode.task1.aggregator.parser.Number;

import java.net.MalformedURLException;
import java.util.ArrayList;
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
            Platform.runLater(() -> {
                try {
                    Map<String, List<Number>> numbersCountry = numberAggregator.getNumbersCountry();

                    for (Map.Entry<String, List<Number>> entry : numbersCountry.entrySet()) {
                        String country = entry.getKey();
                        String stringNumbers = entry.getValue().stream().map(Number::toString).collect(Collectors.joining("\n"));

                        numbersCountryTextArea.appendText("""
                            %s
                            %s
                            """.formatted(country, stringNumbers));
                    }
                } catch (MalformedURLException | ConnectionException e) {
                    throw new RuntimeException(e);
                }
            });
        });
        thread.start();
    }
}
