package com.example.borrar;

import static com.example.borrar.db.BBDD_Exercise.TABLE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.borrar.Adapter.ExercisesListAdapter;
import com.example.borrar.Adapter.SeriesListAdapter;
import com.example.borrar.Classes.ExerciseClass;
import com.example.borrar.db.dbHelper_Exercise;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class AllExercises extends AppCompatActivity {

    RecyclerView listExercises;
    ArrayList<ExerciseClass> listArrayExercises;
    TextView mysession;
    Boolean started;
    Boolean finish;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_exercises);

        //start session
        started=Boolean.FALSE;
        finish=Boolean.FALSE;
        mysession=findViewById(R.id.session);
        mysession.setText("Start session");
        mysession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (started==Boolean.FALSE){
                    mysession.setText("End session");
                    started=Boolean.TRUE;
                }else{finish=Boolean.TRUE;}

            }
        });


        //RecyclerView
        listExercises=findViewById(R.id.listSeries);
        listExercises.setLayoutManager(new LinearLayoutManager(this));

        listArrayExercises= new ArrayList<>();

        Bundle datos=getIntent().getExtras();
        String program=datos.getString("program");

        //Pass the query below to the adapter in order to place the items
        ExercisesListAdapter adapter= new ExercisesListAdapter(showExercises(program));
        listExercises.setAdapter(adapter);


        //Refresh
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Bundle datos=getIntent().getExtras();
                String program=datos.getString("program");
                ExercisesListAdapter adapter= new ExercisesListAdapter(showExercises(program));
                listExercises.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    //Query to the database
    public ArrayList<ExerciseClass> showExercises(String program){
        dbHelper_Exercise dbHelper=new dbHelper_Exercise(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<ExerciseClass> listExercise=new ArrayList<>();
        ExerciseClass exercise=null;
        Cursor cursor=null;

        cursor=db.rawQuery("SELECT * FROM "+ TABLE_NAME+" WHERE PROGRAM == "+program, null);
        if(cursor.moveToFirst()){
            do{
                exercise=new ExerciseClass();
                exercise.setId(cursor.getInt(0));
                exercise.setName(cursor.getString(1));
                exercise.setProgram(cursor.getInt(2));

                listExercise.add(exercise);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return listExercise;
    }

    public void ejecutar_add(View v){
        Intent i=new Intent(this,AddExercise.class);
        Bundle datos=getIntent().getExtras();
        String program=datos.getString("program");
        i.putExtra("program",program);
        startActivity(i);
    }

    public void ejecutar_Register(View v){
        Intent i=new Intent(this,Register.class);
        startActivity(i);
    }
}