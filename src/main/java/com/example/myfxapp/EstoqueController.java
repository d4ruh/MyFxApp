package com.example.myfxapp;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
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

        String operacao;
        if (modoBusca == "valor")
            operacao = modoBusca + " <= ?";
        else
            operacao = modoBusca + " like ?";

        String getData = "select nome, classe, quantidade, valor from registro_estoque_produtos where " + operacao;

        try {
            PreparedStatement selectDados = conDB.prepareStatement(getData);

            if (modoBusca == "valor")   selectDados.setDouble(1, Double.parseDouble(pesquisaText.getText()));
            else                        selectDados.setString(1, "%" + pesquisaText.getText() + "%");

            ResultSet rs = selectDados.executeQuery();

            ObservableList<Produto> listaProdutos = tabela.getItems();
            listaProdutos.clear();

            while (rs.next()) {
                Produto produto = new Produto( rs.getString(1), rs.getString(2),
                        rs.getInt(3), +rs.getDouble(4));

                listaProdutos.add(produto);
            }

            tabela.setItems(listaProdutos);

            conDB.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    protected void onEfetivarVendaButtonClick() {
        if (!checkIfSelected(tabela))
            return;

        Data.produtoSelecionado = getProductName();

        new Controller().changeScene("registrarVenda.fxml", "WoodPecker Furniture - Dados de Venda", (Stage) logoutButton.getScene().getWindow());
    }

    protected boolean checkIfSelected(TableView tb) {
        if (tb.getSelectionModel().isEmpty()) {
            System.out.println("item não selecionado");
            return false;
        }

        return true;
    }

    @FXML
    protected void onLogoutButtonClick() {
        new Controller().changeScene("login.fxml", "WoodPecker Furniture - login",(Stage) logoutButton.getScene().getWindow());
        Data.userLogedIn = null;
    }

    private String getProductName() {
        TablePosition pos = tabela.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();

        Produto item = tabela.getItems().get(row);
        return colNome.getCellObservableValue(item).getValue().toString();
    }

    @FXML
    protected void onRemoverButtonClick() {
        if (!checkIfSelected(tabela))
            return;

        String data = getProductName();

        DatabaseHandler connect = new DatabaseHandler();
        Connection conDB = connect.getConnection();

        String deletarProduto = "delete from registro_estoque_produtos where nome = ?";

        try {
            PreparedStatement stmt = conDB.prepareStatement(deletarProduto);
            stmt.setString(1, data);
            stmt.executeUpdate();

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
        if (!checkIfSelected(tabela))
            return;

        Data.produtoSelecionado = getProductName();

        new Controller().changeScene("registroProduto.fxml", "WoodPecker Furniture - Dados de Produto", (Stage) alterarButton.getScene().getWindow());
    }

    @FXML
    protected void onVoltarButtonClick() {
        new Controller().changeScene("menu01.fxml", "WoodPecker Furniture - Menu",(Stage) voltarButton.getScene().getWindow());
    }
}
