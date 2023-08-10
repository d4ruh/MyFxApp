package com.example.myfxapp;

public class Vendedor {
    private int num_vendas;
    private String nome;

    public Vendedor(int num_vendas, String nome, String cpf) {
        this.num_vendas = num_vendas;
        this.nome = nome;
    }

    public int getNum_vendas() {
        return num_vendas;
    }

    public String getNome() {
        return nome;
    }

}
