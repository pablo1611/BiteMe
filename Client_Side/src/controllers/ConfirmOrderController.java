package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import common.SharedData;
import entities.OrderDetails;
import entities.Request;
import common.RequestType;
import client.ChatClient;
import client.ClientUI;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ConfirmOrderController extends AbstractController {

    @FXML
    private Button confirmReceiveButton;

    private Instant startTime;
    private int totalPrice;

    @FXML
    public void initialize() {
        startTime = Instant.now(); // Record the start time when the window is opened
        totalPrice = SharedData.getInstance().getTotalPrice(); // Retrieve the total price passed from the previous window
    }

    @FXML
    private void handleConfirmReceive(ActionEvent event) {
        Instant endTime = Instant.now();
        Duration duration = Duration.between(startTime, endTime);

        String supplyStatus = duration.getSeconds() < 20 ? "On time" : "Delayed";

        // Prepare order details to send to the server
        OrderDetails orderDetails = SharedData.getInstance().getOrderDetails();
        Map<String, Object> orderData = new HashMap<>();
        orderData.put("username", this.currentUser.getUserName());
        orderData.put("order_date", LocalDate.now()); // Set current date as order date
        orderData.put("restaurant", orderDetails.getRestaurant());
        orderData.put("branch", orderDetails.getBranch());
        orderData.put("type", orderDetails.getOrderType());
        orderData.put("price", totalPrice); // Use the passed total price
        orderData.put("supply_status", supplyStatus);

        // Create and configure the request object
        Request request = new Request();
        request.setType(RequestType.INSERT_ORDER);
        request.setRequest(orderData);

        // Send the request to the server
        ChatClient client = ClientUI.getClient();
        try {
            byte[] arr = request.getBytes();
            client.handleMessageFromClientUI(arr);

            // Check for the response
            Object response = SharedData.getInstance().getLastResponse();
            if (response != null) {
                System.out.println("Order inserted successfully: " + response); // Debug print
            } else {
                System.out.println("Failed to insert order");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Navigate to the main menu
        navigateTo("/gui/CustomerMainMenu.fxml", "/gui/CustomerDetailsPage.css", (Node) event.getSource());
        /*
        // Close the confirmation window
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        */
    }
}
