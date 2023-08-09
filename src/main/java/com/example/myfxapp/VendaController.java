package com.example.myfxapp;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;

public class VendaController implements Initializable {
    @FXML private Button voltarButton;

    @FXML private TableView<Venda> tabela;
    @FXML private TableColumn<Venda, String> nomeProdutoCol;
    @FXML private TableColumn<Venda, Date> dataCol;
    @FXML private TableColumn<Venda, String> cpfClienteCol;
    @FXML private TableColumn<Venda, String> cpfVendedorCol;
    @FXML private TableColumn<Venda, Double> valorCol;
    @FXML private TableColumn<Venda, Integer> quantCol;
    @FXML private TableColumn<Venda, Double> valorTotalCol;

    @FXML private MenuItem cpfVOption;
    @FXML private MenuItem cpfCOption;
    @FXML private MenuItem nomeOption;
    @FXML private MenuItem classeOption;
    @FXML private MenuItem valorMaxOption;
    @FXML private MenuItem valorMinOption;
    @FXML private MenuItem quantMaxOption;
    @FXML private MenuItem quantMinOption;

    @FXML private TextField pesquisaText;

    private String modoBusca = "";

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        nomeProdutoCol.setCellValueFactory(new PropertyValueFactory<Venda, String>("nomeProduto"));
        dataCol.setCellValueFactory(new PropertyValueFactory<Venda, Date>("dataVenda"));
        cpfClienteCol.setCellValueFactory(new PropertyValueFactory<Venda, String>("cpfCliente"));
        cpfVendedorCol.setCellValueFactory(new PropertyValueFactory<Venda, String>("cpfVendedor"));
        valorCol.setCellValueFactory(new PropertyValueFactory<Venda, Double>("valorProduto"));
        quantCol.setCellValueFactory(new PropertyValueFactory<Venda, Integer>("quantidade"));
        valorTotalCol.setCellValueFactory(new PropertyValueFactory<Venda, Double>("valorTotal"));

        cpfVOption.setOnAction(event -> { modoBusca = "id_vendedor like ?;"; });
        cpfCOption.setOnAction(event -> { modoBusca = "id_cliente like ?;"; });
        nomeOption.setOnAction(event -> { modoBusca = "id_produto like ?;"; });
        valorMaxOption.setOnAction(event -> { modoBusca = "valor_total <= ?;"; });
        valorMinOption.setOnAction(event -> { modoBusca = "valor_total >= ?;"; });
        quantMaxOption.setOnAction(event -> { modoBusca = "quantidade <= ?;"; });
        quantMinOption.setOnAction(event -> { modoBusca = "quantidade >= ?;"; });
        classeOption.setOnAction(event -> { modoBusca = "classe"; });

        DatabaseHandler connect = new DatabaseHandler();
        Connection conDB = connect.getConnection();
        String getData = "select id_produto, id_cliente, id_vendedor, quantidade, valor_total, data from registro_venda;";

        try {
            Statement stmt = conDB.createStatement();
            ResultSet rs = stmt.executeQuery(getData);

            ObservableList<Venda> listaProdutos = tabela.getItems();
            listaProdutos.clear();

            while (rs.next()) {
                Double valorTotal = rs.getDouble(5);
                Integer quant = rs.getInt(4);

                Venda venda = new Venda( rs.getString(1), rs.getString(2),
                        rs.getString(3), (valorTotal/quant), quant, valorTotal, rs.getDate(6));

                listaProdutos.add(venda);
            }

            tabela.setItems(listaProdutos);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    protected void onPesquisarButtonClick() {
        if (modoBusca.isEmpty())   return;

        DatabaseHandler connect = new DatabaseHandler();
        Connection conDB = connect.getConnection();

        String getData = "select id_produto, id_cliente, id_vendedor, quantidade, valor_total, data from registro_venda where " + modoBusca;
        if (modoBusca.equals("classe")) {
            getData = "select id_produto, id_cliente, id_vendedor, registro_venda.quantidade, valor_total, data from registro_estoque_produtos inner join registro_venda on registro_estoque_produtos.nome = registro_venda.id_produto and registro_estoque_produtos.classe like ?;";
        }

        try {
            PreparedStatement stmt = conDB.prepareStatement(getData);
            stmt.setString(1, pesquisaText.getText());
            ResultSet rs = stmt.executeQuery();

            ObservableList<Venda> listaProdutos = tabela.getItems();
            listaProdutos.clear();

            while (rs.next()) {
                Double valorTotal = rs.getDouble(5);
                Integer quant = rs.getInt(4);

                Venda venda = new Venda( rs.getString(1), rs.getString(2),
                        rs.getString(3), (valorTotal/quant), quant, valorTotal, rs.getDate(6));

                listaProdutos.add(venda);
            }

            tabela.setItems(listaProdutos);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    protected void onVoltarButtonClick() {
        new Controller().changeScene("menu01.fxml", "WoodPecker Furniture - Estoque",(Stage) voltarButton.getScene().getWindow());
    }

    @FXML
    protected void onLogoutButtonClick() {
        new Controller().changeScene("login.fxml", "WoodPecker Furniture - login",(Stage) voltarButton.getScene().getWindow());
        Data.userLogedIn = null;
    }
}
