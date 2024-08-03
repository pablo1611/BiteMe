package controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import client.ChatClient;
import client.ClientUI;
import common.SharedData;
import entities.OrderDetails;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class EarlyDeliveryPageController extends AbstractController {

    @FXML
    private TextField townField;

    @FXML
    private TextField streetField;

    @FXML
    private TextField streetNumberField;

    @FXML
    private DatePicker deliveryDatePicker;

    @FXML
    private ComboBox<String> hourField;

    @FXML
    private Button btnContinue;
    
    @FXML
    private Button btnExit;

    @FXML
    void initialize() {
        // Initialize hourField with hours
        hourField.setItems(FXCollections.observableArrayList(
                "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00"
        ));
    }

    @FXML
    void handleContinue(ActionEvent event) {
        // Collect the input details
        String town = townField.getText();
        String street = streetField.getText();
        String streetNumber = streetNumberField.getText();
        LocalDate deliveryDate = deliveryDatePicker.getValue();
        LocalTime deliveryTime = LocalTime.parse(hourField.getValue());
        LocalDateTime deliveryDateTime = LocalDateTime.of(deliveryDate, deliveryTime);

        // Store details in SharedData
        OrderDetails orderDetails = SharedData.getInstance().getOrderDetails();
        if (orderDetails != null) {
            String address = String.format("%s, %s %s", town, street, streetNumber);
            orderDetails.setAddress(address);
            orderDetails.setDeliveryTime(deliveryDateTime.toString());
            SharedData.getInstance().setOrderDetails(orderDetails); // Update SharedData with modified orderDetails
            System.out.println("Updated OrderDetails: " + orderDetails); // Debug print
        } else {
            System.out.println("OrderDetails is null in handleContinue");
        }

        // Navigate to the next page
        navigateTo("/gui/OrderItemsPage.fxml", "/gui/CustomerDetailsPage.css", (Node) event.getSource());
    }
    
    @FXML
    private void handleBack(ActionEvent event) {
        navigateTo("/gui/CustomerDetailsPage.fxml", "/gui/CustomerDetailsPage.css", (Node) event.getSource());
    }

    @FXML
    void exit(ActionEvent event) throws IOException {
        handleExit(event);
    }
}
