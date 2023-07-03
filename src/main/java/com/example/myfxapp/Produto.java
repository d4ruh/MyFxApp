package com.example.myfxapp;

public class Produto {
    private String nome;
    private String classe;
    private Integer quantidade;
    private Double valor;

    public Produto(String nome, String classe, int quantidade, double valor) {
        this.nome = nome;
        this.classe = classe;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }

    public String getClasse() {
        return classe;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getNome() {
        return nome;
    }
}
