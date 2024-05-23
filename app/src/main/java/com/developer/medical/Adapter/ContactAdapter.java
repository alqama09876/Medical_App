package com.developer.medical.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.medical.Database.DatabaseHelper;
import com.developer.medical.Model.Contact_Model;
import com.developer.medical.R;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    ArrayList<Contact_Model> arrayList = new ArrayList<>();
    Context context;
    DatabaseHelper databaseHelper;

    public ContactAdapter(ArrayList<Contact_Model> arrayList, Context context, DatabaseHelper databaseHelper) {
        this.arrayList = arrayList;
        this.context = context;
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_contact_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact_Model contactModel = arrayList.get(position);
        holder.tv_name.setText("Name: "+contactModel.getName());
        holder.tv_number.setText("Number: "+contactModel.getNumber());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_number;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_number = itemView.findViewById(R.id.tv_number);
        }
    }
}

