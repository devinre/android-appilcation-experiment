package com.example.dh.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by dh on 2017/10/16.
 */

public class MyDBOpenHelper extends SQLiteOpenHelper {
    public MyDBOpenHelper(Context context, String name,
                          SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String sqlstr="CREATE TABLE student(stuId INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "name VARCHAR(20), "+
                "score INTEGER, "+
                "img BLOB)";
        db.execSQL(sqlstr);
        Log.i("SQLite",sqlstr);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL("DROP TABLE student");
        db.execSQL("CREATE TABLE sutdent(stuId INTEGER PRIMARY KEY AUTOINCREMENT, "+
        "name VARCHAR(20), "+
        "score INTEGER");
    }
}
