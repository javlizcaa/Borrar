package com.example.borrar;

import static com.example.borrar.db.BBDD_Exercise.TABLE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.borrar.Adapter.ExercisesListAdapter;
import com.example.borrar.Classes.ExerciseClass;
import com.example.borrar.db.dbHelper_Exercise;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class AllExercises extends AppCompatActivity {

    RecyclerView listExercises;
    ArrayList<ExerciseClass> listArrayExercises;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_exercises);

        //RecyclerView
        listExercises=findViewById(R.id.listEx);
        listExercises.setLayoutManager(new LinearLayoutManager(this));

        listArrayExercises= new ArrayList<>();

        Bundle datos=getIntent().getExtras();
        String program=datos.getString("program");

        //Pass the query below to the adapter in order to place the items
        ExercisesListAdapter adapter= new ExercisesListAdapter(showExercises(program));
        listExercises.setAdapter(adapter);

        //bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnItemSelectedListener(this::onNavigationItemSelected);

    }

    //Query to the database
    public ArrayList<ExerciseClass> showExercises(String program){
        dbHelper_Exercise dbHelper=new dbHelper_Exercise(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<ExerciseClass> listExercise=new ArrayList<>();
        ExerciseClass exercise=null;
        Cursor cursor=null;
        Toast.makeText(getApplicationContext(),program, Toast.LENGTH_LONG).show();


        cursor=db.rawQuery("SELECT * FROM "+ TABLE_NAME+" WHERE PROGRAM == "+program, null);
        if(cursor.moveToFirst()){
            do{
                exercise=new ExerciseClass();
                exercise.setId(cursor.getInt(0));
                exercise.setName(cursor.getString(1));
                exercise.setProgram(cursor.getString(2));

                listExercise.add(exercise);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return listExercise;
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



    public void ejecutar_añadir(View v){
        Intent i=new Intent(this,AddExercise.class);
        startActivity(i);
    }
}