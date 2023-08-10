package com.example.myfxapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrarClienteController {
    @FXML private Button confirmarDadosButton;

    @FXML private TextField cpfTextField;

    @FXML private TextField emailTextField;

    @FXML private TextField enderecoTextField;

    @FXML private TextField nomeTextField;

    @FXML
    private Label resultadoLabel;

    @FXML
    private TextField sobrenomeTextField;

    @FXML
    private TextField telefoneTextField;

    @FXML
    private Button voltarButton;

    @FXML
    protected void confirmarDadosButtonOnCLick(ActionEvent event) {
        if(cadastraUsuario())
            resultadoLabel.setText("Vendedor Cadastrado");
        else
            resultadoLabel.setText("Não cadastrado!\nTente Novamente");
    }

    @FXML
    protected void voltarButtonOnClick(ActionEvent event) {
        new Controller().changeScene("menu01.fxml", "Menu Principal", (Stage) voltarButton.getScene().getWindow());
    }

    public boolean cadastraUsuario(){
        DatabaseHandler connect = new DatabaseHandler();
        Connection conDB = connect.getConnection();

        if(cpfTextField.getText().isEmpty()||emailTextField.getText().isEmpty()||enderecoTextField.getText().isEmpty()||nomeTextField.getText().isEmpty()||sobrenomeTextField.getText().isEmpty()||telefoneTextField.getText().isEmpty()) {
            return false;
        }

        String criarCliente = "INSERT INTO registro_cliente (nome,sobrenome,email,telefone,cpf,endereco) VALUES (?,?,?,?,?,?)";

        try {
            PreparedStatement prst= conDB.prepareStatement(criarCliente);
            prst.setString(1, nomeTextField.getText());
            prst.setString(2, sobrenomeTextField.getText());
            prst.setString(3, emailTextField.getText());
            prst.setString(4, telefoneTextField.getText());
            prst.setString(5, cpfTextField.getText());
            prst.setString(6, enderecoTextField.getText());

            prst.executeUpdate();
            conDB.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }
}

