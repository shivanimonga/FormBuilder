package com.example.shivani.formbuilder.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shivani.formbuilder.R;
import com.example.shivani.formbuilder.AddFormActivity;
import com.example.shivani.formbuilder.database.FormAttributeDB;
import com.example.shivani.formbuilder.database.FormData;
import com.example.shivani.formbuilder.database.FormMaster;
import com.example.shivani.formbuilder.database.FormMasterDB;
import com.example.shivani.formbuilder.DisplayForm;
import com.example.shivani.formbuilder.ViewFormsList;

import java.util.ArrayList;

/**
 * Created by shivani on 16/7/17.
 */

public class FormMasterAdapter extends RecyclerView.Adapter<FormMasterAdapter.ViewHolder> {
    private ArrayList<? extends Object> formArrayList;
    private Context context;
    boolean isFormMaster;

    public FormMasterAdapter(Context context, ArrayList<? extends Object> list,boolean isFormMaster) {
        formArrayList=list;
        this.context = context;
        this.isFormMaster=isFormMaster;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public TextView formName;
        public Object formObject;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            formName = itemView.findViewById(R.id.formName);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }
        public void setUser(Object formObject){
            this.formObject=formObject;
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Log.d("view position", String.valueOf(position));
            if(isFormMaster){
            final int formID = ((FormMaster)formObject).getId();
            //Log.d("form attr array size", String.valueOf(formAttributesArrayList.size()));
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Select option to add or view form ");
            builder.setNegativeButton("View",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            Intent intent = new Intent(context, ViewFormsList.class);
                            intent.putExtra("formId", formID);
                            context.startActivity(intent);
                            Toast.makeText(context, "view is clicked", Toast.LENGTH_LONG).show();

                        }
                    });
            builder.setPositiveButton("Add",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(context, "add is clicked", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(context, AddFormActivity.class);
                            intent.putExtra("formId", formID);
                            context.startActivity(intent);
                        }
                    });
            builder.show();
        }
        else {
                Intent intent = new Intent(context, DisplayForm.class);
                intent.putExtra("form data id", ((FormData)formObject).getDataId());
                context.startActivity(intent);
            }
        }

        @Override
        public boolean onLongClick(View view) {
            final int position = getAdapterPosition();
            Log.d("view position", String.valueOf(position));
            if (isFormMaster) {
                final int formID = ((FormMaster) formObject).getId();
                //Log.d("form attr array size", String.valueOf(formAttributesArrayList.size()));
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to delete form ");
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Toast.makeText(context, "no is clicked", Toast.LENGTH_LONG).show();

                            }
                        });
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, "yes is clicked", Toast.LENGTH_LONG).show();
                                FormMasterDB formMasterDB=new FormMasterDB(context);
                                formMasterDB.deleteForm(formID);

                                FormAttributeDB formAttributeDB=new FormAttributeDB(context);
                                formAttributeDB.deleteFormAttributes(formID);
                                formArrayList.remove(position);
                                notifyDataSetChanged();

                            }
                        });
                builder.show();
            }
                return false;
        }
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return context;
    }

    @Override
    public FormMasterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View formView = inflater.inflate(R.layout.form_list_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(formView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FormMasterAdapter.ViewHolder holder, int position) {
        if(isFormMaster){
            FormMaster formMaster = (FormMaster) formArrayList.get(position);
            TextView textView2 = holder.formName;
            holder.setUser(formMaster);
            textView2.setText(formMaster.getName());
        }
       else{

            FormData form = (FormData) formArrayList.get(position);
            // Set item views based on your views and data model
            holder.setUser(form);
            TextView textView2 = holder.formName;
            textView2.setText(form.getValue());
        }
        // Set item views based on your views and data model
    }

    @Override
    public int getItemCount() {
        return formArrayList.size();
    }
}
