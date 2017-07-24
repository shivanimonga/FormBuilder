package com.example.shivani.formbuilder.database;

/**
 * Created by shivani on 14/7/17.
 */

public class FormAttributes {

    int id;
    String label;
    String type;
    int sequence;

    public FormAttributes() {
        id = 0;
        label = null;
        type = null;
        sequence = 0;
    }

    public FormAttributes(int id, String label, String type, int sequence) {
        this.id = id;
        this.label = label;
        this.type = type;
        this.sequence = sequence;
    }

    //setter methods
    public void setId(int id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    //getter functions
    public int getAttributeId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getType() {
        return type;
    }

    public int getSequence() {
        return sequence;
    }

}
