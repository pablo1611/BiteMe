package controllers;

import java.io.IOException;
import entities.Request;
import common.RequestType;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CustomerMainMenuController extends AbstractController {

    @FXML
    private Button btnNewOrder;

    @FXML
    private Button btnOrderHistory;

    @FXML
    private Button btnExit;

    @FXML
    void handleNewOrder(ActionEvent event) {
        System.out.println("New Order button clicked");
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

	    
