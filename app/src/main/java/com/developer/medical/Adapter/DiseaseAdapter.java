package com.developer.medical.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.medical.Database.DatabaseHelper;
import com.developer.medical.Model.Disease_Model;
import com.developer.medical.R;
import com.developer.medical.UpdateDisease;

import java.util.ArrayList;

public class DiseaseAdapter extends RecyclerView.Adapter<DiseaseAdapter.ViewHolder> {
    private ArrayList<Disease_Model> arrayList;
    private Context context;
    private DatabaseHelper databaseHelper;

    public DiseaseAdapter(ArrayList<Disease_Model> arrayList, Context context, DatabaseHelper databaseHelper) {
        this.arrayList = arrayList;
        this.context = context;
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_disease_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }

        Disease_Model diseaseModel = arrayList.get(position);
        holder.tv_name.setText("Name: "+diseaseModel.getName());
        holder.tv_description.setText("Description: "+diseaseModel.getDescription());
        holder.tv_cause.setText("Cause: "+diseaseModel.getCause());
        holder.tv_symptoms.setText("Symptoms: "+diseaseModel.getSymptoms());

        holder.btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Disease_Model deleteDisease = arrayList.get(holder.getAdapterPosition());
                Log.d("DiseaseAdapter", "Disease: " + deleteDisease.getName());
                boolean isDeleted = databaseHelper.deleteDisease(deleteDisease.getId());
                if (isDeleted) {
                    arrayList.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                    notifyItemRangeChanged(holder.getAdapterPosition(), arrayList.size()); // Add this line
                    Toast.makeText(context, "Disease deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Failed to delete disease "+deleteDisease.getId(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Disease_Model selectedDisease = arrayList.get(holder.getAdapterPosition());
                Intent intent = new Intent(context, UpdateDisease.class);
                intent.putExtra("id", selectedDisease.getId());
                intent.putExtra("Name", selectedDisease.getName());
                intent.putExtra("Description", selectedDisease.getDescription());
                intent.putExtra("Cause", selectedDisease.getCause());
                intent.putExtra("Symptoms", selectedDisease.getSymptoms());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_description, tv_cause, tv_symptoms;
        ImageView btn_Update, btn_Delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_description = itemView.findViewById(R.id.tv_description);
            tv_cause = itemView.findViewById(R.id.tv_cause);
            tv_symptoms = itemView.findViewById(R.id.tv_symptoms);
            btn_Update = itemView.findViewById(R.id.btn_Update);
            btn_Delete = itemView.findViewById(R.id.btn_Delete);
        }
    }
}