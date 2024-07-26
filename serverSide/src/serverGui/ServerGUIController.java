package serverGui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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

    @FXML
    private Label lblSQLConnection;

    @FXML
    private Label lblFooter;

    public static String userName, password, hostName, schemaName;

    @FXML
    private void handleConnect() {
        String port = txtPort.getText();
        hostName = txtHostName.getText();
        userName = txtUserName.getText();
        password = txtPassword.getText();
        schemaName = txtSchemaName.getText();

        if (port.trim().isEmpty() || password.equals("") || hostName.equals("") || userName.equals("") || schemaName.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Missing fields");
            alert.setContentText("\t\t\tFill all fields!");
            alert.showAndWait();
        } else {
            ServerUI.runServer(port);
        }
    }

    @FXML
    private void handleExit() {
        Stage stage = (Stage) btnDisconnect.getScene().getWindow();
        stage.close();
    }

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/serverGui/ServerGUI.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/serverGui/ServerGUI.css").toExternalForm());
        primaryStage.setTitle("ServerUI");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}