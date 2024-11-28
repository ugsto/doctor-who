package com.example.doctorwho;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SymptomsDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "symptoms.db";
    private static final int DATABASE_VERSION = 1;

    public SymptomsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public ArrayList<DiseaseMatch> findDiseasesBySymptoms(ArrayList<String> symptoms) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<DiseaseMatch> results = new ArrayList<>();

        StringBuilder query = new StringBuilder("SELECT prognosis, ");
        for (String symptom : symptoms) {
            query.append(symptom).append(" + ");
        }
        query.setLength(query.length() - 3);
        query.append(" AS match_count FROM symptoms ORDER BY match_count DESC LIMIT 10");

        try (Cursor cursor = db.rawQuery(query.toString(), null)) {
            int prognosisIndex = cursor.getColumnIndex("prognosis");
            int matchCountIndex = cursor.getColumnIndex("match_count");

            if (prognosisIndex == -1 || matchCountIndex == -1) {
                throw new IllegalArgumentException("Missing required columns in query results.");
            }

            if (cursor.moveToFirst()) {
                do {
                    String diseaseName = cursor.getString(prognosisIndex);
                    int matchCount = cursor.getInt(matchCountIndex);
                    results.add(new DiseaseMatch(diseaseName, matchCount));
                } while (cursor.moveToNext());
            }
        }

        return results;
    }
}
