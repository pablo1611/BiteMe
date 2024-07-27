package controllers;

import client.ChatClient;
import common.RequestType;
import entities.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import client.ClientUI;
import javafx.stage.StageStyle;

import java.io.IOException;

public class CEOMainMenuController extends AbstractController {

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
    void handleLogout(ActionEvent event) throws IOException {

        ChatClient client=ClientUI.getClient();
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

        String path1="/gui/Login.fxml";
        String path2="/gui/Login.css";
        openAppropriateMenu(event,path1,path2);
    }

    /**
     * Handles the action to exit the application.
     * This method closes the connection to the server and quits the application.
     *
     * @param event The action event that triggered this method.
     * @throws IOException If an error occurs during the process.
     */
    @FXML
    void Exit(ActionEvent event) throws IOException {
        ChatClient client=ClientUI.getClient();
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

    private void openAppropriateMenu(ActionEvent event,String path1,String path2) {
        try {
            String fxmlFile=path1;
            String cssFile=path2;

            ((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource(cssFile).toExternalForm());
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setTitle("");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message to the user)
        }
    }


}