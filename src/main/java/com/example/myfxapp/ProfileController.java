package com.example.myfxapp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    @FXML Button logoutButton;
    @FXML Button voltarButton;

    @FXML TextField nomeText;
    @FXML TextField usernameText;
    @FXML TextField vendasText;
    @FXML TextField cpfText;
    @FXML TextField comissaoText;
    @FXML TextField passwordText;
    @FXML TextField emailText;
    @FXML TextField telefoneText;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        DatabaseHandler connect = new DatabaseHandler();
        Connection conDB = connect.getConnection();

        String getData = "select * from registro_vendedor where username='" + Data.userLogedIn + "';";

        try {
            Statement stmt = conDB.createStatement();
            ResultSet rs = stmt.executeQuery(getData);

            while (rs.next()) {
                usernameText.setText(rs.getString(1));
                passwordText.setText(rs.getString(2));
                nomeText.setText(rs.getString(3) + " " + rs.getString(4));
                emailText.setText(rs.getString(5));
                telefoneText.setText(rs.getString(6));
                cpfText.setText(rs.getString(7));
                vendasText.setText(rs.getString(8));
                comissaoText.setText(rs.getString(9));
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

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
