package com.example.shivani.formbuilder.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shivani.formbuilder.R;
import com.example.shivani.formbuilder.database.FormData;
import com.example.shivani.formbuilder.database.FormMaster;
import com.example.shivani.formbuilder.viewFormsList;

import java.util.ArrayList;

/**
 * Created by shivani on 19/7/17.
 */

public class FormListAdapter extends RecyclerView.Adapter<FormListAdapter.ViewHolder> {

    public FormListAdapter() {

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView formName;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            formName = itemView.findViewById(R.id.formName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Log.d("view position", String.valueOf(position));
            if (clickListener != null) {
                clickListener.onClick(view, formData, position);
            }
        }
    }
    private ArrayList<FormData> formData;
    private Context context;
   FormDataClickListener clickListener;

    public FormListAdapter(Context context, ArrayList<FormData> list) {
        formData=list;
        this.context=context;
        this.clickListener=(viewFormsList) context;
    }

    @Override
    public FormListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View formView = inflater.inflate(R.layout.form_data_list_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder=new ViewHolder(formView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FormListAdapter.ViewHolder holder, int position) {

        FormData form = formData.get(position);
        // Set item views based on your views and data model
        TextView textView2 = holder.formName;
        textView2.setText(form.getValue());

    }
    @Override
    public int getItemCount() {
        return formData.size();
    }
}
