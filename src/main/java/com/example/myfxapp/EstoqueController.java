package com.example.myfxapp;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class EstoqueController implements Initializable {
    @FXML private TableView<Produto> tabela;
    @FXML private TableColumn<Produto, String> colNome;
    @FXML private TableColumn<Produto, String> colClasse;
    @FXML private TableColumn<Produto, Double> colValor;
    @FXML private TableColumn<Produto, Integer> colQuantidade;

    @FXML private MenuItem nomeOption;
    @FXML private MenuItem classeOption;
    @FXML private MenuItem valorOption;

    @FXML private TextField pesquisaText;

    private String modoBusca;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        colNome.setCellValueFactory(new PropertyValueFactory<Produto, String>("nome"));
        colClasse.setCellValueFactory(new PropertyValueFactory<Produto, String>("classe"));
        colValor.setCellValueFactory(new PropertyValueFactory<Produto, Double>("valor"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<Produto, Integer>("quantidade"));

        nomeOption.setOnAction(event -> {
            modoBusca = "nome";
            System.out.println(modoBusca);
        });

        classeOption.setOnAction(event -> {
            modoBusca = "classe";
            System.out.println(modoBusca);
        });

        valorOption.setOnAction(event -> {
            modoBusca = "valor";
            System.out.println(modoBusca);
        });
    }

    @FXML
    public void onPesquisarButtonClick() {
        DatabaseHandler connect = new DatabaseHandler();
        Connection conDB = connect.getConnection();
        String getData;

        if (modoBusca.equals("valor")) {
            getData = "select nome, classe, quantidade, valor from registro_estoque_produtos where " + modoBusca + " < " + Double.parseDouble(pesquisaText.getText()) + ";";
        }
        else {
            getData = "select nome, classe, quantidade, valor from registro_estoque_produtos where " + modoBusca + " = '" + pesquisaText.getText() + "';";
        }

        try {
            Statement stmt = conDB.createStatement();
            ResultSet rs = stmt.executeQuery(getData);

            ObservableList<Produto> listaProdutos = tabela.getItems();
            listaProdutos.clear();

            while (rs.next()) {
                Produto produto = new Produto( rs.getString(1), rs.getString(2),
                                                rs.getInt(3), rs.getDouble(4));

                listaProdutos.add(produto);
            }

            tabela.setItems(listaProdutos);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
