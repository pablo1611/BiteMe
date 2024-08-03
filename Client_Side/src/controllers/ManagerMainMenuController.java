package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;

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
    void ViewReports(ActionEvent event) throws IOException {
        System.out.println("Current user in ViewReports: " + currentUser); // Debug
        navigateTo("/gui/SelectReport.fxml", "/gui/SelectReport.css", (Node) event.getSource());
    }

    @FXML
    void CreateCustomer(ActionEvent event) throws IOException {
        navigateTo("/gui/CreateCustomer.fxml", "/gui/CreateCustomer.css", (Node) event.getSource());
    }

    @FXML
    void Logout(ActionEvent event) throws IOException {
        handleLogout(event);
    }

    @FXML
    void Exit(ActionEvent event) throws IOException {
        handleExit(event);
    }
}
