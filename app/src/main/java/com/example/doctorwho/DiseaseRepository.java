package com.example.doctorwho;

import android.content.Context;

import java.util.List;

public class DiseaseRepository {

    private final DatabaseHelper databaseHelper;

    public DiseaseRepository(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public List<Disease> getDiseases() {
        return databaseHelper.getDiseases();
    }
}