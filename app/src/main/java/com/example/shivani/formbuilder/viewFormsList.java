package com.example.shivani.formbuilder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.shivani.formbuilder.adapters.FormDataClickListener;
import com.example.shivani.formbuilder.adapters.FormListAdapter;
import com.example.shivani.formbuilder.adapters.FormMasterAdapter;
import com.example.shivani.formbuilder.database.FormAttributeDB;
import com.example.shivani.formbuilder.database.FormAttributes;
import com.example.shivani.formbuilder.database.FormData;
import com.example.shivani.formbuilder.database.FormDataDB;
import com.example.shivani.formbuilder.database.FormMaster;

import java.util.ArrayList;

public class viewFormsList extends AppCompatActivity implements FormDataClickListener {
    FormDataDB formDataDB = new FormDataDB(this);
    FormAttributeDB formAttributeDB = new FormAttributeDB(this);
    FormListAdapter adapter=new FormListAdapter();
    int formId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_forms_list);
        Intent intent = getIntent();
        formId = intent.getIntExtra("formId", 0);

    }

    @Override
    public void onClick(View view, ArrayList<FormData> formData, int position) {
        Log.d("data id of form", String.valueOf(formData.get(position).getDataId()));
        int dataId=formData.get(position).getDataId();
      //  ArrayList<FormData> formDataArrayList=formDataDB.getAllData(formData.get(position).getDataId());
        Intent intent=new Intent(viewFormsList.this,viewFormActivity.class);
        intent.putExtra("form data id",dataId);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.formsListRecyclerView);
        ArrayList<FormAttributes> formAttributesArrayList = formAttributeDB.getFormAttributes(formId);
        ArrayList<FormData> formDataArrayList = formDataDB.getFirstValue(formAttributesArrayList);
        if (formDataArrayList.size() == 0) {
            Toast.makeText(viewFormsList.this, "No forms added", Toast.LENGTH_SHORT).show();
            finish();
        }

        adapter = new FormListAdapter(this,formDataArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
      //  adapter.notifyDataSetChanged();
    }
}
