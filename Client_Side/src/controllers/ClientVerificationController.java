package controllers;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ClientVerificationController {

    @FXML
    private TextField customerIdTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button verifyButton;

    @FXML
    private Label verificationStatusLabel;

    @FXML
    public void initialize() {
    }

    @FXML
    private void handleVerifyButtonAction() {
    }
}