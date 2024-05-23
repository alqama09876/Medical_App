package com.developer.medical.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.developer.medical.Model.Contact_Model;
import com.developer.medical.Model.Disease_Model;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "MEDICALINFO";
    private static final int DB_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDiseaseTable = "CREATE TABLE DISEASE(id INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Description TEXT, Cause TEXT, Symptoms TEXT)";
        String createContactTable = "CREATE TABLE CONTACT(id INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Number TEXT)";
        db.execSQL(createDiseaseTable);
        db.execSQL(createContactTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS DISEASE");
        db.execSQL("DROP TABLE IF EXISTS CONTACT");
        onCreate(db);
    }
    public long addDiseaseData(String name, String description, String cause, String symptoms) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Name", name);
        cv.put("Description", description);
        cv.put("Cause", cause);
        cv.put("Symptoms", symptoms);
        long id = sqLiteDatabase.insert("DISEASE", null, cv);
        sqLiteDatabase.close();
        return id;
    }

    public boolean addContactData(String name, String number) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Name", name);
        cv.put("Number", number);
        Long l = sqLiteDatabase.insert("CONTACT", null, cv);
        sqLiteDatabase.close();
        return l > 0;
    }

    public Cursor displayDiseaseData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query = "SELECT * FROM DISEASE";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        return cursor;
    }

    public Cursor displayContactData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query = "SELECT * FROM CONTACT";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        return cursor;
    }

    @SuppressLint("Range")
    public ArrayList<Disease_Model> searchDiseaseByName(String name) {
        ArrayList<Disease_Model> diseases = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String selection = "Name LIKE ?";
        String[] selectionArgs = {"%" + name + "%"};

        try {
            Cursor cursor = db.query(
                    "DISEASE",
                    null,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            if (cursor.moveToFirst()) {
                do {
                    Disease_Model disease = new Disease_Model();
                    disease.setId(cursor.getString(0));
                    disease.setName(cursor.getString(1));
                    disease.setDescription(cursor.getString(2));
                    disease.setCause(cursor.getString(3));
                    disease.setSymptoms(cursor.getString(4));
                    diseases.add(disease);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return diseases;
    }

    @SuppressLint("Range")
    public ArrayList<Contact_Model> searchContactByName(String name) {
        ArrayList<Contact_Model> contacts = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String selection = "Name LIKE ?";
        String[] selectionArgs = {"%" + name + "%"};

        try {
            Cursor cursor = db.query(
                    "CONTACT",
                    null,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            if (cursor.moveToFirst()) {
                do {
                    Contact_Model contactModel = new Contact_Model();
                    contactModel.setId(cursor.getString(0));
                    contactModel.setName(cursor.getString(1));
                    contactModel.setNumber(cursor.getString(2));
                    contacts.add(contactModel);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return contacts;
    }

    public Disease_Model getDiseaseById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Disease_Model diseaseModel = null;
        String[] projection = {
                "id",
                "Name",
                "Description",
                "Cause",
                "Symptoms"
        };
        String selection = "id" + " = ?";
        String[] selectionArgs = {id};
        Cursor cursor = db.query(
                "DISEASE",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        try {
            if (cursor.moveToFirst()) {
                String _id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("Description"));
                String cause = cursor.getString(cursor.getColumnIndexOrThrow("Cause"));
                String symptoms = cursor.getString(cursor.getColumnIndexOrThrow("Symptoms"));
                diseaseModel = new Disease_Model(_id, name, description, cause, symptoms);
            }
        } finally {
            cursor.close();
        }
        return diseaseModel;
    }


    public boolean deleteDisease(String id) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String whereClause = "id=?";
            String[] whereArgs = new String[]{id};
            Log.d("Database", "Deleting disease with ID: " + id);
            int rowsDeleted = db.delete("DISEASE", whereClause, whereArgs);
            db.close();
            return rowsDeleted > 0;
        } catch (Exception e) {
            Log.e("Database", "Error deleting disease: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }



    public boolean updateDiseaseDetails(Disease_Model diseaseModel) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", diseaseModel.getName());
        values.put("Description", diseaseModel.getDescription());
        values.put("Cause", diseaseModel.getCause());
        values.put("Symptoms", diseaseModel.getSymptoms());
        String selection = "id = ?";
        String[] selectionArgs = {diseaseModel.getId()};
        int count = db.update("DISEASE", values, selection, selectionArgs);
        db.close();
        return count > 0;
    }
}

