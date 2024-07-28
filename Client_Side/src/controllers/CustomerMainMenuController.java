package controllers;

import java.io.IOException;
import entities.Request;
import common.RequestType;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CustomerMainMenuController extends AbstractController {

    @FXML
    private Button btnNewOrder;

    @FXML
    private Button btnOrderHistory;

    @FXML
    private Button btnExit;

    @FXML
    void handleNewOrder(ActionEvent event) {
        try {
            // Load the CustomerDetailsPage.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/CustomerDetailsPage.fxml"));
            Parent root = loader.load();

            // Pass user data to the CustomerDetailsController
            AbstractController controller = loader.getController();
            controller.setUser(currentUser);

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            System.err.println("Error casting event source to Node: " + e.getMessage());
        }
    }
    
    
    @FXML
    void handleOrderHistory(ActionEvent event) {
        System.out.println("Order History button clicked");
    }

    @FXML
    void Exit(ActionEvent event) {
        try {
            System.out.println("Preparing logout request for user: " + currentUser);
            Request request = new Request();
            request.setType(RequestType.USER_LOGOUT);
            request.setRequest(currentUser);
            System.out.println("Request object: " + request);

            byte[] arr = request.getBytes();
            System.out.println("Serialized logout request: " + arr);
            ClientUI.chat.handleMessageFromClientUI(arr);

            // Wait for logout confirmation
            while (!ClientUI.chat.logout) {
                Thread.sleep(100);
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Error while sending logout request: " + e.getMessage());
        }

        // Close connection and quit application
        if (ClientUI.chat != null) {
            try {
                ClientUI.chat.closeConnection();
                ClientUI.chat.quit();
            } catch (Exception e) {
                System.out.println("Error while closing connection: " + e.getMessage());
            }
        }
        System.exit(0);
    }
}

	    
