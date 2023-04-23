package com.example.borrar.db;

public class BBDD_Serie {
    private BBDD_Serie(){  }
    public static final String TABLE_NAME="t_Serie";
    public static final String COLUMN_id="id";
    public static final String COLUMN_exercise="exercise";
    public static final String COLUMN_repetitions="repetitions";
    public static final String COLUMN_weights="weights";
    public static final String COLUMN_rest="rest";
    public static final String COLUMN_notes="notes";
    public static final String COLUMN_visible="visible";


    private static final String TEXT_TYPE=" TEXT";
    private static final String INT_TYPE=" INTEGER";
    private static final String COMMA_SEP=",";
    public static final String SQL_CREATE_ENTRIES=
            "CREATE TABLE "+BBDD_Serie.TABLE_NAME+" ("+BBDD_Serie.COLUMN_id+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +BBDD_Serie.COLUMN_exercise+INT_TYPE+COMMA_SEP
                    +BBDD_Serie.COLUMN_repetitions+INT_TYPE+COMMA_SEP
                    +BBDD_Serie.COLUMN_weights+TEXT_TYPE+COMMA_SEP
                    +BBDD_Serie.COLUMN_rest+TEXT_TYPE+COMMA_SEP
                    +BBDD_Serie.COLUMN_notes+TEXT_TYPE+COMMA_SEP
                    +BBDD_Serie.COLUMN_visible+INT_TYPE+" )";
    public static final String SQL_DELETE_ENTRIES=
            "DROP TABLE IF EXISTS"+ BBDD_Serie.TABLE_NAME;
}
