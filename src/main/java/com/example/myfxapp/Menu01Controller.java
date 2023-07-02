package com.example.myfxapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Menu01Controller implements Initializable{
    @FXML
    private Button logoutButton;
    @FXML private Label bemVindoText;
    @FXML private Button consultarPerfilButton;
    @FXML private Button consultarEstoqueButton;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        bemVindoText.setText(Data.userLogedIn);
    }

    @FXML
    protected void onLogoutButtonClick() {
        new Controller().changeScene("login.fxml", (Stage) logoutButton.getScene().getWindow());
        Data.userLogedIn = null;
    }

    @FXML
    protected void onConsultarEstoqueButtonClick() {
        new Controller().changeScene("consultarEstoque.fxml", (Stage) consultarEstoqueButton.getScene().getWindow());
    }

    @FXML
    protected void onAtualizarEstoqueButtonClick() {
    }

    @FXML
    protected void onRegistrarUsuarioButtonClick() {
    }

    @FXML
    protected void onRegistrarVendaButtonClick() {
    }

    @FXML
    protected void onConsultarPerfilClick() {
        new Controller().changeScene("profile.fxml", (Stage) consultarPerfilButton.getScene().getWindow());
    }
}
