package com.example.shivani.formbuilder;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.shivani.formbuilder.adapters.FormClickListener;
import com.example.shivani.formbuilder.adapters.FormMasterAdapter;
import com.example.shivani.formbuilder.database.FormMaster;
import com.example.shivani.formbuilder.database.FormMasterDB;

import java.util.ArrayList;

public class viewFormMasters extends AppCompatActivity implements FormClickListener{
    FormMasterDB formMasterDB=new FormMasterDB(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("in on create","view button");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_formmasters);
        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.my_recycler_view);
        ArrayList<FormMaster> formMasterArrayList=formMasterDB.getAllForms();

        if(formMasterArrayList.size()==0){
            Toast.makeText(viewFormMasters.this,"No forms added",Toast.LENGTH_SHORT).show();
            finish();
        }
        FormMasterAdapter adapter=new FormMasterAdapter(this,formMasterArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View view, ArrayList<FormMaster> formMasters,int position) {
       // Toast.makeText(context, formName.getText(), Toast.LENGTH_SHORT).show();
        final int formID=formMasters.get(position).getId();

        //Log.d("form attr array size", String.valueOf(formAttributesArrayList.size()));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setNegativeButton("View",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Intent intent=new Intent(viewFormMasters.this,viewFormsList.class);
                        intent.putExtra("formId",formID);
                        startActivity(intent);
                      //  Toast.makeText(viewFormMasters.this, "view is clicked", Toast.LENGTH_LONG).show();

                    }
                });
        builder.setPositiveButton("Add",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                     //   Toast.makeText(viewFormMasters.this, "add is clicked", Toast.LENGTH_LONG).show();

                        Intent intent=new Intent(viewFormMasters.this,addFormActivity.class);
                        intent.putExtra("formId",formID);
                        startActivity(intent);
                    }
                });
        builder.show();
    }
    }

