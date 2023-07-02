package com.example.myfxapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Controller {
    @FXML private Parent root;
    @FXML private Scene scene;
    @FXML private Stage stage;

    @FXML private Label loginText;
    @FXML private Button loginButton;
    @FXML private TextField usernameText;
    @FXML private PasswordField passwordText;
    @FXML private Button esqueciButton;


    public void changeScene(String endereco, Stage stageAnterior) {
        if (endereco == null)   return;

        try {
            root = FXMLLoader.load(getClass().getResource(endereco));
            scene = new Scene(root);
            stage = new Stage();
            Image icon= new Image(new File("src/main/java/com/example/myfxapp/imagens/icon/Logo.png").toURI().toString());
            stage.setTitle("Hello!");
            stage.getIcons().add(icon);
            stage.setScene(scene);
            stage.show();
            stageAnterior.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    protected void onLoginButtonClick() {
        validateLogin();
    }

    @FXML
    protected void onEsqueciButtonClick(){
        changeScene("RenovarSenha.fxml", (Stage) esqueciButton.getScene().getWindow());

    }

    public void validateLogin() {
        DatabaseHandler connect = new DatabaseHandler();
        Connection conDB = connect.getConnection();

        String verifyLogin = "select count(1) from registro_vendedor where username = '" + usernameText.getText() + "' and password = '" + passwordText.getText() + "';";

        try {
            Statement stmt = conDB.createStatement();
            ResultSet rs = stmt.executeQuery(verifyLogin);

            while (rs.next()) {
                if (rs.getInt(1) == 1) {
                    Data.userLogedIn = usernameText.getText();
                    changeScene("menu01.fxml", (Stage) loginButton.getScene().getWindow());
                    conDB.close();
                    return;
                }
                else {
                    loginText.setText("dados invalidos, digite novamente");
                    usernameText.setText("");
                    passwordText.setText("");
                    return;
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}