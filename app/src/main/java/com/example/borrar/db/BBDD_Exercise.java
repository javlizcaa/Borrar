
package com.example.borrar.db;

public class BBDD_Exercise {
    private BBDD_Exercise(){  }
    public static final String TABLE_NAME="t_Exercises";
    public static final String COLUMN_id="id";
    public static final String COLUMN_name="Nombre";
    public static final String COLUMN_program="Program";
    public static final String COLUMN_userID="userID";


    private static final String TEXT_TYPE=" TEXT";
    private static final String INT_TYPE=" INTEGER";
    private static final String COMMA_SEP=",";
    public static final String SQL_CREATE_ENTRIES=
            "CREATE TABLE "+BBDD_Exercise.TABLE_NAME+" ("+BBDD_Exercise.COLUMN_id+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +BBDD_Exercise.COLUMN_name+TEXT_TYPE+COMMA_SEP
                    +BBDD_Exercise.COLUMN_program+INT_TYPE+COMMA_SEP
                    +BBDD_Exercise.COLUMN_userID+INT_TYPE+" )";
    public static final String SQL_DELETE_ENTRIES=
            "DROP TABLE IF EXISTS"+ BBDD_Exercise.TABLE_NAME;
}


