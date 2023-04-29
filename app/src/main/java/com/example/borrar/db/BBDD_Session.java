package com.example.borrar.db;

public class BBDD_Session {
    private BBDD_Session(){  }
    public static final String TABLE_NAME="t_Session";
    public static final String COLUMN_id="id";
    public static final String COLUMN_serie="serie";
    public static final String COLUMN_date="date";
    public static final String COLUMN_userID="userID";


    private static final String TEXT_TYPE=" TEXT";
    private static final String INT_TYPE=" INTEGER";
    private static final String DATE_TYPE=" DATE";
    private static final String COMMA_SEP=",";
    public static final String SQL_CREATE_ENTRIES=
            "CREATE TABLE "+BBDD_Session.TABLE_NAME+" ("+BBDD_Session.COLUMN_id+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +BBDD_Session.COLUMN_serie+INT_TYPE+COMMA_SEP
                    +BBDD_Session.COLUMN_date+TEXT_TYPE+COMMA_SEP
                    +BBDD_Session.COLUMN_userID+INT_TYPE+" )";
    public static final String SQL_DELETE_ENTRIES=
            "DROP TABLE IF EXISTS"+ BBDD_Session.TABLE_NAME;


}
