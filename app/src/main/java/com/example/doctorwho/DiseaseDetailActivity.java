package com.example.doctorwho;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DiseaseDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_detail);

        // Encontrar as Views no layout
        TextView nameTextView = findViewById(R.id.disease_name);
        TextView descriptionTextView = findViewById(R.id.disease_description);
        TextView symptomsTextView = findViewById(R.id.disease_symptoms);

        // Receber os dados passados pela Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("name");
            String description = extras.getString("description");
            String symptoms = extras.getString("symptoms");

            // Configurar os valores nas Views
            nameTextView.setText(name);
            descriptionTextView.setText(description);
            symptomsTextView.setText("Sintomas: " + symptoms);
        }
    }
}
