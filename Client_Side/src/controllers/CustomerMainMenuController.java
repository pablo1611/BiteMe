package controllers;

import client.ChatClient;
import client.ClientUI;
import entities.User;
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
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the Welcome Page of the application. 
 * This class handles the navigation from the welcome page to other pages based on the user's choice, 
 * including traveler login, worker login, and creating a reservation.
 */
public class CustomerMainMenuController implements Initializable {
	@FXML
	private Button btnExit;
    @FXML
    private Button btnCO;

    @FXML
    private Button btnDisconnect;

    @FXML
    private Button btnOrderHistory;

    @FXML
    private Label lblIDnr;

    @FXML
    private Label lblInvalidPW;

    @FXML
    private Label lblconnected;
    /**
     * Handles the action to exit the application. 
     * This method closes the connection to the server and quits the application.
     *
     * @param event The action event that triggered this method.
     * @throws IOException If an error occurs during the process.
     */
    @FXML
	void Exit(ActionEvent event) throws IOException {
		ChatClient client = ClientUI.getClient();
		if (client != null) {
			try {
			//	Disconnect(event);
				client.closeConnection();
				client.quit();
			} catch (Exception e) {
				System.out.println("Error while closing connection: " + e.getMessage());
			}
		}
		// Close the application
		System.exit(0);
	}

    /**
     * Navigates to the page for creating a reservation. 
     * This method hides the current window and opens the reservation page.
     *
     * @param event The action event that triggered this method.
     * @throws IOException If an error occurs during loading the FXML.
     */
    @FXML
    void CreateOrder(ActionEvent event) throws IOException {
		//should pass the user instance in order to create an order with specific user.
		String path1="/gui/CustomerDetailsPage.fxml";
		String path2="/gui/CustomerDetailsPage.css";
		openAppropriateMenu(event,path1,path2);
    }
    /**
     * Navigates to the traveler login page.
     * This method hides the current window and opens the traveler login page.
     *
     * @param event The action event that triggered this method.
     * @throws IOException If an error occurs during loading the FXML.
     */
    @FXML
    void Disconnect(ActionEvent event) throws IOException {

		//should implement the User Connection Status to disconnect, and handle case of exit.
		
		String path1="/gui/Login.fxml";
		String path2="/gui/Login.css";
		openAppropriateMenu(event,path1,path2);
    }
    /**
     * Navigates to the worker login page. 
     * This method hides the current window and opens the worker login page.
     *
     * @param event The action event that triggered this method.
     * @throws IOException If an error occurs during loading the FXML.
     */
    @FXML
    void OrderHistory(ActionEvent event) throws IOException  {
		String path1="/gui/OrderHistoryPage.fxml";
		String path2="/gui/OrderHistoryPage.css";
		openAppropriateMenu(event,path1,path2);
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



    /**
     * Initializes the controller class. 
     * This method is automatically called after the FXML file has been loaded. 
     * It can be used to initialize the state of the controller.
     *
     * @param arg0 The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param arg1 The resources used to localize the root object, or null if the root object was not localized.
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
	    

		
	    
	    
