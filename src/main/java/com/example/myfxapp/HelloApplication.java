package com.example.myfxapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("WoodPecker Furniture - login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
//        EmailController email= new EmailController();
//
//        email.enviarEmail("rafasilvam220@gmail.com");

    }
}