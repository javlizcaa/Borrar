package com.example.borrar.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbHelper_User extends SQLiteOpenHelper {

    private static final int DATABASE_VERSOIN=1;
    private static final String DATABASE_NOMBRE="EZ_FIT4.db";

    public dbHelper_User(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSOIN);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {db.execSQL(BBDD_User.SQL_CREATE_ENTRIES);}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(BBDD_User.SQL_DELETE_ENTRIES);
        onCreate(db);

    }
}
