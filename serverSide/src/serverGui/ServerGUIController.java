package serverGui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
//import entities.ConnectedClient;
import BMServer.DBController;
import BMServer.EchoServer;
import BMServer.ServerUI;

public class ServerGUIController {

    @FXML
    private TextField txtPort;

    @FXML
    private TextField txtHostName;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtSchemaName;

    @FXML
    private Button btnConnect;

    @FXML
    private Button btnDisconnect;

    //@FXML
    //private ImageView imageView;

    @FXML
    private Label lblSQLConnection;

    @FXML
    private Label lblFooter;
    
    public static String userName, password, hostName, schemaName;

    @FXML
    private void handleConnect() {
        // Handle connect button action
        String port = txtPort.getText();
        String hostName = txtHostName.getText();
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        String schemaName = txtSchemaName.getText();

        // Your connection logic here
        System.out.println("Connecting to SQL server with details:");
        System.out.println("Port: " + port);
        System.out.println("Host Name: " + hostName);
        System.out.println("User Name: " + userName);
        System.out.println("Password: " + password);
        System.out.println("Schema Name: " + schemaName);

        // Example: You might want to set a status label or log this info
        lblSQLConnection.setText("Connected to SQL server.");
    }

    @FXML
    private void handleExit() {
        // Handle exit button action
        Stage stage = (Stage) btnDisconnect.getScene().getWindow();
        stage.close();
    }
    
    
    public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/serverGui/ServerGUI.fxml")); // TODO package

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/GNServer/ServerGUI.css").toExternalForm());// TODO package
		primaryStage.setTitle("ServerUI");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
    
    
}
