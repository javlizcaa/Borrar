package com.example.borrar;

import static com.example.borrar.db.BBDD_Exercise.TABLE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.borrar.Classes.ExerciseClass;
import com.example.borrar.Classes.SeriesClass;
import com.example.borrar.Classes.SessionClass;
import com.example.borrar.Classes.UserClass;
import com.example.borrar.db.BBDD_Serie;
import com.example.borrar.db.BBDD_Session;
import com.example.borrar.db.BBDD_User;
import com.example.borrar.db.dbHelper_Exercise;
import com.example.borrar.db.dbHelper_Session;
import com.example.borrar.db.dbHelper_User;
import com.example.borrar.db.dbHelper_serie;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity2 extends AppCompatActivity {

    TextView n_works;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view2);
        bottomNavigationView.setOnItemSelectedListener(this::onNavigationItemSelected);

        //set the total number of workouts
        String userID=getUserId();

        n_works = findViewById(R.id.n_works);
        //n_works.setText(String.valueOf(getSessionWorks(userID)));

    }

    //in order to know the selected item of the bottom navigation
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment;
        int itemId = item.getItemId();
        if (itemId == R.id.profile_fragment) {
            fragment = new ProfileFragment();
        }
        else if (itemId == R.id.progress_fragment) {
            fragment = new ProgressFragment();
        }
        else {
            fragment = new ProgramsFragment();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.navFragment2, fragment)
                .commit();
        return true;
    }

    //Query to the database to get the number of workouts
    public int getSessionWorks(String userID){
        dbHelper_Session dbHelper=new dbHelper_Session(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int n_session=0;
        Cursor cursor=null;
        cursor=db.rawQuery("SELECT * FROM "+ BBDD_Session.TABLE_NAME+" WHERE userID == " + userID, null);
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    n_session+=1;

                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return n_session;
    }


    public String getUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        String userID = sharedPreferences.getString("userID", "");
        return userID;
    }

    public void ejecutar_PullPush(View v){
        Intent i=new Intent(this,AllExercises.class);
        i.putExtra("program","0");
        startActivity(i);
    }
    public void ejecutar_Wider(View v){
        Intent i=new Intent(this,AllExercises.class);
        i.putExtra("program","1");
        startActivity(i);
    }
    public void ejecutar_Chrono(View v){
        Intent i=new Intent(this,chrono.class);
        startActivity(i);
    }
    public void ejecutar_editProfile(View v){
        Intent i=new Intent(this,EditProfile.class);
        startActivity(i);
    }
    public void ejecutar_LogIn(View v){
        Intent i=new Intent(this,LogIn.class);
        startActivity(i);
    }

}