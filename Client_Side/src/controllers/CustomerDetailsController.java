package controllers;

import java.io.IOException;

import client.ClientUI;
import common.RequestType;
import entities.OrderDetails;
import entities.Request;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CustomerDetailsController extends AbstractController {

    @FXML
    private TextField restaurantField; // שדה טקסט במקום ComboBox כדי לאפשר הזנה חופשית

    @FXML
    private ComboBox<String> orderTypeComboBox;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField orderAddressField;

    @FXML
    private void initialize() {
        orderTypeComboBox.getItems().addAll("Pick-up", "Delivery");
    }

    @FXML
    private void handleNext(ActionEvent event) {
        if (validateInput()) {
            User currentUser = getCurrentUser(); // מקבל את המשתמש הנוכחי מ-AbstractController
            OrderDetails orderDetails = new OrderDetails(currentUser.getId(), restaurantField.getText(), orderTypeComboBox.getValue(),
                    phoneNumberField.getText(), orderAddressField.getText());

            Request request = new Request();
            request.setType(RequestType.ORDER_DETAILS);
            request.setRequest(orderDetails);

            byte[] arr;
            try {
                arr = request.getBytes();
                ClientUI.chat.handleMessageFromClientUI(arr);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // לאחר שליחת הבקשה, נעבור למסך הבא
            handleNext("/gui/OrderItemsPage.fxml", (Node) event.getSource()); //next screen by abstract controller
        }
    }


    private boolean validateInput() {
        if (!restaurantField.getText().equalsIgnoreCase("Fat Vinny") && !restaurantField.getText().equalsIgnoreCase("BP")) {
            showAlert("Invalid Restaurant", "Please enter 'Fat Vinny' or 'BP'.");
            return false;
        }
        if (orderTypeComboBox.getValue() == null ||
                phoneNumberField.getText().isEmpty() ||
                orderAddressField.getText().isEmpty()) {
            showAlert("Input Validation Error", "Please fill in all fields.");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void handleBack(ActionEvent event) {
        handleBack("/gui/CustomerMainMenu.fxml", (Node) event.getSource());//previous screen by abstract controller
    }

}


