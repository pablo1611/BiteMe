package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class CustomerDetailsController {

    @FXML
    private TextField restaurantField;

    @FXML
    private ComboBox<String> orderTypeComboBox;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField orderAddressField;

    @FXML
    void handleNext(ActionEvent event) {
        // Handle next action, e.g., navigate to the next screen
        System.out.println("Next button clicked");
        
    }

    @FXML
    void handleBack(ActionEvent event) {
        // Handle back action, e.g., navigate to the previous screen
        System.out.println("Back button clicked");
    }
}