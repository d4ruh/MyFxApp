package com.example.myfxapp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class RegistrarVendaController implements Initializable {
    @FXML private TextField nomeProdutoText;
    @FXML private TextField cpfVendedorText;
    @FXML private TextField cpfClienteText;
    @FXML private TextField quantidadeText;

    @FXML private Button cancelarButton;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        nomeProdutoText.setText(Data.produtoSelecionado);
        Data.produtoSelecionado = null;
    }

    @FXML
    protected void onCancelarButtonClick() {
        new Controller().changeScene("consultarEstoque.fxml","Consultar estoque",(Stage) cancelarButton.getScene().getWindow());
    }

    @FXML
    protected void onConfirmarButtonClick() {
        int check = 0;

        if (!checkCPFV(cpfVendedorText.getText(), "vendedor")) {
            System.out.println("O CPF do vendedor não está registrado");
            check++;
        }

        if (!checkCPFV(cpfClienteText.getText(), "cliente")) {
            System.out.println("O CPF do cliente não etá registrado");
            check++;
        }

        if (!checkQuantidade(nomeProdutoText.getText(), Integer.parseInt(quantidadeText.getText()))) {
            System.out.println("A quantidade de produtos inserida é inválida");
            check++;
        }

        if (check > 0)  return;

        System.out.println("Todos os dados estão corretos");

        int quant = Integer.parseInt(quantidadeText.getText());
        String nome = nomeProdutoText.getText();

        DatabaseHandler connect = new DatabaseHandler();
        Connection conDB = connect.getConnection();

        String insertVenda = "insert into registro_venda ( id_produto, id_cliente, id_vendedor, quantidade, valor_total, data ) values ( ?, ?, ?, ?, ?, now() )";
        String checkValor = "select valor from registro_estoque_produtos where nome = ?";

        double valor = 0.0;
        try {
            PreparedStatement selectStatement = conDB.prepareStatement(checkValor);
            selectStatement.setString(1, nome);

            ResultSet rs = selectStatement.executeQuery();
            rs.next();
            valor = rs.getDouble(1);

            PreparedStatement insertStatement = conDB.prepareStatement(insertVenda);
            insertStatement.setString(1, nome);
            insertStatement.setString(2, cpfClienteText.getText());
            insertStatement.setString(3, cpfVendedorText.getText());
            insertStatement.setInt(4, quant);
            insertStatement.setDouble(5, valor*quant);

            insertStatement.executeUpdate();

        }
        catch (Exception e) {
            System.out.print(e.getMessage());
        }

        String updateQuantidade = "update registro_estoque_produtos set quantidade = quantidade - ? where nome = ?";
        try {
            PreparedStatement updateStatement = conDB.prepareStatement(updateQuantidade);
            updateStatement.setInt(1, quant);
            updateStatement.setString(2, nome);

            updateStatement.executeUpdate();

            conDB.close();
        }
        catch (Exception e) {
            System.out.print(e.getMessage());
        }

        updateComissao_Vendas(cpfVendedorText.getText(), valor);

        new Controller().changeScene("consultarEstoque.fxml","Consultar estoque",(Stage) nomeProdutoText.getScene().getWindow());
    }

    private void updateComissao_Vendas(String cpfVendedor, double valor) {
        String updateComissaoStmt = "update registro_vendedor set valor_comissao = valor_comissao + ?, num_vendas = num_vendas + 1 where cpf like ?;";

    DatabaseHandler connect = new DatabaseHandler();
        Connection conDB = connect.getConnection();

        try {
            PreparedStatement stmt = conDB.prepareStatement(updateComissaoStmt);
            stmt.setDouble(1, (valor*Integer.parseInt(quantidadeText.getText())/100.0));
            stmt.setString(2, cpfVendedor);

            stmt.executeUpdate();

            conDB.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean checkCPFV(String cpf, String userType) {
        DatabaseHandler connect = new DatabaseHandler();
        Connection conDB = connect.getConnection();

        String checkData = "select count(1) from registro_" + userType + " where cpf = ?";

        try {
            PreparedStatement stmt = conDB.prepareStatement(checkData);
            stmt.setString(1, cpf);

            ResultSet rs = stmt.executeQuery();

            rs.next();

            if (rs.getInt(1) == 1)
                return true;
        }
        catch (Exception e) {
            System.out.print("Erro Inesperado: ");
            System.out.println(e.getMessage());
        }
        return false;
    }

    private boolean checkQuantidade(String nomeProduto, int quantidade) {
        DatabaseHandler connect = new DatabaseHandler();
        Connection conDB = connect.getConnection();

        String checkData = "select quantidade from registro_estoque_produtos where nome = ?";

        try {
            PreparedStatement stmt = conDB.prepareStatement(checkData);
            stmt.setString(1, nomeProduto);

            ResultSet rs = stmt.executeQuery();

            rs.next();
            if (rs.getInt(1) >= quantidade && quantidade >= 1)
                return  true;
        }
        catch (Exception e) {
            System.out.print("Erro Inesperado: ");
            System.out.println(e.getMessage());
        }

        return false;
    }
}
