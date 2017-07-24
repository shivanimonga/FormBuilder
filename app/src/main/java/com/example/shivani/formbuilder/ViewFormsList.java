package com.example.shivani.formbuilder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.shivani.formbuilder.adapter.FormMasterAdapter;
import com.example.shivani.formbuilder.database.FormAttributeDB;
import com.example.shivani.formbuilder.database.FormAttributes;
import com.example.shivani.formbuilder.database.FormData;
import com.example.shivani.formbuilder.database.FormDataDB;

import java.util.ArrayList;

public class ViewFormsList extends AppCompatActivity  {
    FormDataDB formDataDB = new FormDataDB(this);
    FormAttributeDB formAttributeDB = new FormAttributeDB(this);
    FormMasterAdapter adapter;
    int formId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_forms_list);
        Intent intent = getIntent();
        formId = intent.getIntExtra("formId", 0);

    }


    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.formsListRecyclerView);
        ArrayList<FormAttributes> formAttributesArrayList = formAttributeDB.getFormAttributes(formId);
        ArrayList<FormData> formDataArrayList = formDataDB.getFirstValue(formAttributesArrayList);
        if (formDataArrayList.size() == 0) {
            Toast.makeText(ViewFormsList.this, "No forms added", Toast.LENGTH_SHORT).show();
            finish();
        }
            else {
            adapter = new FormMasterAdapter(this, formDataArrayList,false);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        //  adapter.notifyDataSetChanged();
    }
}
