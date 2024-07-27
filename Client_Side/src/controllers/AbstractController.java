package controllers;

import entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public abstract class AbstractController {
    protected String userName;
    protected User currentUser;

    @FXML
    protected Label lblUserName;

    public void setUser(User user) {
        this.currentUser = user;
        this.userName=user.getUserName();
        if (lblUserName != null) {
            lblUserName.setText(user.getUserName());
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }
}