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
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Controller {
    @FXML private Parent root;
    @FXML private Scene scene;
    @FXML private Stage stage;

    @FXML private Label loginText;
    @FXML private Button loginButton;
    @FXML private TextField usernameText;
    @FXML private PasswordField passwordText;
    @FXML private Button esqueciButton;

    public void changeScene(String endereco, String titulo, Stage stageAnterior) {
        if (endereco == null)   return;

        try {
            root = FXMLLoader.load(getClass().getResource(endereco));
            scene = new Scene(root);
            stage = new Stage();
            Image icon= new Image(new File("src/main/java/com/example/myfxapp/imagens/icon/Logo.png").toURI().toString());
            stage.setTitle(titulo);
            stage.getIcons().add(icon);
            scene.getStylesheets().add(new File("src/main/java/styles/Styles.css").toURI().toString());
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
        changeScene("RenovarSenha.fxml","Esqueci minha senha", (Stage) esqueciButton.getScene().getWindow());

    }

    public void validateLogin() {
        DatabaseHandler connect = new DatabaseHandler();
        Connection conDB = connect.getConnection();

        String selectString = "select count(1) from registro_vendedor where username = ? and password = ?";

        try {
            PreparedStatement selectUser = conDB.prepareStatement(selectString);
            conDB.setAutoCommit(false);

            selectUser.setString(1, usernameText.getText());
            selectUser.setString(2, passwordText.getText());

            ResultSet rs = selectUser.executeQuery();

            rs.next();
            if (rs.getInt(1) == 1) {
                Data.userLogedIn = usernameText.getText();
                changeScene("menu01.fxml", "Menu Principal",(Stage) loginButton.getScene().getWindow());
            } else {
                loginText.setText("dados invalidos, digite novamente");
                usernameText.setText("");
                passwordText.setText("");
            }

            conDB.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
