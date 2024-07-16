package BMgui;

import javafx.scene.control.Alert;

public abstract class AbstractController {
    private String controllerName;

    public AbstractController(String controllerName) {
        this.controllerName = controllerName;
    }

    public String getControllerName() {
        return controllerName;
    }

    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }

    // Add any shared methods or fields here
    protected void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(type.name());
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

