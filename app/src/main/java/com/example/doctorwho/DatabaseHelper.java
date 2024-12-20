package com.example.doctorwho;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "diseases.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_DISEASES = "diseases";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_SYMPTOMS = "symptoms";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_DISEASES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_SYMPTOMS + " TEXT)";
        db.execSQL(createTable);

        preloadData(db);
    }

    private void preloadData(SQLiteDatabase db) {
        String insertQuery = "INSERT INTO " + TABLE_DISEASES + " (" +
                COLUMN_NAME + ", " + COLUMN_DESCRIPTION + ", " + COLUMN_SYMPTOMS + ") VALUES (?, ?, ?)";
        db.execSQL(insertQuery, new String[]{"Gripe", "Infecção viral comum", "Febre,Tosse,Cansaço"});
        db.execSQL(insertQuery, new String[]{"Dengue", "Doença transmitida por mosquito", "Febre,Dor de cabeça"});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISEASES);
        onCreate(db);
    }

    public List<Disease> getDiseases() {
        List<Disease> diseases = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DISEASES, null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                String symptoms = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SYMPTOMS));
                List<String> symptomsList = Arrays.asList(symptoms.split(","));
                diseases.add(new Disease(name, description, symptomsList));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return diseases;
    }
}
