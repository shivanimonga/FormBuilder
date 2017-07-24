package com.example.shivani.formbuilder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.shivani.formbuilder.adapter.FormMasterAdapter;
import com.example.shivani.formbuilder.database.FormMaster;
import com.example.shivani.formbuilder.database.FormMasterDB;

import java.util.ArrayList;

public class ViewFormMasters extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("in on create", "view button");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_formmasters);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        ArrayList<FormMaster> formMasterArrayList =  new FormMasterDB(this).getAllForms();

        if (formMasterArrayList.isEmpty()) {
            Toast.makeText(ViewFormMasters.this, "No forms added", Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
            FormMasterAdapter adapter = new FormMasterAdapter(this, formMasterArrayList,true);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}

