package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Node;

import java.io.IOException;

public class CEOMainMenuController extends AbstractController {

    @FXML
    private Button btnViewQuarterlyReports;

    @FXML
    private Button logoutButton;

    @FXML
    private Button exitButton;

    @FXML
    void QuarterlyReports(ActionEvent event) {
        // TODO: Implement view reports functionality
        System.out.println("View Reports button clicked");
        // When implemented, you can use navigateTo like this:
        // navigateTo("/gui/ViewReports.fxml", "/gui/ViewReports.css", (Node) event.getSource());
    }
    @FXML
    void CreateCustomer(ActionEvent event) throws IOException {
        navigateTo("/gui/CreateCustomer.fxml", "/gui/CreateCustomer.css", (Node) event.getSource());
    }

    @FXML
    void Disconnected(ActionEvent event) throws IOException {
        handleLogout(event);
    }

    @FXML
    void Exit(ActionEvent event) throws IOException {
        handleExit(event);
    }
}