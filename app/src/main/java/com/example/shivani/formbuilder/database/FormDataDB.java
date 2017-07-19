package com.example.shivani.formbuilder.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.example.shivani.formbuilder.database.FormMasterDB.DATABASE_NAME;
import static com.example.shivani.formbuilder.database.FormMasterDB.DATABASE_VERSION;

/**
 * Created by shivani on 17/7/17.
 */

public class FormDataDB extends SQLiteOpenHelper {


    private static final String TABLE_FORM_DATA = "data_table";
    private static final String COL_DATA_ID="dataID";
    private static final String COL_FORM_ID="formID";
    private static final String COL_ATTR_ID="attributeID";
    private static final String COL_FORM_VALUE="value";
    private Context context;
    private static final String CREATE_TABLE_FORM_DATA = "CREATE TABLE "
            + TABLE_FORM_DATA + "("
            + COL_DATA_ID + " INTEGER ,"
            + COL_FORM_ID + " INTEGER ,"
            + COL_ATTR_ID + " INTEGER ,"
            + COL_FORM_VALUE + " TEXT)";

    private static final String SQL_DELETE_DATA =
            "DROP TABLE IF EXISTS " + TABLE_FORM_DATA;

    public FormDataDB(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
        this.context=context;
      //  db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL(CREATE_TABLE_FORM_DATA);
    }

    public boolean addData(ArrayList<FormData> formDataArrayList){
        SharedPreferences pref =context.getSharedPreferences("counters", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        int dataId=pref.getInt("data_id",0);
            dataId++;
            editor.putInt("data_id",dataId);
            editor.apply();

        boolean flag=true;
        SQLiteDatabase db=this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            for (int i = 0; i < formDataArrayList.size(); i++) {
                FormData data = formDataArrayList.get(i);
                //data.setDataId(dataId);
                values.put(COL_DATA_ID,dataId);
                values.put(COL_FORM_ID,data.getFormId());
                values.put(COL_ATTR_ID, data.getAtrributeID());
                values.put(COL_FORM_VALUE, data.getValue());
                Log.d("data id", String.valueOf(dataId));
                Log.d("form Id", String.valueOf(data.getFormId()));
                Log.d("atrr id in formDB", String.valueOf(data.getAtrributeID()));
                Log.d("value in formDB", String.valueOf(data.getValue()));
                long rowid = db.insertOrThrow(TABLE_FORM_DATA, null, values);
                Log.d("inserted data row", String.valueOf(rowid));
            }
        }catch (SQLiteException e){
            flag=false;
        }
        return flag;
    }
    public ArrayList<FormData> getFirstValue(ArrayList<FormAttributes> formAtrributesList){
        ArrayList<FormData> formDataList = new ArrayList<>();
        String query="SELECT "+COL_DATA_ID+","+COL_FORM_VALUE+" FROM "+TABLE_FORM_DATA+" WHERE "+COL_ATTR_ID+"="
                +formAtrributesList.get(0).getAttributeId();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query,null);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                FormData formData=new FormData();
                formData.setDataId(cursor.getInt(cursor.getColumnIndex(COL_DATA_ID)));
                formData.setValue(cursor.getString(cursor.getColumnIndex(COL_FORM_VALUE)));
                formDataList.add(formData);
            }
        }
        return formDataList;
    }

    public ArrayList<FormData> getAllData(int DataId){
        ArrayList<FormData> formDataList = new ArrayList<>();
        String query="SELECT * FROM "+TABLE_FORM_DATA+" WHERE "+COL_DATA_ID+"="+DataId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query,null);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                FormData formData=new FormData();
                formData.setDataId(cursor.getInt(cursor.getColumnIndex(COL_DATA_ID)));
                formData.setFormId(cursor.getInt(cursor.getColumnIndex(COL_FORM_ID)));
                formData.setAtrributeID(cursor.getInt(cursor.getColumnIndex(COL_ATTR_ID)));
                formData.setValue(cursor.getString(cursor.getColumnIndex(COL_FORM_VALUE)));
                formDataList.add(formData);
            }
        }
        return formDataList;
    }


    public ArrayList<FormData> getFormData(ArrayList<FormAttributes> formAtrributesList) {
        ArrayList<FormData> formDataList = new ArrayList<>();
        ArrayList<FormData> formData = new ArrayList<>();

        String getQuery = "SELECT * FROM " + TABLE_FORM_DATA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(getQuery, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                FormData Data = new FormData();
                Data.setAtrributeID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COL_ATTR_ID))));
                Data.setValue(cursor.getString(cursor.getColumnIndex(COL_FORM_VALUE)));
                formDataList.add(Data);
            }
        }
        for(int i=0;i<formAtrributesList.size();i++){
            if(formAtrributesList.get(i).getAttributeId()==formDataList.get(i).getAtrributeID())
                formData.add(formDataList.get(i));
        }
        return formData;
    }

    public Cursor getAttributeCursor(int startingAttributeID, int lastAtributeID) {
        Log.d("FormDB", "in getAttributeCursor()");
        String countQuery = "SELECT  * FROM " + TABLE_FORM_DATA + " WHERE " + COL_ATTR_ID + " BETWEEN " + startingAttributeID + " AND " + lastAtributeID;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        // SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery(countQuery, null);
        // return count
        return cursor;
    }
    public boolean updateData(ArrayList<FormData> formDataArrayList,int dataId){
        boolean flag=true;
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        try{
        for(int i=0;i<formDataArrayList.size();i++){
            FormData data=formDataArrayList.get(i);
            values.put(COL_DATA_ID,data.getDataId());
            values.put(COL_FORM_ID,data.getFormId());
            values.put(COL_ATTR_ID, data.getAtrributeID());
            values.put(COL_FORM_VALUE, data.getValue());
            Log.d("atrr id in formDB", String.valueOf(data.getAtrributeID()));
            db.update(TABLE_FORM_DATA,values,COL_ATTR_ID + "=" + data.getAtrributeID()
                    +" AND "+ COL_DATA_ID+"="+dataId,null);
          //  Log.d("inserted data row", String.valueOf(rowid));
        }}catch (SQLiteException e){
            flag=false;
        }
        return flag;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_DATA);
        onCreate(sqLiteDatabase);
    }
}
