package com.example.shivani.formbuilder.adapters;

import android.view.View;

import com.example.shivani.formbuilder.database.FormMaster;

import java.util.ArrayList;

/**
 * Created by shivani on 16/7/17.
 */

public interface FormClickListener {
    public void onClick(View view, ArrayList<FormMaster> formMasters,int position);
}
