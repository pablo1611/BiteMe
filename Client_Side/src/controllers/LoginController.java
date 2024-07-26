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


/**
 * Controller class for the Worker Login Page.
 * This class handles the authentication process for workers attempting to log in to the system.
 */
public class LoginController {
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
    /**
     * Exits the application.
     * This method is triggered when the Exit button is pressed.
     *
     * @param event The action event that triggered this method.
     * @throws IOException If an error occurs during the process.
     */
	@FXML
	void Exit(ActionEvent event) throws IOException {
		if (ServerIpController.clientController != null) {
			try {
				ServerIpController.clientController.getClient().closeConnection();
				ServerIpController.clientController.getClient().quit();
			} catch (Exception e) {
				System.out.println("Error while closing connection: " + e.getMessage());
			}
		}
		// Close the application
		System.exit(0);
	}
    /**
     * Navigates back to the Welcome Page.
     * This method is triggered when the Back button is pressed.
     *
     * @param event The action event that triggered this method.
     * @throws IOException If an error occurs during loading the FXML.
     */
    @FXML
    void Back(ActionEvent event) throws IOException {
    	((Node) event.getSource()).getScene().getWindow().hide();; //hiding primary window
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ServerIP.fxml"));
		
		
		Scene scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource("/gui/ServerIP.css").toExternalForm());
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
			
		primaryStage.show();
	
	
    }
    /**
     * Attempts to log in a worker.
     * This method validates the user's credentials against the system's records. If successful, 
     * it redirects the user to their respective dashboard based on their role. If the login attempt fails,
     * appropriate error messages are displayed.
     *
     * @param event The action event that triggered this method.
     * @throws IOException If an error occurs during the process.
     */
    @FXML
    void Login(ActionEvent event) throws IOException {
		String id=txtFUserName.getText();
		String password=txtFPassword.getText();
		User user = new User(id,password);

		//send to server:
		Request request= new Request();
		request.setType(RequestType.USER_LOGIN);
		request.setRequest(user);
		System.out.println(request.getRequest().toString()); //print the request+type of request
		byte[] arr;
		try {
			arr = request.getBytes();
			System.out.println("Serialized bytes: " + arr.length);
			ServerIpController.clientController.getClient().handleMessageFromClientUI(arr);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(	ServerIpController.clientController.getClient().user.toString());
		user= ServerIpController.clientController.getClient().user;
		switch (ServerIpController.clientController.getClient().user.getPermission()) {

		case "Password invalid":
			lblInvalidPW.setVisible(true);
			break;
		case "ID not registered":
			lblIDnr.setVisible(true);
			break;
			case "Connected":
				lblconnected.setVisible(true);
				User loggedInUser = ServerIpController.clientController.getClient().user;
				openAppropriateMenu(event, loggedInUser);
				break;
//		case "USER":
//			ParkManagerController.user=user;
//			((Node) event.getSource()).getScene().getWindow().hide();; //hiding primary window
//			Stage primaryStage1 = new Stage();
//			Parent root1 = FXMLLoader.load(getClass().getResource("/fxml/ParkManager.fxml"));
//
//			Scene scene1 = new Scene(root1);
//			//scene1.getStylesheets().add(getClass().getResource("/fxml/ParkManager.css").toExternalForm());
//			primaryStage1.setTitle("");
//			primaryStage1.initStyle(StageStyle.UNDECORATED);
//			primaryStage1.setScene(scene1);
//			primaryStage1.setResizable(false);
//			primaryStage1.show();
//			break;
//		case "Manager" :
//			WorkerPageController.user=user;
//			((Node) event.getSource()).getScene().getWindow().hide();; //hiding primary window
//			Stage primaryStage2 = new Stage();
//			Parent root2 = FXMLLoader.load(getClass().getResource("/fxml/WorkerPage.fxml"));
//			Scene scene2 = new Scene(root2);
//			//scene2.getStylesheets().add(getClass().getResource("/fxml/WorkerPage.css").toExternalForm());
//			primaryStage2.setTitle("");
//			primaryStage2.initStyle(StageStyle.UNDECORATED);
//			primaryStage2.setScene(scene2);
//			primaryStage2.setResizable(false);
//			primaryStage2.show();
//			break;
//
//		case "Department Manager" :
//			DepartmentManagerController.user=user;
//			((Node) event.getSource()).getScene().getWindow().hide();; //hiding primary window
//			Stage primaryStage3 = new Stage();
//			Parent root3 = FXMLLoader.load(getClass().getResource("/fxml/DepartmentManager.fxml"));
//			Scene scene3 = new Scene(root3);
//			//scene3.getStylesheets().add(getClass().getResource("/fxml/DepartmentManager.css").toExternalForm());
//			primaryStage3.initStyle(StageStyle.UNDECORATED);
//			primaryStage3.setTitle("");
//			primaryStage3.setScene(scene3);
//			primaryStage3.setResizable(false);
//			primaryStage3.show();
//			break;
		}
    }


	private void openAppropriateMenu(ActionEvent event, User user) {
		try {
			String fxmlFile;
			String cssFile;

//			if (user.getRole().equalsIgnoreCase("Manager")) {
//				fxmlFile = "/gui/ManagerMainMenu.fxml";
//				cssFile = "/gui/ManagerMainMenu.css";
//			} else {
				fxmlFile = "/gui/CustomerMainMenu.fxml";
				cssFile = "/gui/CustomerMainMenu.css";
//			}

			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
			Scene scene = new Scene(root);
		//	scene.getStylesheets().add(getClass().getResource(cssFile).toExternalForm());
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
