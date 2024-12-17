package com.example.doctorwho;

import java.util.List;

public class Disease {
    private String nome;
    private String descricao;
    private List<String> sintomas;

    // Construtor
    public Disease(String nome, String descricao, List<String> sintomas) {
        this.nome = nome;
        this.descricao = descricao;
        this.sintomas = sintomas;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public List<String> getSintomas() {
        return sintomas;
    }
}
