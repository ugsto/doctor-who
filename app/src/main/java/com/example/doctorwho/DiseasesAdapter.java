package com.example.doctorwho;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DiseasesAdapter extends RecyclerView.Adapter<DiseasesAdapter.ViewHolder> {

    private List<Disease> diseases; // Lista de doenças associadas
    private OnDiseaseClickListener listener; // Listener para cliques nos itens

    // Interface para tratar cliques nos itens
    public interface OnDiseaseClickListener {
        void onDiseaseClick(Disease disease);
    }

    // Construtor do Adapter
    public DiseasesAdapter(List<Disease> diseases, OnDiseaseClickListener listener) {
        this.diseases = diseases;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar o layout do item da lista
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Configurar os dados no item da lista
        Disease disease = diseases.get(position);
        holder.textView.setText(disease.getNome());

        // Adicionar clique no item
        holder.itemView.setOnClickListener(v -> listener.onDiseaseClick(disease));
    }

    @Override
    public int getItemCount() {
        return diseases.size();
    }

    // Classe ViewHolder para armazenar a referência das Views
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}
