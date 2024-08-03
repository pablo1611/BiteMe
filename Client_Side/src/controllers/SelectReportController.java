package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entities.User;

public class SelectReportController extends AbstractController implements Initializable {

    @FXML
    private Label lblBranchDetails;

    @FXML
    private Button btnDetailedOrderIncomeReport;

    @FXML
    private Button btnPerformanceReport;

    @FXML
    private Button btnBack;

    /*
    @Override
    public void setUser(User user) {
        super.setUser(user);
        System.out.println("User in setUser: " + user); // Debug
        if (user != null) {
            lblBranchDetails.setText("Select Report for " + user.getRestaurant() + " - " + user.getBranch());
            System.out.println(this.);
            System.out.println("restaurant: "+user.getRestaurant()+"branch: "+user.getBranch());
        } else {
            lblBranchDetails.setText("User data is missing");
        }
    }
    */


    @FXML
    void showDetailedOrderIncomeReport(ActionEvent event) throws IOException {
    	System.out.println("my user is: "+this.currentUser.getUserName());
        navigateTo("/gui/IncomeReport.fxml", "/gui/IncomeReport.css", (Node) event.getSource());
    }

    @FXML
    void showPerformanceReport(ActionEvent event) {
        // This method is intentionally left empty for now
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        navigateTo("/gui/ManagerMainMenu.fxml", "/gui/ManagerMainMenu.css", (Node) event.getSource());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialization code if needed
    }
}

