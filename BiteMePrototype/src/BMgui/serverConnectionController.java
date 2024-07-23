package BMgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import BMserver.BMServerUI;

public class serverConnectionController {

    @FXML
    private TextField txtPortNumber;

    @FXML
    private Button btnStartServer;

    @FXML
    private void startServer(ActionEvent event) {
        String port = txtPortNumber.getText();
        if (port.trim().isEmpty() || !port.matches("\\d+")) {
            showAlert(Alert.AlertType.ERROR, "You must enter a valid digits-only port number");
        } else if (!(Integer.parseInt(port) >= 1024 && Integer.parseInt(port) <= 65535)) {
            showAlert(Alert.AlertType.ERROR, "Port number must be in range (1024-65535)");
        } else {
            BMServerUI.runServer(port);
            showAlert(Alert.AlertType.INFORMATION, "Server started successfully on port " + port);
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(type.name());
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
