package com.example.doctorwho;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

public class DiseaseDetailActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_detail);

        TextView nameTextView = findViewById(R.id.disease_name);
        TextView descriptionTextView = findViewById(R.id.disease_description);
        ListView symptomsListView = findViewById(R.id.disease_symptoms_list);
        ImageView diseaseImageView = findViewById(R.id.disease_image);
        Button audioButton = findViewById(R.id.audio_button);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("name");
            String description = extras.getString("description");
            String symptoms = extras.getString("symptoms");

            nameTextView.setText(name);
            descriptionTextView.setText(description);

            List<String> symptomsList = Arrays.asList(symptoms.split(", "));
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, symptomsList);
            symptomsListView.setAdapter(adapter);

            if ("Gripe".equals(name)) {
                diseaseImageView.setImageResource(R.drawable.gripe_image);
                mediaPlayer = MediaPlayer.create(this, R.raw.gripe_audio);
            } else if ("Dengue".equals(name)) {
                diseaseImageView.setImageResource(R.drawable.dengue_image);
                mediaPlayer = MediaPlayer.create(this, R.raw.dengue_audio);
            }
        }

        audioButton.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                if (isPlaying) {
                    mediaPlayer.pause();
                    audioButton.setText("Play");
                    isPlaying = false;
                } else {
                    mediaPlayer.start();
                    audioButton.setText("Pause");
                    isPlaying = true;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}