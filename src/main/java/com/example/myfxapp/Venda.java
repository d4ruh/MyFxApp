package com.example.myfxapp;

import java.util.Date;

public class Venda {
    private String nomeProduto;
    private String cpfCliente;
    private String cpfVendedor;
    private Double valorProduto;
    private Integer quantidade;
    private Double valorTotal;
    private Date dataVenda;

    public Venda(String nomeProduto, String cpfCliente, String cpfVendedor, Double valorProduto, Integer quantidade, Double valorTotal, Date dataVenda) {
        this.nomeProduto = nomeProduto;
        this.cpfCliente = cpfCliente;
        this.cpfVendedor = cpfVendedor;
        this.valorProduto = valorProduto;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
        this.dataVenda = dataVenda;
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

    public Date getDataVenda() {
        return dataVenda;
    }
}
