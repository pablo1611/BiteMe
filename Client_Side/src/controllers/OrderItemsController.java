package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class OrderItemsController {

    @FXML
    private ComboBox<String> dishTypeComboBox;

    @FXML
    private TextField dishNameField;

    @FXML
    private TextField quantityField;

    @FXML
    void handleAddToOrder(ActionEvent event) {
        // Handle add to order action
    }

    @FXML
    void handleSubmitOrder(ActionEvent event) {
        // Handle submit order action
    }

    @FXML
    void handleBack(ActionEvent event) {
        // Handle backaction
    }
}