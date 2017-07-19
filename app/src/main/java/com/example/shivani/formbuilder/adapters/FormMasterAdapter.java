package com.example.shivani.formbuilder.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shivani.formbuilder.database.FormMaster;
import com.example.shivani.formbuilder.R;
import com.example.shivani.formbuilder.viewFormMasters;

import java.util.ArrayList;

/**
 * Created by shivani on 16/7/17.
 */

public class FormMasterAdapter extends RecyclerView.Adapter<FormMasterAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView formName;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            formName =itemView.findViewById(R.id.formName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Log.d("view position", String.valueOf(position));
            if (clickListener != null) {
                clickListener.onClick(view, formMasters,position);
            }
        }
    }

    private ArrayList<FormMaster> formMasters;
    private Context context;
    FormClickListener clickListener;


    public FormMasterAdapter(Context context, ArrayList<FormMaster> list) {
        formMasters=list;
        this.context = context;
        this.clickListener=(viewFormMasters) context;
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

        FormMaster form = formMasters.get(position);
        // Set item views based on your views and data model
        TextView textView2 = holder.formName;
        textView2.setText(form.getName());

    }
    @Override
    public int getItemCount() {
        return formMasters.size();
    }
}
