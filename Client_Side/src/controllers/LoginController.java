package controllers;
import client.ChatClient;
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

import java.io.IOException;


/**
 * Controller class for the Worker Login Page.
 * This class handles the authentication process for workers attempting to log in to the system.
 */
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
	private Label lblnotExist;


	@FXML
    private Label lblInvalidPW;

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
		ChatClient client = ClientUI.getClient();
		if (client != null) {
			try {
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
		//primaryStage.initStyle(StageStyle.UNDECORATED);
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
		String username = txtFUserName.getText();
		String password = txtFPassword.getText();
		User user = new User(username, password);

		ChatClient client = ClientUI.getClient();

		Request request = new Request();
		request.setType(RequestType.USER_LOGIN);
		request.setRequest(user);
		System.out.println(request.getRequest().toString());

		try {
			byte[] arr = request.getBytes();
			System.out.println("Serialized bytes: " + arr.length);
			client.handleMessageFromClientUI(arr);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(client.user.toString());
		user = client.user;

		switch (client.user.getPermission()) {
			case "Password invalid":
				lblInvalidPW.setVisible(true);
				break;
			case "User not Exist":
				lblnotExist.setVisible(true);
				break;
			case "Connected":
				lblconnected.setVisible(true);
				openAppropriateMenu(event, client.user);
				break;
		}
	}

	private void openAppropriateMenu(ActionEvent event, User user) {
		try {
			String fxmlFile;
			String cssFile;

			if (user.getRole().equalsIgnoreCase("Manager")) {
				fxmlFile = "/gui/ManagerMainMenu.fxml";
				cssFile = "/gui/ManagerMainMenu.css";
			} else if (user.getRole().equalsIgnoreCase("CEO")) {
				fxmlFile = "/gui/CEOMainMenu.fxml";
				cssFile = "/gui/CEOMainMenu.css";
			} else {
				fxmlFile = "/gui/CustomerMainMenu.fxml";
				cssFile = "/gui/CustomerMainMenu.css";
			}


			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
			Parent root = loader.load();

			// Get the controller of the next screen
			AbstractController controller = loader.getController();
			// Pass the user to the controller
			controller.setUser(user);

			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window

			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource(cssFile).toExternalForm());
			Stage primaryStage = new Stage();
			//primaryStage.initStyle(StageStyle.UNDECORATED);
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
