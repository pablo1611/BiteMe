package BMgui;

import BMclient.BMClientController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class ClientConnectionController extends AbstractController {

    public ClientConnectionController() {
        super("ClientConnectionController");
    }

    @FXML
    private Button btnConnect, btnExit;

    @FXML
    private TextField txtPortNumber, txtServerAddress;

    private BMClientController clientController;

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/BMgui/ClientConnection.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Client Connection");
        primaryStage.show();
    }

    @FXML
    public void initialize() {
        // Set default value for server address and make it non-editable
        //txtServerAddress.setText("localhost");
        //txtServerAddress.setEditable(false);
    }

    @FXML
    private void connectButtonClicked(ActionEvent event) {
        String serverAddress = txtServerAddress.getText();
        String port = txtPortNumber.getText();

        if (serverAddress.trim().isEmpty() || port.trim().isEmpty() || !port.matches("\\d+")) {
            showAlert(Alert.AlertType.ERROR, "You must enter a valid server address and digits-only port number");
        } else if (!(Integer.parseInt(port) >= 1024 && Integer.parseInt(port) <= 65535)) {
            showAlert(Alert.AlertType.ERROR, "Port number must be in range (1024-65535)");
        } else {
            clientController = new BMClientController(serverAddress, Integer.parseInt(port));
            boolean connected = clientController.connectClientToServer(serverAddress, Integer.parseInt(port));
            if (connected) {
                showAlert(Alert.AlertType.INFORMATION, "Connected to server successfully");
                showOrderDetailsScreen((Stage) btnConnect.getScene().getWindow());
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to connect to server");
            }
        }
    }

    @FXML
    private void exitButtonClicked(ActionEvent event) {
        ((Stage) btnExit.getScene().getWindow()).close();
    }

    private void showOrderDetailsScreen(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BMgui/OrderDetails.fxml"));
            Parent root = loader.load();
            OrderDetailsController controller = loader.getController();
            controller.setClientController(clientController);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Order Details");
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Failed to load order details screen: " + e.getMessage());
        }
    }
}

