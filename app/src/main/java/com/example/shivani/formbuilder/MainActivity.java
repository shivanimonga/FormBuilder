package com.example.shivani.formbuilder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shivani.formbuilder.database.FormAttributeDB;
import com.example.shivani.formbuilder.database.FormDataDB;
import com.example.shivani.formbuilder.database.FormMasterDB;

import java.net.MalformedURLException;
import java.net.URL;

import static com.example.shivani.formbuilder.database.FormMasterDB.DATABASE_NAME;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FormAttributeDB formAttributeDB;
        FormDataDB formDataDB;
        formAttributeDB=new FormAttributeDB(this);
        formDataDB=new FormDataDB(this);
        SharedPreferences prefs = this.getSharedPreferences("counters", this.MODE_PRIVATE);
        boolean flag = prefs.getBoolean("counter", false);
        if(!flag){
            SQLiteDatabase sqLiteDatabase=openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
            formAttributeDB.onUpgrade(sqLiteDatabase,0,0);
            formDataDB.onUpgrade(sqLiteDatabase,0,0);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("counter", true);
            editor.apply();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void goButton(View view) {
        EditText urlText = (EditText) findViewById(R.id.urlEditText);

        try {
            URL formURL = new URL(urlText.getText().toString());
            Log.d("form url", formURL.toString());
            downloadForms downloadForm = new downloadForms(MainActivity.this);
           if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) // Above Api Level 13
            {
                downloadForm.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,formURL.toString());
            }
            else // Below Api Level 13
            {
                downloadForm.execute(formURL.toString(), null, null);
            }
        } catch (MalformedURLException e) {
            Toast.makeText(this, "Please enter valid url", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void addButton(View view) {
        Log.d("in main activity", "viewform button");
        Intent intent = new Intent(this, viewFormMasters.class);
        startActivity(intent);
    }
}





