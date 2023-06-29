package com.example.myfxapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EsqueciSenhaController {
    @FXML private TextField emailRecuperacaoTextField;
    @FXML private TextField codigoRecuperacaoTextField;
    @FXML private Button enviarCodigoButton;
    @FXML private Label labelEsqueciSenha;
    @FXML private Button voltarButton;



    private EmailController emailController = new EmailController();

    @FXML
    protected void onEnviarCodigoButtonClick(){
        emailController.enviarEmail(emailRecuperacaoTextField.getText());
    }
    @FXML
    protected void onValidarButtonClick(){
        if(codigoRecuperacaoTextField.getText().equals(emailController.codigo))
            labelEsqueciSenha.setText("Correto!");

        else
            labelEsqueciSenha.setText("Codigo incorreto!\nTente Novamente!");
    }

    @FXML
    protected void onVoltarButtonClick(){
        new Controller().changeScene("login.fxml", (Stage) voltarButton.getScene().getWindow());
    }


}
