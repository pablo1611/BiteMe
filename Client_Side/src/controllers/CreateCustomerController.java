package controllers;

import client.ChatClient;
import client.ClientUI;
import common.RequestType;
import entities.Request;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;

import java.io.IOException;

public class CreateCustomerController extends AbstractController {

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtRole;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtRestaurant;

    @FXML
    private TextField txtBranch;

    @FXML
    void handleCreateCustomer(ActionEvent event) throws IOException {
        // Get the input values
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String name = txtName.getText();
        String phone = txtPhone.getText();
        String address = txtAddress.getText();
        String role = txtRole.getText();
        String email = txtEmail.getText();
        String restaurant = txtRestaurant.getText();
        String branch = txtBranch.getText();

        // Create a new User object
        User newUser = new User(username, password);
        newUser.setName(name);
        newUser.setPhone(phone);
        newUser.setAddress(address);
        newUser.setRole(role);
        newUser.setEmail(email);
        newUser.setRestaurant(restaurant);  // Add restaurant
        newUser.setBranch(branch);  // Add branch
        newUser.setStatus("Disconnected"); // Default status

        // Create a request
        Request request = new Request();
        request.setType(RequestType.CREATE_USER);
        request.setRequest(newUser);

        // Send the request to the server
        ChatClient client = ClientUI.getClient();
        try {
            byte[] arr = request.getBytes();
            client.handleMessageFromClientUI(arr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Navigate back to the Manager Main Menu
        navigateTo("/gui/ManagerMainMenu.fxml", "/gui/ManagerMainMenu.css", (Node) event.getSource());
    }

    @FXML
    void handleBack(ActionEvent event) throws IOException {
        navigateTo("/gui/ManagerMainMenu.fxml", "/gui/ManagerMainMenu.css", (Node) event.getSource());
    }
}

