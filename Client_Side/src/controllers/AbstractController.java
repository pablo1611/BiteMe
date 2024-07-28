package controllers;

import java.io.IOException;

import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public abstract class AbstractController {

    protected User currentUser;

    @FXML
    protected Label lblUserName;

    public void setUser(User user) {
        this.currentUser = user;
        if (lblUserName != null) {
            lblUserName.setText(user.getUserName());
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }
    protected void navigateTo(String fxmlFile, Node eventSource) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            AbstractController controller = loader.getController();
            controller.setUser(currentUser);
            Stage stage = (Stage) eventSource.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void handleBack(String fxmlFile, Node eventSource) {
        navigateTo(fxmlFile, eventSource);
    }

    protected void handleNext(String fxmlFile, Node eventSource) {
        navigateTo(fxmlFile, eventSource);
    }
}

