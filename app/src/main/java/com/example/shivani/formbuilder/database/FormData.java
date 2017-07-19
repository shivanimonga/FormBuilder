package com.example.shivani.formbuilder.database;

/**
 * Created by shivani on 17/7/17.
 */

public class FormData {
    int dataId;
    int formId;
    int atrributeID;
    String value;

    public void setFormId(int formId){
        this.formId=formId;
    }

    public void setDataId(int dataId){
        this.dataId=dataId;
    }

    public void setAtrributeID(int id){
        atrributeID=id;
    }

    public void setValue(String value){
        this.value=value;
    }

    //*************************************//

    public int getDataId(){
        return dataId;
    }

    public int getFormId(){
        return formId;
    }

    public int getAtrributeID(){
        return atrributeID;
    }

    public String getValue(){
        return value;
    }
}
