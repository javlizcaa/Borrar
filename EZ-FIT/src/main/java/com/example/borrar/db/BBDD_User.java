package com.example.borrar.db;

public class BBDD_User {
    private BBDD_User(){  }
    public static final String TABLE_NAME="t_users";
    public static final String COLUMN_id="id";
    public static final String COLUMN_name="name";
    public static final String COLUMN_surname="surname";
    public static final String COLUMN_mail="mail";
    public static final String COLUMN_password="password";
    public static final String COLUMN_age="age";
    public static final String COLUMN_height="height";
    public static final String COLUMN_weight="weight";


    private static final String TEXT_TYPE=" TEXT";
    private static final String INT_TYPE=" INTEGER";
    private static final String FLOAT_TYPE=" REAL";
    private static final String COMMA_SEP=",";
    public static final String SQL_CREATE_ENTRIES=
            "CREATE TABLE "+BBDD_User.TABLE_NAME+" ("+BBDD_User.COLUMN_id+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +BBDD_User.COLUMN_name+TEXT_TYPE+COMMA_SEP
                    +BBDD_User.COLUMN_surname+TEXT_TYPE+COMMA_SEP
                    +BBDD_User.COLUMN_mail+TEXT_TYPE+COMMA_SEP
                    +BBDD_User.COLUMN_password+TEXT_TYPE+COMMA_SEP
                    +BBDD_User.COLUMN_age+INT_TYPE+COMMA_SEP
                    +BBDD_User.COLUMN_height+FLOAT_TYPE+COMMA_SEP
                    +BBDD_User.COLUMN_weight+FLOAT_TYPE+" )";
    public static final String SQL_DELETE_ENTRIES=
            "DROP TABLE IF EXISTS"+ BBDD_User.TABLE_NAME;
}
