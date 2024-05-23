package com.developer.medical;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.developer.medical.Database.DatabaseHelper;
import com.developer.medical.Model.Disease_Model;

public class UpdateDisease extends AppCompatActivity {
    public static final int UPDATE_DISEASE_REQUEST = 1;
    private EditText edt_name, edt_description, edt_cause, edt_symptoms;
    private Button btn_UpdateDisease;
    private ImageView btn_back;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_disease);

        databaseHelper = new DatabaseHelper(this);

        edt_name = findViewById(R.id.edt_name);
        edt_description = findViewById(R.id.edt_description);
        edt_cause = findViewById(R.id.edt_cause);
        edt_symptoms = findViewById(R.id.edt_symptoms);
        btn_UpdateDisease = findViewById(R.id.btn_UpdateDisease);
        btn_back = findViewById(R.id.btn_back);

        //...
        Intent intent = getIntent();
        if (intent != null) {
            String id = intent.getStringExtra("id");
            edt_name.setText(intent.getStringExtra("Name"));
            edt_description.setText(intent.getStringExtra("Description"));
            edt_cause.setText(intent.getStringExtra("Cause"));
            edt_symptoms.setText(intent.getStringExtra("Symptoms"));


            btn_UpdateDisease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (id != null) {
                        Log.d("UpdateDisease", "Updating disease with ID: " + id);
                        updateDisease(id);
                    } else {
                        Log.e("UpdateDisease", "ID is null");
                        Toast.makeText(UpdateDisease.this, "Failed to update disease. ID is null", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void updateDisease(String id) {
        String name = edt_name.getText().toString().trim();
        String description = edt_description.getText().toString().trim();
        String cause = edt_cause.getText().toString().trim();
        String symptoms = edt_symptoms.getText().toString().trim();

        if (name.isEmpty() || description.isEmpty() || cause.isEmpty() || symptoms.isEmpty()) {
            Toast.makeText(UpdateDisease.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Disease_Model diseaseModel = new Disease_Model(id, name, description, cause, symptoms);
        boolean success = databaseHelper.updateDiseaseDetails(diseaseModel);
        if (success) {
            Toast.makeText(UpdateDisease.this, "Disease updated successfully", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            startActivity(new Intent(getApplicationContext(), Dashboard.class));
            finish();
        } else {
            Toast.makeText(UpdateDisease.this, "Failed to update disease", Toast.LENGTH_SHORT).show();
        }
    }
}
