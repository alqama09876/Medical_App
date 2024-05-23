// Disease_Model.java
package com.developer.medical.Model;

public class Disease_Model {
    private String id;
    private String name;
    private String description;
    private String cause;
    private String symptoms;

    public Disease_Model() {
        // Default constructor required for Firebase
    }

    public Disease_Model(String id, String name, String description, String cause, String symptoms) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cause = cause;
        this.symptoms = symptoms;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }
}
