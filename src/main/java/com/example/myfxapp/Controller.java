package com.example.myfxapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML private Parent root;
    @FXML private Scene scene;
    @FXML private Stage stage;

    @FXML private ImageView logo;

    @FXML private Label loginText01;
    @FXML private Label loginText02;
    @FXML private Button loginButton;
    @FXML private TextField usernameText;
    @FXML private PasswordField passwordText;
    @FXML private Button esqueciButton;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File("src/main/java/com/example/myfxapp/imagens/icon/Logo.png");
        Image image = new Image(file.toURI().toString());
        logo.setImage(image);
    }

    public void changeScene(String endereco, String titulo, Stage stageAnterior) {
        if (endereco == null)   return;

        try {
            root = FXMLLoader.load(getClass().getResource(endereco));
            scene = new Scene(root);
            stage = new Stage();
            Image icon= new Image(new File("src/main/java/com/example/myfxapp/imagens/icon/Logo.png").toURI().toString());
            stage.setTitle(titulo);
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
                loginText01.setText("dados invalidos,");
                loginText02.setText("digite novamente");
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
