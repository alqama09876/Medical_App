package com.developer.medical;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.medical.Adapter.DiseaseAdapter;
import com.developer.medical.Database.DatabaseHelper;
import com.developer.medical.Model.Disease_Model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {
    public static final int UPDATE_DISEASE_REQUEST = 1;
    private EditText edt_search_disease;
    private ImageView iv_contact, instruction;
    private RecyclerView rv_displayDiseases;
    private FloatingActionButton fab_addDisease;
    private AppCompatButton btn_AddContact;
    private ArrayList<Disease_Model> diseaseModels = new ArrayList<>();
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        databaseHelper = new DatabaseHelper(this);

        iv_contact = findViewById(R.id.iv_contact);
        edt_search_disease = findViewById(R.id.edt_search_disease);
        rv_displayDiseases = findViewById(R.id.rv_displayDiseases);
        fab_addDisease = findViewById(R.id.fab_addDisease);
        btn_AddContact = findViewById(R.id.btn_AddContact);
        instruction = findViewById(R.id.instruction);

        displayAllDiseases();

        edt_search_disease.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String search = charSequence.toString();
                if (!search.isEmpty()) {
                    searchDiseases(search);
                } else {
                    displayAllDiseases();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        btn_AddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, AddContacts.class));
            }
        });

        fab_addDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Add_Disease.class));
            }
        });

        iv_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Contact.class));
            }
        });

        instruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HealthInstruction.class));
            }
        });
    }

    private void displayAllDiseases() {
        diseaseModels.clear();
        Cursor cursor = databaseHelper.displayDiseaseData();
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            String cause = cursor.getString(3);
            String symptoms = cursor.getString(4);

            Disease_Model diseaseModel = new Disease_Model(id, name, description, cause, symptoms);
            diseaseModels.add(diseaseModel);
        }
        setAdapter();
    }

    private void searchDiseases(String query) {
        diseaseModels.clear();
        ArrayList<Disease_Model> searchResults = databaseHelper.searchDiseaseByName(query);
        if (searchResults != null) {
            diseaseModels.addAll(searchResults);
        }
        setAdapter();
    }

    private void setAdapter() {
        DiseaseAdapter adapter = new DiseaseAdapter(diseaseModels, this, databaseHelper);
        rv_displayDiseases.setLayoutManager(new LinearLayoutManager(this));
        rv_displayDiseases.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_DISEASE_REQUEST && resultCode == RESULT_OK) {
            displayAllDiseases();
            Toast.makeText(this, "Disease updated successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
