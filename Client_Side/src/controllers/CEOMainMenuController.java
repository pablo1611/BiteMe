package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import client.ClientUI;

public class CEOMainMenuController {

    @FXML
    private Button viewReportsButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button exitButton;

    @FXML
    void handleViewReports(ActionEvent event) {
        // TODO: Implement view reports functionality
        System.out.println("View Reports button clicked");
    }

    @FXML
    void handleLogout(ActionEvent event) {
        // TODO: Implement logout functionality
        System.out.println("Logout button clicked");
        // For example:
        // ClientUI.getClient().handleMessageFromClientUI("#logout");
        // Then navigate to login screen
    }

    @FXML
    void handleExit(ActionEvent event) {
        // Close the application
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
        System.exit(0);
    }
}