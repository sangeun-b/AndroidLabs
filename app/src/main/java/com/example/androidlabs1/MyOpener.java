package com.example.androidlabs1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

public class MyOpener extends SQLiteOpenHelper {

    protected static final String DATABASE_NAME = "ChatDB";
    protected static final int VERSION_NUM = 1;
    public static final String TABLE_NAME="CHAT";
    public static final String COL_ID = "_id";
    public static final String COL_MESSAGE="MESSAGE";
    public static final String COL_IS_SEND = "IS_SEND";

    public MyOpener(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_MESSAGE + " MESSAGE,"
                + COL_IS_SEND + " IS_SEND);");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);

        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
