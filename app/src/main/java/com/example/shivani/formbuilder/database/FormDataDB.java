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
import static com.example.shivani.formbuilder.database.FormMasterDB.COL_ATTRIBUTE_FORMID;
import static com.example.shivani.formbuilder.database.FormMasterDB.DATABASE_NAME;
import static com.example.shivani.formbuilder.database.FormMasterDB.DATABASE_VERSION;
import static com.example.shivani.formbuilder.database.FormMasterDB.TABLE_FORM_ATTRIBUTES;

/**
 * Created by shivani on 17/7/17.
 */

public class FormDataDB extends SQLiteOpenHelper {


    private static final String TABLE_FORM_DATA = "data_table";
    private static final String COL_DATA_ID = "dataID";
    private static final String COL_FORM_ID = "formID";
    private static final String COL_ATTR_ID = "attributeID";
    private static final String COL_FORM_VALUE = "value";
    private Context context;
    private static final String CREATE_TABLE_FORM_DATA = "CREATE TABLE IF NOT EXISTS "
            + TABLE_FORM_DATA + "("
            + COL_DATA_ID + " INTEGER ,"
            + COL_FORM_ID + " INTEGER ,"
            + COL_ATTR_ID + " INTEGER ,"
            + COL_FORM_VALUE + " TEXT)";

    private static final String SQL_DELETE_DATA =
            "DROP TABLE IF EXISTS " + TABLE_FORM_DATA;

    public FormDataDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        //  db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_FORM_DATA);
    }

    public boolean addData(ArrayList<FormData> formDataArrayList) {
        SharedPreferences pref = context.getSharedPreferences("counters", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        int dataId = pref.getInt("data_id", 0);
        editor.putInt("data_id", ++dataId);
        editor.apply();

        boolean flag = true;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            for (FormData data : formDataArrayList) {
                //data.setDataId(dataId);
                values.put(COL_DATA_ID, dataId);
                values.put(COL_FORM_ID, data.getFormId());
                values.put(COL_ATTR_ID, data.getAtrributeID());
                values.put(COL_FORM_VALUE, data.getValue());
                Log.d("data id", String.valueOf(dataId));
                Log.d("form Id", String.valueOf(data.getFormId()));
                Log.d("atrr id in formDB", String.valueOf(data.getAtrributeID()));
                Log.d("value in formDB", String.valueOf(data.getValue()));
                long rowid = db.insertOrThrow(TABLE_FORM_DATA, null, values);
                Log.d("inserted data row", String.valueOf(rowid));
            }
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public ArrayList<FormData> getFirstValue(ArrayList<FormAttributes> formAtrributesList) {

        SQLiteDatabase db = null;
        Cursor cursor = null;
        ArrayList<FormData> formDataList = new ArrayList<>();
        String query = "SELECT " + COL_DATA_ID + "," + COL_FORM_VALUE + " FROM " + TABLE_FORM_DATA + " WHERE " + COL_ATTR_ID + "="
                + formAtrributesList.get(0).getAttributeId();
        try {
            db = this.getReadableDatabase();
            cursor = db.rawQuery(query, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    FormData formData = new FormData();
                    formData.setDataId(cursor.getInt(cursor.getColumnIndex(COL_DATA_ID)));
                    formData.setValue(cursor.getString(cursor.getColumnIndex(COL_FORM_VALUE)));
                    formDataList.add(formData);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
        return formDataList;
    }

    public ArrayList<FormData> getAllData(int DataId) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        ArrayList<FormData> formDataList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_FORM_DATA + " WHERE " + COL_DATA_ID + "=" + DataId;

        try{
            db = this.getReadableDatabase();
            cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                FormData formData = new FormData();
                formData.setDataId(cursor.getInt(cursor.getColumnIndex(COL_DATA_ID)));
                formData.setFormId(cursor.getInt(cursor.getColumnIndex(COL_FORM_ID)));
                formData.setAtrributeID(cursor.getInt(cursor.getColumnIndex(COL_ATTR_ID)));
                formData.setValue(cursor.getString(cursor.getColumnIndex(COL_FORM_VALUE)));
                formDataList.add(formData);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
        return formDataList;
    }

    public boolean updateData(ArrayList<FormData> formDataArrayList, int dataId) {
        boolean flag = true;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            for (int i = 0; i < formDataArrayList.size(); i++) {
                FormData data = formDataArrayList.get(i);
                values.put(COL_DATA_ID, data.getDataId());
                values.put(COL_FORM_ID, data.getFormId());
                values.put(COL_ATTR_ID, data.getAtrributeID());
                values.put(COL_FORM_VALUE, data.getValue());
                Log.d("atrr id in formDB", String.valueOf(data.getAtrributeID()));
                db.update(TABLE_FORM_DATA, values, COL_ATTR_ID + "=" + data.getAtrributeID()
                        + " AND " + COL_DATA_ID + "=" + dataId, null);
                //  Log.d("inserted data row", String.valueOf(rowid));
            }
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public boolean deleteData(int formId){
        SQLiteDatabase db=null;
        String deleteQuery="DELETE FROM "+TABLE_FORM_DATA+" WHERE "+COL_FORM_ID +"="+formId;
        try{
            db=this.getWritableDatabase();
            db.execSQL(deleteQuery);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            if(db!=null)
                db.close();
        }
        return true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_DATA);
        onCreate(sqLiteDatabase);
    }
}
