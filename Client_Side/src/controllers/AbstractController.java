package controllers;

import client.ChatClient;
import client.ClientUI;
import entities.User;
import entities.Request;
import common.RequestType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public abstract class AbstractController {
    protected User currentUser;

    @FXML
    protected Label lblUserName;

    public void setUser(User user) {
        this.currentUser = user;
        if (lblUserName != null) {
            lblUserName.setText(user.getUserName());
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    protected void navigateTo(String fxmlFile, String cssFile, Node eventSource) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            AbstractController controller = loader.getController();
            controller.setUser(currentUser);
            Stage stage = (Stage) eventSource.getScene().getWindow();
            Scene scene = new Scene(root);

            // Add CSS if provided
            if (cssFile != null && !cssFile.isEmpty()) {
                scene.getStylesheets().add(getClass().getResource(cssFile).toExternalForm());
            }

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Consider adding more robust error handling here
        }
    }


    protected void handleLogout(ActionEvent event) throws IOException {
        ChatClient client = ClientUI.getClient();
        try {
            System.out.println("Preparing logout request for user: " + currentUser);
            Request request = new Request();
            request.setType(RequestType.USER_LOGOUT);
            request.setRequest(currentUser);
            System.out.println("Request object: " + request);

            byte[] arr = request.getBytes();
            System.out.println("Serialized logout request: " + arr);
            client.handleMessageFromClientUI(arr);

        } catch (IOException e) {
            System.out.println("Error while sending logout request: " + e.getMessage());
        }

        // Navigate to login page
        navigateTo("/gui/Login.fxml","/gui/Login.css", (Node) event.getSource());
    }

    protected void handleExit(ActionEvent event) throws IOException {
        ChatClient client = ClientUI.getClient();
        try {
            System.out.println("Preparing logout request for user: " + currentUser);
            Request request = new Request();
            request.setType(RequestType.USER_LOGOUT);
            request.setRequest(currentUser);
            System.out.println("Request object: " + request);

            byte[] arr = request.getBytes();
            System.out.println("Serialized logout request: " + arr);
            client.handleMessageFromClientUI(arr);

        } catch (IOException e) {
            System.out.println("Error while sending logout request: " + e.getMessage());
        }

        // Close connection and quit application
        if (client != null) {
            try {
                client.closeConnection();
                client.quit();
            } catch (Exception e) {
                System.out.println("Error while closing connection: " + e.getMessage());
            }
        }
        System.exit(0);
    }
}