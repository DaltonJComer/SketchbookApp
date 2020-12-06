package com.example.sketchbookapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "Sketchbook_watchlist";
    //private static final String col0 = "ID";
    private static final String col0 = "Name";
    private static final String col1 = "Price";

    public DBHelper(Context Context){
        super(Context,TABLE_NAME,null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createString = "CREATE TABLE " +TABLE_NAME+ " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                col0 + " TEXT, "+col1+" TEXT)";
        db.execSQL(createString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void addData(String[] values){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+col0+" = '"+values[0]+"'";
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.getCount()<=0){
            ContentValues cVals = new ContentValues();
            cVals.put(col0,values[0].trim());
            cVals.put(col1,values[1].trim());
            long resultVal = db.insert(TABLE_NAME,null,cVals);
        }

    }

    public Cursor getComics(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME;
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public void removeComic(String[] values){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM "+TABLE_NAME+" WHERE "+col0+" = '"+values[0]+"' AND "+col1+" = '"+values[1]+"'";
        db.execSQL(query);
    }

}
