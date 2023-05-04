package com.example.borrar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.borrar.Classes.UserClass;
import com.example.borrar.db.BBDD_User;
import com.example.borrar.db.dbHelper_User;

public class EditProfile extends AppCompatActivity {

    TextView edit_name,edit_surname,edit_email,edit_password,edit_age,edit_height,edit_weight;
    Button edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //set user information

        edit_name=findViewById(R.id.edit_name);
        edit_surname=findViewById(R.id.edit_surname);
        edit_email=findViewById(R.id.edit_email);
        edit_password=findViewById(R.id.edit_password);
        edit_height=findViewById(R.id.edit_height);
        edit_weight=findViewById(R.id.edit_weight);
        edit_age=findViewById(R.id.edit_age);

        UserClass user=getUserInfo();
        edit_name.setText(String.valueOf(user.getName()));
        edit_surname.setText(String.valueOf(user.getSurname()));
        edit_email.setText(String.valueOf(user.getMail()));
        edit_password.setText(String.valueOf(user.getPassword()));
        edit_height.setText(Float.toString(user.getHeight()));
        edit_weight.setText(Float.toString(user.getWeight()));
        edit_age.setText(Integer.toString(user.getAge()));

        //edit profile

        edit=findViewById(R.id.editBt);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbHelper_User dbHelper=new dbHelper_User(EditProfile.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(BBDD_User.COLUMN_name, edit_name.getText().toString());
                values.put(BBDD_User.COLUMN_surname, edit_surname.getText().toString());
                values.put(BBDD_User.COLUMN_mail, edit_email.getText().toString());
                values.put(BBDD_User.COLUMN_password, edit_password.getText().toString());
                values.put(BBDD_User.COLUMN_age, edit_age.getText().toString());
                values.put(BBDD_User.COLUMN_height, edit_height.getText().toString());
                values.put(BBDD_User.COLUMN_weight, edit_weight.getText().toString());
                int numRows = db.update(BBDD_User.TABLE_NAME, values, "id = ?", new String[] { String.valueOf(user.getId()) });

                if (numRows > 0) {
                    Toast.makeText(getApplicationContext(),"Information correctly updated", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("SQLite", "information could not be updated");
                }
                db.close();
            }
        });



    }

    public UserClass getUserInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        String userID = sharedPreferences.getString("userID", "");
        dbHelper_User dbHelper=new dbHelper_User(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        UserClass user=null;
        Cursor cursor=null;

        cursor=db.rawQuery("SELECT * FROM "+ BBDD_User.TABLE_NAME+" WHERE id == "+userID, null);
        if (cursor.moveToFirst()) {
            user=new UserClass();
            user.setId(cursor.getInt(0));
            user.setName(cursor.getString(1));
            user.setSurname(cursor.getString(2));
            user.setMail(cursor.getString(3));
            user.setPassword(cursor.getString(4));
            user.setAge(cursor.getInt(5));
            user.setHeight(cursor.getFloat(6));
            user.setWeight(cursor.getFloat(7));
        }
        cursor.close();
        return user;
    }
}