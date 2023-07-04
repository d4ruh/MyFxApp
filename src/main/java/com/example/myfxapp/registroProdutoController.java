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

        if (Data.produtoSelecionado == null) {
            getData = "insert into registro_estoque_produtos ( nome, classe, quantidade, valor ) values ( '" + nomeText.getText() + "', '" + classeText.getText() + "', " + Integer.parseInt(quantText.getText()) + ", " + Double.parseDouble(valorText.getText()) + " );";
        }
        else {
            getData = "update registro_estoque_produtos set classe='" + classeText.getText() + "', quantidade=" + Integer.parseInt(quantText.getText()) + ", valor=" + Double.parseDouble(valorText.getText()) + " where  nome='" + Data.produtoSelecionado + "';";
        }

        try {
            Statement stmt = conDB.createStatement();
            stmt.executeUpdate(getData);

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
