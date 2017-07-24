package com.example.shivani.formbuilder.database;

import java.util.ArrayList;

/**
 * Created by shivani on 14/7/17.
 */

public class FormMaster {

    int id;
    String name;
    ArrayList<FormAttributes> formMaster;

    public FormMaster() {

    }

    public FormMaster(int i, String n, ArrayList<FormAttributes> attributes) {
        id = i;
        name = n;
        formMaster = attributes;
    }

    //setter functions
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setformMaster(ArrayList<FormAttributes> formMaster) {
        this.formMaster = formMaster;
    }

    //getter functions
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<FormAttributes> getformMaster() {
        return formMaster;
    }
}

