package com.example.myfxapp;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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

    @FXML private Button adicionarButton;
    @FXML private Button alterarButton;
    @FXML private Button logoutButton;
    @FXML private Button voltarButton;

    private String modoBusca;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        colNome.setCellValueFactory(new PropertyValueFactory<Produto, String>("nome"));
        colClasse.setCellValueFactory(new PropertyValueFactory<Produto, String>("classe"));
        colValor.setCellValueFactory(new PropertyValueFactory<Produto, Double>("valor"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<Produto, Integer>("quantidade"));

        nomeOption.setOnAction(event -> {
            modoBusca = "nome";

        });

        classeOption.setOnAction(event -> {
            modoBusca = "classe";

        });

        valorOption.setOnAction(event -> {
            modoBusca = "valor";
        });

        DatabaseHandler connect = new DatabaseHandler();
        Connection conDB = connect.getConnection();
        String getData;

        getData = "select nome, classe, quantidade, valor from registro_estoque_produtos;";

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

    @FXML
    public void onPesquisarButtonClick() {
        if (modoBusca == null) {
            return;
        }

        DatabaseHandler connect = new DatabaseHandler();
        Connection conDB = connect.getConnection();
        String getData;
        if (modoBusca.equals("valor")) {
            getData = "select nome, classe, quantidade, valor from registro_estoque_produtos where " + modoBusca + " <= " + Double.parseDouble(pesquisaText.getText()) + ";";
        }
        else {
            getData = "select nome, classe, quantidade, valor from registro_estoque_produtos where " + modoBusca + " like '%" + pesquisaText.getText() + "%';";
        }

        try {
            Statement stmt = conDB.createStatement();
            ResultSet rs = stmt.executeQuery(getData);

            ObservableList<Produto> listaProdutos = tabela.getItems();
            listaProdutos.clear();

            while (rs.next()) {
                Produto produto = new Produto( rs.getString(1), rs.getString(2),
                                                rs.getInt(3), +rs.getDouble(4));

                listaProdutos.add(produto);
            }

            tabela.setItems(listaProdutos);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    protected void onLogoutButtonClick() {
        new Controller().changeScene("login.fxml", "WoodPecker Furniture - login",(Stage) logoutButton.getScene().getWindow());
        Data.userLogedIn = null;
    }

    @FXML
    protected void onRemoverButtonClick() {
        TablePosition pos = tabela.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();

        Produto item = tabela.getItems().get(row);
        String data = colNome.getCellObservableValue(item).getValue().toString();
//
        DatabaseHandler connect = new DatabaseHandler();
        Connection conDB = connect.getConnection();

        String deletarProduto = "delete from registro_estoque_produtos where nome = '" + data + "';";

        try {
            Statement stmt=conDB.createStatement();
            stmt.executeUpdate(deletarProduto);

            conDB.close();

            int idSelecionado = tabela.getSelectionModel().getSelectedIndex();
            tabela.getItems().remove(idSelecionado);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    protected void onAdicionarButtonClick() {
        new Controller().changeScene("registroProduto.fxml", "WoodPecker Furniture - Dados de Produto", (Stage) adicionarButton.getScene().getWindow());
    }

    @FXML
    protected void onAlterarButtonClick() {
        TablePosition pos = tabela.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();

        Produto item = tabela.getItems().get(row);
        String data = colNome.getCellObservableValue(item).getValue().toString();

        Data.produtoSelecionado = data;

        new Controller().changeScene("registroProduto.fxml", "WoodPecker Furniture - Dados de Produto", (Stage) alterarButton.getScene().getWindow());
    }


    @FXML
    protected void onVoltarButtonClick() {
        new Controller().changeScene("menu01.fxml", "WoodPecker Furniture - Estoque",(Stage) voltarButton.getScene().getWindow());
    }
}
