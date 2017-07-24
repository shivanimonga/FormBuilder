package com.example.shivani.formbuilder.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import static com.example.shivani.formbuilder.database.FormMasterDB.COL_ATTRIBUTE_FORMID;
import static com.example.shivani.formbuilder.database.FormMasterDB.COL_ATTRIBUTE_ID;
import static com.example.shivani.formbuilder.database.FormMasterDB.COL_ATTRIBUTE_LABEL;
import static com.example.shivani.formbuilder.database.FormMasterDB.COL_ATTRIBUTE_SEQ;
import static com.example.shivani.formbuilder.database.FormMasterDB.COL_ATTRIBUTE_TYPE;
import static com.example.shivani.formbuilder.database.FormMasterDB.DATABASE_NAME;
import static com.example.shivani.formbuilder.database.FormMasterDB.TABLE_FORM_ATTRIBUTES;

/**
 * Created by shivani on 14/7/17.
 */

public class FormAttributeDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_FORM_ATRRIBUTES = "CREATE TABLE IF NOT EXISTS " +
            TABLE_FORM_ATTRIBUTES + "("
            + COL_ATTRIBUTE_ID + " INTEGER PRIMARY KEY,"
            + COL_ATTRIBUTE_LABEL + " TEXT,"
            + COL_ATTRIBUTE_TYPE + " TEXT,"
            + COL_ATTRIBUTE_SEQ + " TEXT,"
            + COL_ATTRIBUTE_FORMID + " INTEGER)";
    private static final String SQL_DELETE_ATTRIBUTES =
            "DROP TABLE IF EXISTS " + TABLE_FORM_ATTRIBUTES;

    //   private SQLiteDatabase db;

    public FormAttributeDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //    db=this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_FORM_ATRRIBUTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ATTRIBUTES);
        onCreate(sqLiteDatabase);
    }
    public void addFormAttribute(ArrayList<FormAttributes> formAttributesArrayList, int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for (FormAttributes formattribute : formAttributesArrayList) {
            values.put(FormMasterDB.COL_ATTRIBUTE_ID, formattribute.getAttributeId());
            values.put(COL_ATTRIBUTE_LABEL, formattribute.getLabel());
            values.put(COL_ATTRIBUTE_TYPE, formattribute.getType());
            values.put(COL_ATTRIBUTE_SEQ, formattribute.getSequence());
            values.put(COL_ATTRIBUTE_FORMID, id);
            long row_id = db.insert(TABLE_FORM_ATTRIBUTES, null, values);
            Log.d("row id", String.valueOf(row_id));
        }
    }

    public ArrayList<FormAttributes> getFormAttributes(int id) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        ArrayList<FormAttributes> formAttributesArrayList = new ArrayList<>();
        try {
            db = this.getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_FORM_ATTRIBUTES + " WHERE " + COL_ATTRIBUTE_FORMID + "=" + id +" ORDER BY "+ COL_ATTRIBUTE_SEQ;
            cursor = db.rawQuery(query, null);
                while (cursor.moveToNext()) {
                    FormAttributes formAttributes = new FormAttributes();
                    formAttributes.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COL_ATTRIBUTE_ID))));
                    formAttributes.setLabel(cursor.getString(cursor.getColumnIndex(COL_ATTRIBUTE_LABEL)));
                    formAttributes.setType(cursor.getString(cursor.getColumnIndex(COL_ATTRIBUTE_TYPE)));
                    formAttributes.setSequence(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COL_ATTRIBUTE_SEQ))));
                    formAttributesArrayList.add(formAttributes);
                }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }

        return formAttributesArrayList;
    }
    public void deleteFormAttributes(int formId){
        SQLiteDatabase db=null;
        try{
            db=this.getWritableDatabase();
            db.delete(TABLE_FORM_ATTRIBUTES,COL_ATTRIBUTE_FORMID+"=" + formId,null);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(db!=null)
                db.close();}
    }

}
