package com.example.doctorwho;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DiseasesAdapter extends RecyclerView.Adapter<DiseasesAdapter.ViewHolder> {

    private final List<Disease> diseases;
    private final OnDiseaseClickListener listener;

    public interface OnDiseaseClickListener {
        void onDiseaseClick(Disease disease);
    }

    public DiseasesAdapter(List<Disease> diseases, OnDiseaseClickListener listener) {
        this.diseases = diseases;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Disease disease = diseases.get(position);
        holder.textView.setText(disease.getNome());

        holder.itemView.setOnClickListener(v -> listener.onDiseaseClick(disease));
    }

    @Override
    public int getItemCount() {
        return diseases.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}
