package com.developer.medical;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.developer.medical.Database.DatabaseHelper;


public class Add_Disease extends AppCompatActivity {

    ImageView btn_back;
    long isAdded;
    String diseaseName, diseaseDescription, diseaseCause, diseaseSymptoms;
    EditText edt_name, edt_description, edt_cause, edt_symptoms;
    DatabaseHelper databaseHelper = new DatabaseHelper(Add_Disease.this);
    Button btn_addDisease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_disease);

        btn_back = findViewById(R.id.btn_back);
        edt_name = findViewById(R.id.edt_name);
        edt_description = findViewById(R.id.edt_description);
        edt_cause = findViewById(R.id.edt_cause);
        edt_symptoms = findViewById(R.id.edt_symptoms);
        btn_addDisease = findViewById(R.id.btn_addDisease);

        btn_addDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                diseaseName = edt_name.getText().toString();
                diseaseDescription = edt_description.getText().toString();
                diseaseCause = edt_cause.getText().toString();
                diseaseSymptoms = edt_symptoms.getText().toString();
                if (diseaseName.isEmpty() || diseaseDescription.isEmpty() || diseaseCause.isEmpty() || diseaseSymptoms.isEmpty()) {
                    edt_name.setError("*Required");
                    edt_description.setError("*Required");
                    edt_cause.setError("*Required");
                    edt_symptoms.setError("*Required");
                } else {
                    isAdded = databaseHelper.addDiseaseData(diseaseName, diseaseDescription, diseaseCause, diseaseSymptoms);
                    if (isAdded>0) {
                        edt_name.setText("");
                        edt_description.setText("");
                        edt_cause.setText("");
                        edt_symptoms.setText("");
                        Toast.makeText(Add_Disease.this, "Disease Added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Dashboard.class));
                    } else {
                        Toast.makeText(Add_Disease.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}