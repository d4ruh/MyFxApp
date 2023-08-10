package com.example.myfxapp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class RefazerSenha implements Initializable {

    @FXML private PasswordField senhaPassowordField;
    @FXML private PasswordField senhaPassowordField2;
    @FXML private Label usernameLabel;
    @FXML private Button confirmarbutton;
    @FXML private Button voltarButton;
    private String username;
    private String usernameText;
    private String email;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DatabaseHandler connect = new DatabaseHandler();
        Connection conDB = connect.getConnection();

        String sql="select username from registro_vendedor where email=?";

        try{
            PreparedStatement ps= conDB.prepareStatement(sql);
            ps.setString(1, Data.emailUsuarioEsquecido);
            this.email=Data.emailUsuarioEsquecido;
            Data.emailUsuarioEsquecido=null;
            ResultSet rs= ps.executeQuery();

            rs.next();
            this.username=rs.getString("username");
            this.usernameText=usernameLabel.getText()+" "+rs.getString("username");
            String username=usernameText;
            usernameLabel.setText(username);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
    @FXML protected void confirmarbuttonOnClick(){
        if(senhaPassowordField.getText().equals(senhaPassowordField2.getText())){
            if ( atualizarSenha(email,senhaPassowordField.getText())){
                usernameLabel.setText("Senha atualizada com sucesso "+this.username);
                new Controller().changeScene("login.fxml", "WoodPecker Furniture - login", (Stage) confirmarbutton.getScene().getWindow());
            }
            else{
                usernameLabel.setText("Nao foi possivel atualizar a senha, tente novamente!");
                usernameLabel.setText(usernameText);
            }

        }
        else{
            usernameLabel.setText("Senha esta incorreta,tente digita-la novamente");
            usernameLabel.setText(usernameText);
        }


    }

    @FXML protected void voltarButtonOnClick(){
        new Controller().changeScene("login.fxml", "WoodPecker Furniture - login", (Stage) confirmarbutton.getScene().getWindow());
    }

    private boolean atualizarSenha(String email,String novaSenha){
        DatabaseHandler connect= new DatabaseHandler();
        Connection conDb=connect.getConnection();

        String sql= "update registro_vendedor set password=? where email=?";

        try{
            PreparedStatement prst= conDb.prepareStatement(sql);
            prst.setString(1, novaSenha);
            prst.setString(2, email);
            prst.executeUpdate();
            return true;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
