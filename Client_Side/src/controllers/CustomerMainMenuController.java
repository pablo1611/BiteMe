package controllers;
import client.ChatClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.io.IOException;

/**
 * Controller for the Welcome Page of the application. 
 * This class handles the navigation from the welcome page to other pages based on the user's choice, 
 * including traveler login, worker login, and creating a reservation.
 */
public class CustomerMainMenuController extends AbstractController {
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
		ChatClient client= ClientUI.getClient();
		handleExit(event);
	}

    /**
     * Navigates to the page for creating an order.
     * @param event The action event that triggered this method.
     */
    @FXML
    void CreateOrder(ActionEvent event) throws IOException {
		//should pass the user instance in order to create an order with specific user.
		String path1="/gui/CustomerDetailsPage.fxml";
		String path2="/gui/CustomerDetailsPage.css";
		navigateTo(path1,path2, (Node) event.getSource());
    }

	@FXML
	void Disconnect(ActionEvent event) throws IOException {
		handleLogout(event);
	}

}


	    

		
	    
	    
