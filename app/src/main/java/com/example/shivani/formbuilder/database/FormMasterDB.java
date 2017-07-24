package com.example.shivani.formbuilder.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by shivani on 14/7/17.
 */
public class FormMasterDB extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "FormMaster.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TABLE_FORM_MASTER = "form_table";


    public static final String TABLE_FORM_ATTRIBUTES = "attributes_table";
    public static final String COL_ATTRIBUTE_ID = "id";
    public static final String COL_ATTRIBUTE_LABEL = "label";
    public static final String COL_ATTRIBUTE_TYPE = "type";
    public static final String COL_ATTRIBUTE_SEQ = "sequence";
    public static final String COL_ATTRIBUTE_FORMID = "formId";

    private static final String COL_FORM_ID = "id";
    private static final String COL_FORM_NAME = "name";

    // TABLE CREATE STATEMENTS
    private static final String CREATE_TABLE_FORM_MASTER = "CREATE TABLE IF NOT EXISTS "
            + TABLE_FORM_MASTER + "("
            + COL_FORM_ID + " INTEGER PRIMARY KEY,"
            + COL_FORM_NAME + " TEXT)";


    private static final String SQL_DELETE_FORM =
            "DROP TABLE IF EXISTS " + TABLE_FORM_MASTER;


    public FormMasterDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public String getDatabaseName() {
        return DATABASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_FORM_MASTER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_FORM);
        db.execSQL(CREATE_TABLE_FORM_MASTER);
    }

    public void addForm(FormMaster form) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_FORM_ID, form.getId());
        contentValues.put(COL_FORM_NAME, form.getName());
        long row_id = db.insertOrThrow(TABLE_FORM_MASTER, null, contentValues);
        Log.d("row id", String.valueOf(row_id));

    }

    public ArrayList<FormMaster> getAllForms() {
        String getQuery = "Select * FROM " + TABLE_FORM_MASTER;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        ArrayList<FormMaster> formList = new ArrayList<>();
        try {
            db = this.getReadableDatabase();
            cursor = db.rawQuery(getQuery, null);
            while (cursor.moveToNext()) {

                FormMaster formMaster = new FormMaster();
                formMaster.setId(cursor.getInt(cursor.getColumnIndex(COL_FORM_ID)));
                formMaster.setName(cursor.getString(cursor.getColumnIndex(COL_FORM_NAME)));
                formList.add(formMaster);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();}
        return formList;
    }

    public void deleteForm(int formId){
        SQLiteDatabase db=null;
      //  String deleteQuery="DELETE FROM "+TABLE_FORM_MASTER+" WHERE "+COL_FORM_ID +"="+formId;
        try{
            db=this.getWritableDatabase();
            db.delete(TABLE_FORM_MASTER,COL_FORM_ID+"=" + formId,null);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(db!=null)
                db.close();}
    }
}