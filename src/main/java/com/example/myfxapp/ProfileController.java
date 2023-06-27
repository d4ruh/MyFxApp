package com.example.myfxapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ProfileController {
    @FXML Button logoutButton;
    @FXML Button voltarButton;

    @FXML
    protected void onLogoutButtonClick() {
        new Controller().changeScene("login.fxml", (Stage) logoutButton.getScene().getWindow());
        Data.userLogedIn = null;
    }

    @FXML
    protected void onVoltarButtonClick() {
        new Controller().changeScene("menu01.fxml", (Stage) voltarButton.getScene().getWindow());
    }
}
