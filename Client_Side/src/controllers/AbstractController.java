package controllers;

import entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
}

