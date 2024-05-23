package com.developer.medical;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.developer.medical.Database.DatabaseHelper;


public class AddContacts extends AppCompatActivity {

    ImageView btn_back;
    EditText edt_name, edt_number;
    boolean isAdded;
    AppCompatButton btn_addContact;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_contacts);

        btn_back = findViewById(R.id.btn_back);
        edt_name = findViewById(R.id.edt_name);
        edt_number = findViewById(R.id.edt_number);
        btn_addContact = findViewById(R.id.btn_addContact);

        btn_addContact.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                String name = edt_name.getText().toString().trim();
                String number = edt_number.getText().toString().trim();

                if (name.isEmpty() || number.isEmpty()) {
                    edt_name.setError("*Required");
                    edt_number.setError("*Required");
                } else {
                    isAdded = databaseHelper.addContactData(name, number);
                    if (isAdded) {
                        edt_name.setText("");
                        edt_number.setText("");
                        Toast.makeText(AddContacts.this, "Contact Added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Contact.class));
                    } else {
                        Toast.makeText(AddContacts.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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