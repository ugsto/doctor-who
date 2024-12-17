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
import java.util.Arrays;
import java.util.List;

public class SearchFragment extends Fragment {

    private RecyclerView suggestionsRecycler, selectedRecycler, diseasesRecycler;
    private SuggestionsAdapter suggestionsAdapter;
    private SelectedAdapter selectedAdapter;
    private DiseasesAdapter diseasesAdapter;

    private List<String> allSymptoms;      // Lista completa de sintomas
    private List<String> filteredSymptoms; // Lista de sintomas filtrados
    private List<String> selectedSymptoms; // Lista de sintomas selecionados
    private List<Disease> associatedDiseases; // Lista de doenças associadas

    private android.widget.EditText searchInput;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Inicializa componentes da UI
        searchInput = view.findViewById(R.id.search_input);
        suggestionsRecycler = view.findViewById(R.id.suggestions_recycler);
        selectedRecycler = view.findViewById(R.id.selected_recycler);
        diseasesRecycler = view.findViewById(R.id.diseases_recycler);

        // Inicializa listas
        allSymptoms = Arrays.asList("Febre", "Tosse", "Cansaço", "Dor de cabeça", "Coriza", "Náusea");
        filteredSymptoms = new ArrayList<>();
        selectedSymptoms = new ArrayList<>();
        associatedDiseases = new ArrayList<>();

        // Configura os Adapters
        suggestionsAdapter = new SuggestionsAdapter(filteredSymptoms, this::addSelectedSymptom);
        selectedAdapter = new SelectedAdapter(selectedSymptoms, this::removeSelectedSymptom);
        diseasesAdapter = new DiseasesAdapter(associatedDiseases, this::openDiseaseDetail);

        // Configura os RecyclerViews
        suggestionsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        suggestionsRecycler.setAdapter(suggestionsAdapter);

        selectedRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        selectedRecycler.setAdapter(selectedAdapter);

        diseasesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        diseasesRecycler.setAdapter(diseasesAdapter);

        // Listener para o campo de busca
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterSymptoms(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        return view;
    }

    // Filtrar os sintomas conforme o texto digitado
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

    // Adicionar um sintoma selecionado
    private void addSelectedSymptom(String symptom) {
        if (!selectedSymptoms.contains(symptom)) {
            selectedSymptoms.add(symptom);
            selectedAdapter.notifyDataSetChanged();
            updateAssociatedDiseases();
        }
        searchInput.setText(""); // Limpa o campo de busca
        filteredSymptoms.clear();
        suggestionsAdapter.notifyDataSetChanged();
    }

    // Remover um sintoma selecionado
    private void removeSelectedSymptom(String symptom) {
        selectedSymptoms.remove(symptom);
        selectedAdapter.notifyDataSetChanged();
        updateAssociatedDiseases();
    }

    // Atualizar as doenças associadas com base nos sintomas selecionados
    private void updateAssociatedDiseases() {
        associatedDiseases.clear();
        for (Disease disease : MockData.getMockDiseases()) {
            for (String symptom : selectedSymptoms) {
                if (disease.getSintomas().contains(symptom) && !associatedDiseases.contains(disease)) {
                    associatedDiseases.add(disease);
                }
            }
        }
        diseasesAdapter.notifyDataSetChanged();
    }

    // Abrir detalhes da doença em uma nova Activity
    private void openDiseaseDetail(Disease disease) {
        Intent intent = new Intent(getActivity(), DiseaseDetailActivity.class);
        intent.putExtra("name", disease.getNome());
        intent.putExtra("description", disease.getDescricao());
        intent.putExtra("symptoms", String.join(", ", disease.getSintomas()));
        startActivity(intent);
    }
}
