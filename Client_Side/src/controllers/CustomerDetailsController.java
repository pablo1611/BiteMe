package controllers;

import java.io.IOException;
import java.util.ArrayList;

import client.ChatClient;
import client.ClientUI;
import common.RequestType;
import common.SharedData;
import entities.OrderDetails;
import entities.Request;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;

public class CustomerDetailsController extends AbstractController {

    @FXML
    private ComboBox<String> restaurantComboBox;

    @FXML
    private ComboBox<String> branchComboBox;

    @FXML
    private Button btnPickUp;

    @FXML
    private Button btnDelivery;

    @FXML
    private Button btnBusinessDelivery;

    @FXML
    private Button btnEarlyDelivery;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnBack;


    @FXML
    private ImageView deliveryImageView;

    @FXML
    private ImageView scheduleImageView;

    @FXML
    private ImageView pickUpImageView;

    @FXML
    public void initialize() {
        // Initialize ComboBoxes with data from the database
        loadRestaurants();
        loadBranches();
        // Set MouseTransparent to true for each ImageView
        deliveryImageView.setMouseTransparent(true);
        scheduleImageView.setMouseTransparent(true);
        pickUpImageView.setMouseTransparent(true);
    }

    private void loadRestaurants() {
        // Create a request
        Request request = new Request();
        request.setType(RequestType.GET_RESTAURANTS);
        request.setRequest(this.currentUser);

        // Send the request to the server
        ChatClient client = ClientUI.getClient();
        try {
            byte[] arr = request.getBytes();
            client.handleMessageFromClientUI(arr);

            // Check for the response
            Object response = SharedData.getInstance().getLastResponse();
            if (response != null && response instanceof ArrayList) {
                @SuppressWarnings("unchecked")
                ArrayList<String> restaurantNames = (ArrayList<String>) response;
                System.out.println("Received restaurants in controller: " + restaurantNames); // Debug print
                updateRestaurantComboBox(restaurantNames);
            } else {
                System.out.println("Request does not contain a valid list of restaurants");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateRestaurantComboBox(ArrayList<String> restaurantNames) {
        restaurantComboBox.getItems().clear();
        restaurantComboBox.getItems().addAll(restaurantNames);
    }

    private void loadBranches() {
        // Clear existing items and add new branch options
        branchComboBox.getItems().clear();
        branchComboBox.getItems().addAll("North", "South", "Center");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Selection Required");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean validateSelection() {
        if (restaurantComboBox.getValue() == null) {
            showAlert("Please select a restaurant.");
            return false;
        }
        if (branchComboBox.getValue() == null) {
            showAlert("Please select a branch.");
            return false;
        }
        return true;
    }

    @FXML
    private void handlePickUp(ActionEvent event) {
        if (!validateSelection()) return;

        // Handle PICK-UP action
        System.out.println("Pick UP selected");

        // Create and store OrderDetails
        OrderDetails orderDetails = new OrderDetails(this.currentUser, "PICK-UP");
        orderDetails.setRestaurant(restaurantComboBox.getValue());
        orderDetails.setBranch(branchComboBox.getValue());

        SharedData.getInstance().setOrderDetails(orderDetails);

        navigateTo("/gui/OrderItemsPage.fxml", "/gui/CustomerDetailsPage.css", (Node) event.getSource());
    }

    @FXML
    private void handleDelivery(ActionEvent event) {
        if (!validateSelection()) return;

        // Create and store OrderDetails
        OrderDetails orderDetails = new OrderDetails(this.currentUser, "DELIVERY");
        orderDetails.setRestaurant(restaurantComboBox.getValue());
        orderDetails.setBranch(branchComboBox.getValue());

        SharedData.getInstance().setOrderDetails(orderDetails);

        navigateTo("/gui/DeliveryPage.fxml", "/gui/CustomerDetailsPage.css", (Node) event.getSource());
    }

    @FXML
    private void handleBusinessDelivery(ActionEvent event) {
        if (!validateSelection()) return;

        // Create and store OrderDetails
        OrderDetails orderDetails = new OrderDetails(this.currentUser, "BUSINESS DELIVERY");
        orderDetails.setRestaurant(restaurantComboBox.getValue());
        orderDetails.setBranch(branchComboBox.getValue());

        SharedData.getInstance().setOrderDetails(orderDetails);

        navigateTo("/gui/BusinessDeliveryPage.fxml", "/gui/CustomerDetailsPage.css", (Node) event.getSource());
    }

    @FXML
    private void handleBack(ActionEvent event) {
        // Implement navigation to the previous page or desired action
        navigateTo("/gui/CustomerMainMenu.fxml", "/gui/CustomerMainMenu.css", (Node) event.getSource());
    }

    @FXML
    private void handleEarlyDelivery(ActionEvent event) {
        if (!validateSelection()) return;

        // Create and store OrderDetails
        OrderDetails orderDetails = new OrderDetails(this.currentUser, "EARLY DELIVERY");
        orderDetails.setRestaurant(restaurantComboBox.getValue());
        orderDetails.setBranch(branchComboBox.getValue());

        SharedData.getInstance().setOrderDetails(orderDetails);

        navigateTo("/gui/EarlyDeliveryPage.fxml", "/gui/CustomerDetailsPage.css", (Node) event.getSource());
    }

    @FXML
    void exit(ActionEvent event) throws IOException {
        handleExit(event);
    }
}