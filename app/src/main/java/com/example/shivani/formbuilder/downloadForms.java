package com.example.shivani.formbuilder;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.shivani.formbuilder.database.FormAttributeDB;
import com.example.shivani.formbuilder.database.FormAttributes;
import com.example.shivani.formbuilder.database.FormMaster;
import com.example.shivani.formbuilder.database.FormMasterDB;
import com.example.shivani.formbuilder.network.HttpHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

/**
 * Created by shivani on 13/7/17.
 */

public class downloadForms extends AsyncTask<String, Void, String> {

    FormMaster formMaster;
    Context MainActivityContext;
    private ProgressDialog dialog;
    String stats=null;

    downloadForms(Context context) {
        MainActivityContext = context;
        dialog = new ProgressDialog(MainActivityContext);
    }

    protected void onPreExecute() {

        dialog.setCancelable(false);
        dialog.setMessage("Fetching data,please wait!");
        dialog.show();

    }

    @Override
    protected String doInBackground(String... strings) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        String urlString = strings[0];
        Log.d("url string", urlString);
        HttpHandler httpHandler = new HttpHandler();
        String jsonString = httpHandler.makeServiceCall(urlString);

//        Log.d("json string", jsonString);

     // String jsonString="{ \"id\": 1, \"name\":\"Home\", \"formMaster\": [ { \"id\": 1, \"label\": \"Firstname\", \"type\": \"string\", \"sequence\": 1 }, { \"id\": 2, \"label\": \"Lastname\", \"type\": \"string\", \"sequence\": 2 }, { \"id\": 3, \"label\": \"Contact\", \"type\": \"number\", \"sequence\": 3 }, { \"id\": 4, \"label\": \"Address\", \"type\": \"text\", \"sequence\":4 } ] }";
        if(jsonString!=null){
            formMaster = gson.fromJson(jsonString, FormMaster.class);
            Log.d("form id", String.valueOf(formMaster.getId()));
            boolean flag=storeForm(formMaster);
            String stats;
            if (flag) {
                stats = "Form created successfully";
            } else
                stats = "Form already present";
            return stats;
        }
        else
            return null;
    }

   boolean storeForm(FormMaster formMaster){
       FormMasterDB formDB=new FormMasterDB(MainActivityContext);
           try {
               formDB.addForm(formMaster);
               ArrayList<FormAttributes> formattributesList = formMaster.getformMaster();
               storeFormAttributes(formattributesList);
               return true;
                }catch(SQLiteConstraintException e){
                    Log.d("in add form","form exists");
               return false;
           }

    }
    public void storeFormAttributes(ArrayList<FormAttributes> formAtrributesList){
        FormAttributeDB formAttributeDB=new FormAttributeDB(MainActivityContext);
        formAttributeDB.addFormAttribute(formAtrributesList, formMaster.getId());

    }
    @Override
    public void onPostExecute(String s) {
        if(s!=null)
            Toast.makeText(MainActivityContext,s,Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MainActivityContext,"Network error",Toast.LENGTH_SHORT).show();

        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
