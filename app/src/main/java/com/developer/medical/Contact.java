package com.developer.medical;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.medical.Adapter.ContactAdapter;
import com.developer.medical.Database.DatabaseHelper;
import com.developer.medical.Model.Contact_Model;

import java.util.ArrayList;

public class Contact extends AppCompatActivity {

    ArrayList<Contact_Model> arrayList = new ArrayList<>();
    RecyclerView rv_displayContacts;
    String search;
    EditText edt_search_contact;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact);

        rv_displayContacts = findViewById(R.id.rv_displayContacts);
        edt_search_contact = findViewById(R.id.edt_search_contact);

        displayAllContacts();

        edt_search_contact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                search = edt_search_contact.getText().toString();
                if (!search.isEmpty()) {
                    arrayList.clear();
                    arrayList = databaseHelper.searchContactByName(search);
                    ContactAdapter adapter = new ContactAdapter(arrayList, Contact.this, databaseHelper);
                    rv_displayContacts.setLayoutManager(new LinearLayoutManager(Contact.this));
                    rv_displayContacts.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

//    private void displayAllContacts() {
//        arrayList.clear(); // Clear the ArrayList first
//        Cursor cursor = databaseHelper.displayContactData();
//        while (cursor.moveToNext()) {
//            Contact_Model contactModel1 = new Contact_Model(cursor.getString(1), cursor.getString(2));
//            arrayList.add(contactModel1);
//        }
//        ContactAdapter adapter = new ContactAdapter(arrayList, this, databaseHelper);
//        rv_displayContacts.setLayoutManager(new LinearLayoutManager(this));
//        rv_displayContacts.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//    }

    public void displayAllContacts() {
        arrayList.clear(); // Clear the ArrayList first
        Cursor cursor = databaseHelper.displayContactData();
        while (cursor.moveToNext()) {
            Contact_Model contactModel = new Contact_Model(cursor.getString(1), cursor.getString(2));
            arrayList.add(contactModel);
        }
        ContactAdapter adapter = new ContactAdapter(arrayList, this, databaseHelper);
        rv_displayContacts.setLayoutManager(new LinearLayoutManager(this));
        rv_displayContacts.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}