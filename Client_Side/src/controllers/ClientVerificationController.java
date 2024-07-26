package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client.ClientGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
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
    private void handleVerifyButtonAction(MouseEvent event) {
    	String userName = customerIdTextField.getText();
		String password = passwordTextField.getText();
		if(!checkValidText(userName) && !checkValidText(password)) 
		{
			return;
		}
		
    	
		
		
		
		
    }
    private boolean checkValidText(String input) {
		if (input == null || input.equals("")) {
			return false;
		}
		return true;
	}

	public void start(Stage primaryStage) {
		// TODO Auto-generated method stub
		
	}
    
}