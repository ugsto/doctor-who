package com.example.doctorwho;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SelectedAdapter extends RecyclerView.Adapter<SelectedAdapter.ViewHolder> {

    private final List<String> selectedSymptoms;
    private final OnSymptomClickListener listener;


    public interface OnSymptomClickListener {
        void onSymptomClick(String symptom);
    }

    public SelectedAdapter(List<String> selectedSymptoms, OnSymptomClickListener listener) {
        this.selectedSymptoms = selectedSymptoms;
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
        String symptom = selectedSymptoms.get(position);
        holder.textView.setText(symptom);

        holder.itemView.setOnClickListener(v -> listener.onSymptomClick(symptom));
    }

    @Override
    public int getItemCount() {
        return selectedSymptoms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}
