package com.example.borrar.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbHelper_serie extends SQLiteOpenHelper {

    private static final int DATABASE_VERSOIN=1;
    private static final String DATABASE_NOMBRE="EZ_FIT.db";
    private static final String TABLE_SERIE="t_Serie";




    public dbHelper_serie(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSOIN);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE" + TABLE_SERIE + "("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "Exercise INTEGER NOT NULL,"+
                "Repetitions INTEGER NOT NULL,"+
                "Weight DOUBLE NOT NULL,"+
                "Notes TEXT NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE"+ TABLE_SERIE);
        onCreate(db);

    }
}
