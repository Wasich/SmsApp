package com.example.designer2.smssmsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    Context context;
    private static final String DATABASE_NAME="messages";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "message";
    private static final String SMS_TABLE = "create table "+TABLE_NAME +"(mobile VARCHAR,sms VARCHAR primary key)";













    public DatabaseHelper (Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }


    public  void  insertIntoDB( String mobile,String sms){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put("mobile", mobile);
        values.put("sms", sms);

        db.insert(TABLE_NAME, null, values);
        db.close();

    }


    public List<DBModel>getDataFromDB() {
        List<DBModel> modelList = new ArrayList<DBModel>();
        String query = "select * from "+TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do {
                DBModel model = new DBModel();
               // model.setId(cursor.getInt(0));
                model.setMobile(cursor.getString(0));
                model.setMessage(cursor.getString(1));


                modelList.add(model);
            }while (cursor.moveToNext());
        }





        return modelList;
    }
    public void deleteARow(String  sms, Context context ){
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete(TABLE_NAME, "sms" + " = ?", new String[] {sms});
        db.close();
    }
    public DBModel findMessages(){
        List<DBModel> modelList = new ArrayList<DBModel>();
        String query = "Select * FROM "	+ TABLE_NAME + " WHERE " + "sms" + " = " + "sms";
        SQLiteDatabase db = this.getWritableDatabase();
        DBModel dbModel = null;
        Cursor cursor = db.rawQuery(query,	null);
        if	(cursor.moveToFirst()){

            DBModel model = new DBModel();
            // model.setId(cursor.getInt(0));
            model.setMobile(cursor.getString(0));
            model.setMessage(cursor.getString(1));


            modelList.add(model);
        }
        cursor.close();
        return dbModel;
    }






}
