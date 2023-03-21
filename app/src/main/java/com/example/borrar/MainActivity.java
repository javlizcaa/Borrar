package com.example.borrar;

import static com.example.borrar.db.BBDD_Exercise.TABLE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.borrar.Adapter.ExercisesListAdapter;
import com.example.borrar.Classes.ExerciseClass;
import com.example.borrar.db.BBDD_Exercise;
import com.example.borrar.db.dbHelper_Exercise;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView listExercises;
    ArrayList<ExerciseClass> listArrayExercises;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listExercises=findViewById(R.id.listEx);
        listExercises.setLayoutManager(new LinearLayoutManager(this));

        listArrayExercises= new ArrayList<>();

        //Pass the query below to the adapter in order to place the items
        ExercisesListAdapter adapter= new ExercisesListAdapter(showExercises());
        listExercises.setAdapter(adapter);

    }

    //Query to the database
    public ArrayList<ExerciseClass> showExercises(){
        dbHelper_Exercise dbHelper=new dbHelper_Exercise(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<ExerciseClass> listExercise=new ArrayList<>();
        ExerciseClass exercise=null;
        Cursor cursor=null;

        cursor=db.rawQuery("SELECT * FROM "+ TABLE_NAME, null);
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



    public void ejecutar_añadir(View v){
        Intent i=new Intent(this,AddExercise.class);
        startActivity(i);
    }
}