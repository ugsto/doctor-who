package com.example.doctorwho;

import java.util.Arrays;
import java.util.List;

public class MockData {
    public static List<Disease> getMockDiseases() {
        return Arrays.asList(
                new Disease("Gripe", "Infecção viral comum", Arrays.asList("Febre", "Tosse", "Cansaço")),
                new Disease("Dengue", "Doença transmitida por mosquito", Arrays.asList("Febre", "Dor de cabeça"))
        );
    }
}
