package com.example.myfxapp;

public class Venda {
    private String nomeProduto;
    private String cpfCliente;
    private String cpfVendedor;
    private Double valorProduto;
    private Integer quantidade;
    private Double valorTotal;

    public Venda(String nomeProduto, String cpfCliente, String cpfVendedor, Double valorProduto, Integer quantidade, Double valorTotal) {
        this.nomeProduto = nomeProduto;
        this.cpfCliente = cpfCliente;
        this.cpfVendedor = cpfVendedor;
        this.valorProduto = valorProduto;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public String getCpfVendedor() {
        return cpfVendedor;
    }

    public Double getValorProduto() {
        return valorProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Double getValorTotal() {
        return valorTotal;
    }
}
