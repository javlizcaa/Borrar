package com.example.borrar;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
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

public class Register extends AppCompatActivity {

    TextView name, surname,email,password,age,height,weight;
    Button register;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Match buttons
        name=findViewById(R.id.RegisterName);
        surname=findViewById(R.id.RegisterSurname);
        email=findViewById(R.id.LogInEmail);
        password=findViewById(R.id.LogInPassword);
        age=findViewById(R.id.RegisterAge);
        height=findViewById(R.id.RegisterHeight);
        weight=findViewById(R.id.RegisterWeight);

        register=findViewById(R.id.Register);

        //Registration
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String StringName=name.getText().toString();
                if (StringName.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Name cannot be empty", Toast.LENGTH_LONG).show();
                }

                String StringEmail=email.getText().toString();
                if (StringEmail.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Email cannot be empty", Toast.LENGTH_LONG).show();
                }
                String StringPassword=password.getText().toString();
                if (StringPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Password cannot be empty", Toast.LENGTH_LONG).show();
                }

                if (!StringName.isEmpty() && !StringEmail.isEmpty() && !StringPassword.isEmpty()) {
                    ArrayList<String> emails = GetEmails();
                    String userEmail = email.getText().toString();
                    if (!emails.contains(userEmail)) { //si no hay usuarios registrados o Si el usuario no esta registrado
                        try {
                            dbHelper_User helper = new dbHelper_User(Register.this);
                            SQLiteDatabase db = helper.getWritableDatabase();

                            ContentValues values = new ContentValues();

                            values.put(BBDD_User.COLUMN_name, name.getText().toString());
                            values.put(BBDD_User.COLUMN_surname, surname.getText().toString());
                            values.put(BBDD_User.COLUMN_mail, email.getText().toString());
                            values.put(BBDD_User.COLUMN_password, password.getText().toString());
                            values.put(BBDD_User.COLUMN_age, parseInt(age.getText().toString()));
                            values.put(BBDD_User.COLUMN_height, Float.parseFloat(height.getText().toString()));
                            values.put(BBDD_User.COLUMN_weight, Float.parseFloat(weight.getText().toString()));

                            long newRowId = db.insert(BBDD_User.TABLE_NAME, null, values);
                            if (newRowId == -1) {
                                Toast.makeText(getApplicationContext(), "Error -1", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "The user has been correctly registered", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(Register.this, LogIn.class);
                                startActivity(i);
                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error in signing in", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "User is already registered", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    //Query to the database to get all emails
    public ArrayList<String> GetEmails(){
        dbHelper_User dbHelper=new dbHelper_User(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<String> listEmails=new ArrayList<>();
        Cursor cursor=null;

        cursor=db.rawQuery("SELECT * FROM "+ BBDD_User.TABLE_NAME, null);
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    listEmails.add(cursor.getString(3));

                } while (cursor.moveToNext());
            }
            cursor.close();
            return listEmails;
        }else {
            cursor.close();
            return new ArrayList<>();}
    }

    public void ejecutar_LogIn(View v){
        Intent i=new Intent(this,LogIn.class);
        startActivity(i);
    }
}