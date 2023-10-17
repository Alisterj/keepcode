package org.keepcode.task1.aggregator.javafxController;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ControllerForm {

    @FXML
    private TextArea countryNumbers;

    @FXML
    public void initialize() {
        countryNumbers.setText("Начальное значение TextArea в контроллере.");
    }
}
