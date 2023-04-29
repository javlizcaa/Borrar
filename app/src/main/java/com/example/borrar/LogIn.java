package com.example.borrar;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.borrar.db.BBDD_User;
import com.example.borrar.db.dbHelper_User;

import java.util.ArrayList;

public class LogIn extends AppCompatActivity {

    TextView email,password;
    Button logIn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        email=findViewById(R.id.LogInEmail);
        password=findViewById(R.id.LogInPassword);

        logIn=findViewById(R.id.Register);

        //Registration
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userEmail=email.getText().toString();
                if (userEmail.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Email cannot be empty", Toast.LENGTH_LONG).show();
                }
                String userPassword=password.getText().toString();
                if (userPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Password cannot be empty", Toast.LENGTH_LONG).show();
                }
                if (!userEmail.isEmpty() && !userPassword.isEmpty()){
                    String ID=CheckLogIn(userEmail,userPassword);
                    if (ID!="false"){
                        // Guardar el ID de usuario en SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("userID", ID);
                        editor.apply();

                        Intent i=new Intent(LogIn.this,MainActivity2.class);
                        startActivity(i);
                    }
                }

            }
        });


    }

    //Query to the database to check credentials
    public String CheckLogIn(String email,String password ){
        dbHelper_User dbHelper=new dbHelper_User(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String DBpassword;
        Cursor cursor=null;
        cursor=db.rawQuery("SELECT * FROM "+ BBDD_User.TABLE_NAME+" WHERE mail = '" + email + "'", null);
        if (cursor.getCount() > 0) { // si el cursor tiene algún resultado
            cursor.moveToFirst();
            DBpassword=cursor.getString(4);
            if (DBpassword.equals(password)){
                String name=cursor.getString(1);
                Toast.makeText(getApplicationContext(),"Loged In as: "+name, Toast.LENGTH_LONG).show();
                String ID= String.valueOf(cursor.getInt(0));
                cursor.close();
                return ID;
            }else{
                Toast.makeText(getApplicationContext(),"Invalid password", Toast.LENGTH_LONG).show();
                cursor.close();
                return "false";
            }

        } else {
            Toast.makeText(getApplicationContext(),"Email not found, please register", Toast.LENGTH_LONG).show();
            cursor.close();
            return "false";
        }
    }


    public void ejecutar_Register(View v){
        Intent i=new Intent(this,Register.class);
        startActivity(i);
    }
}