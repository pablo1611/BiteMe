package controllers;

import client.ClientUI;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ServerIpController extends Application {
    public static String IP;
    public static int Port;

    @FXML
    private Button btnIP;

    @FXML
    private Label lblServerip;

    @FXML
    private TextField txtIP;

    @FXML
    private TextField txtPortNumber;

    @FXML
    void Exit(ActionEvent event) throws IOException {
        System.exit(0);
    }

    public void getIP(ActionEvent event) throws Exception {
        try {
            IP = txtIP.getText();
            Port = Integer.parseInt(txtPortNumber.getText());
            System.out.println(Port);
            System.out.println(IP);
            ClientUI.host = IP;
        } catch (ArrayIndexOutOfBoundsException e) {
            ClientUI.host = "localhost-default";
        }

        ClientUI.initializeClient(IP, Port);

        ((Node) event.getSource()).getScene().getWindow().hide(); 
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/gui/Login.fxml"));

        Scene scene = new Scene(root);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/ServerIP.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/gui/serverIP.css").toExternalForm());

        primaryStage.setTitle("Connect to server");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
