package controllers;
import client.ChatClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.io.IOException;

import java.io.IOException;

public class ManagerMainMenuController extends AbstractController {

    @FXML
    private Button btnViewReports;

    @FXML
    private Button btnCreateCustomer;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnExit;

    @FXML
    void ViewReports(ActionEvent event) {
        // Implement the action for viewing reports
        System.out.println("View Reports button clicked");
        // Add your code here to open the reports view
    }

    @FXML
    void CreateCustomer(ActionEvent event) {
        // Implement the action for creating a customer
        System.out.println("Create Customer button clicked");
        // Add your code here to open the create customer view
    }

    @FXML
    void Logout(ActionEvent event)throws IOException {
        handleLogout(event);
    }

    @FXML
    void Exit(ActionEvent event) throws IOException {
        ChatClient client= ClientUI.getClient();
        handleExit(event);
    }
}