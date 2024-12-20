package com.example.doctorwho;

import java.util.List;

public class Disease {
    private final String nome;
    private final String description;
    private final List<String> symptoms;

    public Disease(String nome, String description, List<String> symptoms) {
        this.nome = nome;
        this.description = description;
        this.symptoms = symptoms;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getSymptoms() {
        return symptoms;
    }
}
