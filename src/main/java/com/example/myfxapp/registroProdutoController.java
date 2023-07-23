package com.example.myfxapp;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class registroProdutoController implements Initializable {
    @FXML private Label msgLabel;

    @FXML private TextField nomeText;
    @FXML private TextField quantText;
    @FXML private TextField valorText;
    @FXML private TextField classeText;

    @FXML private Button cancelarButton;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        if (Data.produtoSelecionado == null) {
            msgLabel.setText("Insira os dados do novo produto");
            nomeText.setEditable(true);
            return;
        }
        msgLabel.setText("Altere os dados do produto");

        nomeText.setText(Data.produtoSelecionado);

        DatabaseHandler connect = new DatabaseHandler();
        Connection conDB = connect.getConnection();

        String getData;
        getData = "select classe, quantidade, valor from registro_estoque_produtos where nome='" + Data.produtoSelecionado + "';";

        try {
            Statement stmt = conDB.createStatement();
            ResultSet rs = stmt.executeQuery(getData);

            rs.next();

            classeText.setText(rs.getString(1));
            quantText.setText("" + rs.getInt(2));
            valorText.setText("" + rs.getDouble(3));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    protected void onConfirmarButtonClick() {
        DatabaseHandler connect = new DatabaseHandler();
        Connection conDB = connect.getConnection();

        String getData;
        try {
            if (Data.produtoSelecionado == null) {
                getData = "insert into registro_estoque_produtos ( nome, classe, quantidade, valor ) values ( ?, ?, ?, ? )";

                PreparedStatement stmt = conDB.prepareStatement(getData);

                stmt.setString(1, nomeText.getText());
                stmt.setString(2, classeText.getText());
                stmt.setInt(3, Integer.parseInt(quantText.getText()));
                stmt.setDouble(4, Double.parseDouble(valorText.getText()));

                stmt.executeUpdate();
            }
            else {
                getData = "update registro_estoque_produtos set classe = ?, quantidade = ?, valor = ? where  nome = ? ";

                PreparedStatement stmt = conDB.prepareStatement(getData);

                stmt.setString(1, classeText.getText());
                stmt.setInt(2, Integer.parseInt(quantText.getText()));
                stmt.setDouble(3, Double.parseDouble(valorText.getText()));
                stmt.setString(4, Data.produtoSelecionado);

                stmt.executeUpdate();
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Data.produtoSelecionado = null;
        new Controller().changeScene("consultarEstoque.fxml", "Menu Principal",(Stage) cancelarButton.getScene().getWindow());
    }

    @FXML
    public void onCancelarButtonClick() {
        Data.produtoSelecionado = null;
        new Controller().changeScene("consultarEstoque.fxml", "Menu Principal",(Stage) cancelarButton.getScene().getWindow());
    }
}
