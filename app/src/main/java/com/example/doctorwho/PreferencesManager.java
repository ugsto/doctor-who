package com.example.doctorwho;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PreferencesManager {

    private static final String PREF_NAME = "doctor_who_prefs";
    private static final String KEY_SELECTED_SYMPTOMS = "selected_symptoms";

    private final SharedPreferences sharedPreferences;

    public PreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveSelectedSymptoms(List<String> symptoms) {
        String symptomsString = String.join(",", symptoms);
        sharedPreferences.edit().putString(KEY_SELECTED_SYMPTOMS, symptomsString).apply();
    }

    public List<String> getSelectedSymptoms() {
        String symptomsString = sharedPreferences.getString(KEY_SELECTED_SYMPTOMS, "");
        if (symptomsString.isEmpty()) {
            return new ArrayList<>();
        } else {
            return new ArrayList<>(Arrays.asList(symptomsString.split(",")));
        }
    }

    public void clearSelectedSymptoms() {
        sharedPreferences.edit().remove(KEY_SELECTED_SYMPTOMS).apply();
    }
}