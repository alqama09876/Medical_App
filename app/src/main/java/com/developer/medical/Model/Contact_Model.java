package com.developer.medical.Model;

public class Contact_Model {
    String id, Name, Number;

    public Contact_Model(){}

    public Contact_Model(String name, String number) {
        Name = name;
        Number = number;
    }

    public Contact_Model(String id, String name, String number) {
        this.id = id;
        Name = name;
        Number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }
}
