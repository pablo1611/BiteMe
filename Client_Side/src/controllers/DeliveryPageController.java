package controllers;

import java.io.IOException;

import client.ChatClient;
import client.ClientUI;
import common.SharedData;
import entities.OrderDetails;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class DeliveryPageController extends AbstractController {

    @FXML
    private TextField townField;

    @FXML
    private TextField streetField;

    @FXML
    private TextField streetNumberField;

    @FXML
    private Button btnContinue;
    
    @FXML
    private Button btnExit;

    @FXML
    void initialize() {
    	OrderDetails orderDetails = SharedData.getInstance().getOrderDetails();
    	/*
        if (orderDetails != null) {
            System.out.println("Loaded OrderDetails: " + orderDetails.toString()); // Debug print
        }
        */
    }

    @FXML
    void handleContinue(ActionEvent event) {
        // Collect the input details
        String town = townField.getText();
        String street = streetField.getText();
        String streetNumber = streetNumberField.getText();

        // Store details in SharedData
        OrderDetails orderDetails = SharedData.getInstance().getOrderDetails();
        String address = String.format("%s, %s %s", town, street, streetNumber);
        orderDetails.setAddress(address);
        if (orderDetails != null) {
            System.out.println("Loaded OrderDetails: " + orderDetails.toString()); // Debug print
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
