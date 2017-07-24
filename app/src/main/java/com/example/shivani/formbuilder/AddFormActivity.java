package com.example.shivani.formbuilder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.shivani.formbuilder.database.FormAttributeDB;
import com.example.shivani.formbuilder.database.FormAttributes;
import com.example.shivani.formbuilder.database.FormDataDB;

import java.util.ArrayList;

public class AddFormActivity extends AppCompatActivity {

    FormAttributeDB formAttributeDB;
    public ScrollView scrollView;
    FormDataDB formdataDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_form);
        Intent intent = getIntent();
        int formId = intent.getIntExtra("formId", 0);
        Log.d("in display form", String.valueOf(formId));
        formdataDB = new FormDataDB(this);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        formAttributeDB = new FormAttributeDB(AddFormActivity.this);
        showForm(formId);
    }

    public void showForm(int formID) {
      //  ArrayList<String> editTextStrings=new ArrayList<>();
        final ArrayList<FormAttributes> formAttributesArrayList = formAttributeDB.getFormAttributes(formID);
        LinearLayout finalLinear = (LinearLayout)findViewById(R.id.finalLinearLayout);
        LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(this.LAYOUT_INFLATER_SERVICE);

        for (FormAttributes attribute : formAttributesArrayList) {
            View view = layoutInflater.inflate(R.layout.form_layout, null);
            LinearLayout formLinearLayout = view.findViewById(R.id.formLinearLayout);
            finalLinear.addView(formLinearLayout);
           TextView textView = (TextView) view.findViewById(R.id.textView);
            Log.d("label in add form", attribute.getLabel());
            textView.setText(attribute.getLabel());
            EditText editText = (EditText) view.findViewById(R.id.editText);
            editText.setVisibility(View.VISIBLE);
            editText.setHint(attribute.getLabel());

            if ((attribute.getType().equalsIgnoreCase("number")))
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            else if (attribute.getType().equalsIgnoreCase("string"))
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
            else if (attribute.getType().equalsIgnoreCase("text"))
                editText.setInputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
            //editText.setTa
            //int i=0;
           // editTextStrings.add(editText.getText().toString());
           //Log.d("edit text value",editTextStrings.get(i)) ;
        formLinearLayout.setOrientation(LinearLayout.VERTICAL);
        }
    }
}


/*

    public void showForm(final int formID) {
        Log.d("in display form", String.valueOf(formID));
        final ArrayList<FormAttributes> formAttributesArrayList = formAttributeDB.getFormAttributes(formID);
        final ArrayList<FormData> formDataArrayList = new ArrayList<>();
        final List<EditText> editTextList = new ArrayList<>();
        for (FormAttributes attribute : formAttributesArrayList) {
            textView = new TextView(this);
            textView.setText(attribute.getLabel());
            linearLayout.addView(textView);
            editText = new EditText(this);
            if ((attribute.getType().equalsIgnoreCase("number")))
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            else if (attribute.getType().equalsIgnoreCase("text"))
                editText.setInputType(InputType.TYPE_CLASS_TEXT);

            editText.setHint("Enter Value ");
            editTextList.add(editText);
            linearLayout.addView(editText);
        }

        Button b = new Button(this);
        b.setText(" SAVE ");
        linearLayout.addView(b);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FormData formData;
                boolean flag = false;
                for (EditText editText : editTextList) {
                    if (editText.getText().length()==0)
                    {
                        flag = true;
                       break;
                    }
                }
                try {
                    if (flag) {
                        Toast.makeText(AddFormActivity.this, "Please enter all values", Toast.LENGTH_SHORT).show();
                    } else {
                        for (int i = 0; i < editTextList.size(); i++) {
                            formData = new FormData();
                            formData.setAtrributeID(formAttributesArrayList.get(i).getAttributeId());
                            formData.setValue(editTextList.get(i).getText().toString());
                            formData.setFormId(formID);
                            formDataArrayList.add(i, formData);
                            Log.d("attr id", String.valueOf(formData.getAtrributeID()));
                            Log.d("edit text", formData.getValue());
                        }

                        if(formdataDB.addData(formDataArrayList)){
                          Toast.makeText(AddFormActivity.this,"Data added succesfully",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddFormActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(AddFormActivity.this,"Data not added",Toast.LENGTH_SHORT).show();
                    }
                } catch (SQLiteConstraintException e) {
                    Toast.makeText(AddFormActivity.this, "Data already present", Toast.LENGTH_SHORT).show();
                }
            }
        });
        this.setContentView(scrollView);
    }
*/

