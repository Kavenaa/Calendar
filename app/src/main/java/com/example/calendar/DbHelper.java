package com.example.calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.net.ConnectException;
import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="Calendar";
    private static final int DB_VER = 1;
    public static final String DB_TABLE ="ToDo";
    public static final String DB_COLUM = "ToDoName";



    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL);", DB_TABLE, DB_COLUM);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = String.format("DELETE TABLE IF EXISTS %s", DB_TABLE);
        db.execSQL(query);
        onCreate(db);

    }
    public void insertNewTask(String task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COLUM, task);
        db.insertWithOnConflict(DB_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();

    }
    public void deleteTask(String task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE, DB_COLUM + "  ?", new String[]{task});
        db.close();
    }


    public ArrayList<String> getTaskList(){
        ArrayList<String> tasklist = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[]{DB_COLUM},null,null,null,null,null);
        while(cursor.moveToNext()){
            int index = cursor.getColumnIndex(DB_COLUM);
            tasklist.add(cursor.getString(index));
        }
        cursor.close();
        db.close();
        return tasklist;
    }
}