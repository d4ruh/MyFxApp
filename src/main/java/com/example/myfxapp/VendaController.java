package com.example.myfxapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class VendaController {
    @FXML private Button voltarButton;

    @FXML
    protected void onPesquisarButtonClick() {

    }

    @FXML
    protected void onEfetivarVendaButtonClick() {

    }

    @FXML
    protected void onVoltarButtonClick() {
        new Controller().changeScene("menu01.fxml", "WoodPecker Furniture - Estoque",(Stage) voltarButton.getScene().getWindow());
    }

    @FXML
    protected void onLogoutButtonClick() {
        new Controller().changeScene("login.fxml", "WoodPecker Furniture - login",(Stage) voltarButton.getScene().getWindow());
        Data.userLogedIn = null;
    }
}
