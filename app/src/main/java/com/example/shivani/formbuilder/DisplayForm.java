package com.example.shivani.formbuilder;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shivani.formbuilder.database.FormAttributeDB;
import com.example.shivani.formbuilder.database.FormAttributes;
import com.example.shivani.formbuilder.database.FormData;
import com.example.shivani.formbuilder.database.FormDataDB;

import java.util.ArrayList;
import java.util.List;

public class DisplayForm extends AppCompatActivity {
    public TextView textView;
    public EditText editText;
    FormAttributeDB formAttributeDB;
    public ScrollView scrollView;
    public LinearLayout linearLayout;
    FormDataDB formdataDB;
    int dataId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_form);
        Log.d("in oncreate", "in viewform");
        Intent intent = getIntent();
        dataId = intent.getIntExtra("form data id", 0);
        Log.d("dataid in viewform", String.valueOf(dataId));
        FormDataDB formDataDB = new FormDataDB(this);
        ArrayList<FormData> formDataArrayList = formDataDB.getAllData(dataId);
        int formId = formDataArrayList.get(0).getFormId();
        formdataDB = new FormDataDB(this);
        scrollView = new ScrollView(this);
        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(linearLayout);
        formAttributeDB = new FormAttributeDB(DisplayForm.this);
        editForm(formId);
    }

    private void editForm(final int formId) {
        final ArrayList<FormAttributes> formAttributesArrayList = formAttributeDB.getFormAttributes(formId);
        final ArrayList<FormData> formDataArrayList = new ArrayList<>();
        final List<EditText> editTextList = new ArrayList<>();
        ArrayList<FormData> formDataList = formdataDB.getAllData(dataId);

        int count = 0;
        for (FormAttributes attribute : formAttributesArrayList) {
            textView = new TextView(this);
            textView.setText(attribute.getLabel());
            linearLayout.addView(textView);
            editText = new EditText(this);
            editText.setText(formDataList.get(count).getValue());
            if ((attribute.getType().equals("number")))
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            else if (attribute.getType().equals("text"))
                editText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            else if (attribute.getType().equals("string"))
                editText.setInputType(InputType.TYPE_CLASS_TEXT);

            count++;

            editText.setHint("Enter Value ");
            editTextList.add(editText);
            linearLayout.addView(editText);
        }

        Button b = new Button(this);
        b.setText(" UPDATE ");
        linearLayout.addView(b);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FormData formData;
                boolean flag = false;
                for (EditText editText : editTextList) {
                    if (editText.getText().length() == 0) {
                        flag = true;
                        Log.d("all data not present", "in add");
                    }
                }
                try {
                    if (flag) {
                        Toast.makeText(DisplayForm.this, "Please enter all values", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("edit text size", String.valueOf(editTextList.size()));
                        for (int i = 0; i < editTextList.size(); i++) {
                            formData = new FormData();
                            formData.setDataId(dataId);
                            formData.setFormId(formId);
                            formData.setAtrributeID(formAttributesArrayList.get(i).getAttributeId());
                            formData.setValue(editTextList.get(i).getText().toString());
                            formDataArrayList.add(i, formData);
                            Log.d("attr id", String.valueOf(formData.getAtrributeID()));
                            Log.d("edit text", formData.getValue());
                        }
                    }

                    if (formdataDB.updateData(formDataArrayList, dataId)) {
                        Toast.makeText(DisplayForm.this, "Data added succesfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DisplayForm.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    } else
                        Toast.makeText(DisplayForm.this, "Data not added", Toast.LENGTH_SHORT).show();
                } catch (SQLiteConstraintException e) {
                    Toast.makeText(DisplayForm.this, "Data already present", Toast.LENGTH_SHORT).show();
                }
            }
        });
        this.setContentView(scrollView);
    }
}
