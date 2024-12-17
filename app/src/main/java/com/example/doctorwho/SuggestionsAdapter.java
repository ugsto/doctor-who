package com.example.doctorwho;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SuggestionsAdapter extends RecyclerView.Adapter<SuggestionsAdapter.ViewHolder> {

    private List<String> suggestions; // Lista de sintomas sugeridos
    private OnSymptomClickListener listener; // Listener para cliques

    // Interface para tratar cliques nos itens
    public interface OnSymptomClickListener {
        void onSymptomClick(String symptom);
    }

    // Construtor
    public SuggestionsAdapter(List<String> suggestions, OnSymptomClickListener listener) {
        this.suggestions = suggestions;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar o layout do item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Configurar os dados
        String symptom = suggestions.get(position);
        holder.textView.setText(symptom);

        // Adicionar evento de clique para adicionar o sintoma
        holder.itemView.setOnClickListener(v -> listener.onSymptomClick(symptom));
    }

    @Override
    public int getItemCount() {
        return suggestions.size();
    }

    // ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}
