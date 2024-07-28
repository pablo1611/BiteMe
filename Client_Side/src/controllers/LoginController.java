package controllers;

import client.ClientUI;
import common.RequestType;
import entities.Request;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LoginController extends AbstractController {
    @FXML
    private Button btnExit;
    @FXML
    private Button btnLogin;
    @FXML
    private Label lblPassword;
    @FXML
    private Label lblRC;
    @FXML
    private Label lblUserName;
    @FXML
    private Label lblInvalidPW;
    @FXML
    private Label lblIDnr;
    @FXML
    private Label lblconnected;
    @FXML
    private PasswordField txtFPassword;
    @FXML
    private TextField txtFUserName;

    boolean flag;

    @FXML
    void Exit(ActionEvent event) throws IOException {
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

    @FXML
    void Back(ActionEvent event) throws IOException {
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/gui/ServerIP.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/gui/ServerIP.css").toExternalForm());
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @FXML
    void Login(ActionEvent event) throws IOException {
        String id = txtFUserName.getText();
        String password = txtFPassword.getText();
        User user = new User(id, password);

        Request request = new Request();
        request.setType(RequestType.USER_LOGIN);
        request.setRequest(user);
        System.out.println("Login request: " + request.getRequest().toString());

        byte[] arr;
        try {
            arr = request.getBytes();
            System.out.println("Serialized bytes: " + arr.length);
            ClientUI.chat.handleMessageFromClientUI(arr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Wait for the server response
        while (ClientUI.chat.user == null) {
            // Optional: Add a timeout to avoid infinite loop
            try {
                Thread.sleep(100); // Sleep for 100ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("User after login: " + ClientUI.chat.user);
        user = ClientUI.chat.user;

        if (user == null) {
            lblInvalidPW.setText("Login failed, please try again.");
            lblInvalidPW.setVisible(true);
            return;
        }

        switch (user.getPermission()) {
            case "Password invalid":
                lblInvalidPW.setVisible(true);
                break;
            case "ID not registered":
                lblIDnr.setVisible(true);
                break;
            case "Connected":
                lblconnected.setVisible(true);
                User loggedInUser = ClientUI.chat.user;
                openAppropriateMenu(event, loggedInUser);
                break;
            default:
                lblInvalidPW.setText("Unexpected error, please try again.");
                lblInvalidPW.setVisible(true);
                break;
        }
    }

    private void openAppropriateMenu(ActionEvent event, User user) {
        try {
            String fxmlFile;
            String cssFile;
            fxmlFile = "/gui/CustomerMainMenu.fxml";
            cssFile = "/gui/CustomerMainMenu.css";

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // Get the controller of the next screen
            AbstractController controller = loader.getController();
            // Pass the user to the controller
            controller.setUser(user);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

