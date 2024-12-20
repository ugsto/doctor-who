package com.example.doctorwho;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private SuggestionsAdapter suggestionsAdapter;
    private SelectedAdapter selectedAdapter;
    private DiseasesAdapter diseasesAdapter;

    private List<String> allSymptoms;
    private List<String> filteredSymptoms;
    private List<String> selectedSymptoms;
    private List<Disease> associatedDiseases;

    private android.widget.EditText searchInput;

    private DiseaseRepository diseaseRepository;
    private PreferencesManager preferencesManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        diseaseRepository = new DiseaseRepository(requireContext());
        preferencesManager = new PreferencesManager(requireContext());

        searchInput = view.findViewById(R.id.search_input);
        RecyclerView suggestionsRecycler = view.findViewById(R.id.suggestions_recycler);
        RecyclerView selectedRecycler = view.findViewById(R.id.selected_recycler);
        RecyclerView diseasesRecycler = view.findViewById(R.id.diseases_recycler);

        allSymptoms = getAllSymptoms();
        filteredSymptoms = new ArrayList<>();
        selectedSymptoms = preferencesManager.getSelectedSymptoms(); // Load saved symptoms
        associatedDiseases = new ArrayList<>();

        suggestionsAdapter = new SuggestionsAdapter(filteredSymptoms, this::addSelectedSymptom);
        selectedAdapter = new SelectedAdapter(selectedSymptoms, this::removeSelectedSymptom);
        diseasesAdapter = new DiseasesAdapter(associatedDiseases, this::openDiseaseDetail);

        suggestionsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        suggestionsRecycler.setAdapter(suggestionsAdapter);

        selectedRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        selectedRecycler.setAdapter(selectedAdapter);

        diseasesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        diseasesRecycler.setAdapter(diseasesAdapter);

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterSymptoms(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        updateAssociatedDiseases();

        return view;
    }

    private List<String> getAllSymptoms() {
        List<Disease> diseases = diseaseRepository.getDiseases();
        List<String> symptoms = new ArrayList<>();
        for (Disease disease : diseases) {
            for (String symptom : disease.getSymptoms()) {
                if (!symptoms.contains(symptom)) {
                    symptoms.add(symptom);
                }
            }
        }
        return symptoms;
    }

    private void filterSymptoms(String query) {
        filteredSymptoms.clear();
        if (!query.isEmpty()) {
            for (String symptom : allSymptoms) {
                if (symptom.toLowerCase().contains(query.toLowerCase()) && !selectedSymptoms.contains(symptom)) {
                    filteredSymptoms.add(symptom);
                }
            }
        }
        suggestionsAdapter.notifyDataSetChanged();
    }

    private void addSelectedSymptom(String symptom) {
        if (!selectedSymptoms.contains(symptom)) {
            selectedSymptoms.add(symptom);
            preferencesManager.saveSelectedSymptoms(selectedSymptoms); // Save updated symptoms
            selectedAdapter.notifyDataSetChanged();
            updateAssociatedDiseases();
        }
        searchInput.setText("");
        filteredSymptoms.clear();
        suggestionsAdapter.notifyDataSetChanged();
    }

    private void removeSelectedSymptom(String symptom) {
        selectedSymptoms.remove(symptom);
        preferencesManager.saveSelectedSymptoms(selectedSymptoms); // Save updated symptoms
        selectedAdapter.notifyDataSetChanged();
        updateAssociatedDiseases();
    }

    private void updateAssociatedDiseases() {
        associatedDiseases.clear();
        List<Disease> diseases = diseaseRepository.getDiseases();
        for (Disease disease : diseases) {
            for (String symptom : selectedSymptoms) {
                if (disease.getSymptoms().contains(symptom) && !associatedDiseases.contains(disease)) {
                    associatedDiseases.add(disease);
                }
            }
        }
        diseasesAdapter.notifyDataSetChanged();
    }

    private void openDiseaseDetail(Disease disease) {
        Intent intent = new Intent(getActivity(), DiseaseDetailActivity.class);
        intent.putExtra("name", disease.getNome());
        intent.putExtra("description", disease.getDescription());
        intent.putExtra("symptoms", String.join(", ", disease.getSymptoms()));
        startActivity(intent);
    }
}