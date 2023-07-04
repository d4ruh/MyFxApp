package com.example.myfxapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class RegistroUsuarioController {

    @FXML private Button confirmarDadosButton;

    @FXML private Button voltarButton;

    @FXML private TextField usernameTextField;

    @FXML private TextField nomeTextField;

    @FXML private TextField sobrenomeTextField;


    @FXML private TextField emailTextField;

    @FXML private TextField telefoneTextField;

    @FXML private TextField cpfTextField;

    @FXML private PasswordField senhaTextField;

    @FXML private TextField enderecoTextField;

    @FXML private Label resultadoLabel;

    @FXML
    protected void confirmarDadosButtonOnCLick(){
        if(cadastraUsuario())
            resultadoLabel.setText("Vendedor Cadastrado");
        else
            resultadoLabel.setText("Não cadastrado!\nTente Novamente");
}
public boolean cadastraUsuario(){
    DatabaseHandler connect = new DatabaseHandler();
    Connection conDB = connect.getConnection();
    if(cpfTextField.getText().isEmpty()||usernameTextField.getText().isEmpty()||senhaTextField.getText().isEmpty()) {
        return false;
    }
    String criarUsuario= "insert into registro_vendedor(username,password,nome,sobrenome,email,telefone,cpf,num_vendas,valor_comissao) values('"+usernameTextField.getText()+"','"+senhaTextField.getText()+"','"+nomeTextField.getText()+"','"+sobrenomeTextField.getText()+"','"+ emailTextField.getText()+"','"+telefoneTextField.getText()+"','"+cpfTextField.getText()+"',0,0.0);";
    try{
        Statement stmt=conDB.createStatement();
        stmt.executeUpdate(criarUsuario);
        conDB.close();
        return true;

    }catch(Exception e){
        System.out.println(e.getMessage());
        return false;

    }

}
@FXML
    protected void voltarButtonOnClick(){
        new Controller().changeScene("menu01.fxml", "Menu Principal", (Stage) voltarButton.getScene().getWindow());
}



}
