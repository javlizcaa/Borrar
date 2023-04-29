package com.example.borrar;

import static com.example.borrar.db.BBDD_Exercise.TABLE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.borrar.Classes.ExerciseClass;
import com.example.borrar.Classes.SeriesClass;
import com.example.borrar.Classes.SessionClass;
import com.example.borrar.db.BBDD_Serie;
import com.example.borrar.db.BBDD_Session;
import com.example.borrar.db.dbHelper_Exercise;
import com.example.borrar.db.dbHelper_Session;
import com.example.borrar.db.dbHelper_serie;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity2 extends AppCompatActivity {

    ArrayList<SessionClass> mySessions;
    SeriesClass myserie;
    HashMap<String, Integer> TotalWorkout = new HashMap<>();
    Integer accumulator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view2);
        bottomNavigationView.setOnItemSelectedListener(this::onNavigationItemSelected);

        // Recuperar el nombre de usuario de SharedPreferences
        String userID=getUserId();

        //Progress
        String date;
        int day;
        int month;
        for(month=1; month<13; month++){
            for(day=1; day<31; day++){
                date= String.valueOf(day)+String.valueOf(month)+String.valueOf(2022);
                mySessions=getSessionWork(date);
                accumulator=0;
                for(SessionClass session : mySessions) {
                    try {
                        myserie=GetSerie(session.getSerie());
                        accumulator=accumulator+myserie.getRepetitions();

                    }catch (Exception e){ Toast.makeText(getApplicationContext(),"Session not found", Toast.LENGTH_LONG).show();}
                }
                TotalWorkout.put(date, accumulator);
            }
        }


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

    //Query to the database to get the workouts per day
    public ArrayList<SessionClass> getSessionWork(String date){
        dbHelper_Session dbHelper=new dbHelper_Session(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<SessionClass> listSession=new ArrayList<>();
        SessionClass session=null;
        Cursor cursor=null;
        cursor=db.rawQuery("SELECT * FROM "+ BBDD_Session.TABLE_NAME+" WHERE date == "+date, null);
        if(cursor.moveToFirst()){
            do{
                session=new SessionClass();
                session.setId(cursor.getInt(0));
                session.setSerie(cursor.getInt(1));
                session.setDate(cursor.getString(2));
                listSession.add(session);

            } while(cursor.moveToNext());
        }
        cursor.close();
        return listSession;
    }

    //Query to the database to get the serie given the session
    public SeriesClass GetSerie(int serieId){
        dbHelper_serie dbHelper=new dbHelper_serie(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SeriesClass serie=null;
        Cursor cursor=null;

        cursor=db.rawQuery("SELECT * FROM "+ BBDD_Serie.TABLE_NAME+" WHERE id == "+serieId, null);
        if(cursor.moveToFirst()){
            do{
                serie=new SeriesClass();
                serie.setId(cursor.getInt(0));
                serie.setExercise(cursor.getInt(1));
                serie.setRepetitions(cursor.getInt(2));
                serie.setWeight(cursor.getString(3));
                serie.setRest(cursor.getString(4));
                serie.setNotes(cursor.getString(5));

            } while(cursor.moveToNext());
        }
        cursor.close();
        return serie;
    }

    public String getUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        String userID = sharedPreferences.getString("userID", "");
        return userID;
    }
}