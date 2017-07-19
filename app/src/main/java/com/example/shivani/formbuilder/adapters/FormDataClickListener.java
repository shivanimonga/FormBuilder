package com.example.shivani.formbuilder.adapters;

import android.view.View;

import com.example.shivani.formbuilder.database.FormData;
import com.example.shivani.formbuilder.database.FormMaster;

import java.util.ArrayList;

/**
 * Created by shivani on 19/7/17.
 */

public interface FormDataClickListener {
    public void onClick(View view, ArrayList<FormData> formMasters, int position);

}
