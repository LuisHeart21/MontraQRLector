package com.example.montraqrlector.activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, "Userdata.bd",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table Userdetails(lectura TEXT primary key, qrscan TEXT, name TEXT, empresa TEXT, tel1 TEXT, tel2 TEXT, correo1 TEXT, correo2 TEXT, info TEXT, coment TEXT, agrx TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists Userdetails");

    }

    public Boolean insertuserData(String lectura, String qrscan, String name, String empresa, String tel1, String tel2, String correo1, String correo2, String info, String coment, String agrx){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("lectura",lectura);
        contentValues.put("qrscan",qrscan);
        contentValues.put("name",name);
        contentValues.put("empresa",empresa);
        contentValues.put("tel1",tel1);
        contentValues.put("tel2",tel2);
        contentValues.put("correo1",correo1);
        contentValues.put("correo2",correo2);
        contentValues.put("info",info);
        contentValues.put("coment",coment);
        contentValues.put("agrx",agrx);
        long result=db.insert("Userdetails", null,contentValues);
        if(result ==-1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getData()
    {
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor cursor= db.rawQuery("Select * from Userdetails", null);
        return cursor;
    }

    public void deleteALL()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from Userdetails");
        db.close();
    }

}
