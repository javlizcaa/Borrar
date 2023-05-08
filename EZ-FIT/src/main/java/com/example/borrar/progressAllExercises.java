package com.example.borrar;

import static com.example.borrar.db.BBDD_Exercise.TABLE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.borrar.Adapter.ExercisesListAdapterProgress;
import com.example.borrar.Adapter.SeriesListAdapter;
import com.example.borrar.Classes.ExerciseClass;
import com.example.borrar.db.dbHelper_Exercise;

import java.util.ArrayList;

public class progressAllExercises extends AppCompatActivity {

    RecyclerView listExercises;
    ArrayList<ExerciseClass> listArrayExercises;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_allexercises);


        //RecyclerView
        listExercises=findViewById(R.id.listSeries);
        listExercises.setLayoutManager(new LinearLayoutManager(this));

        listArrayExercises= new ArrayList<>();

        //Pass the query below to the adapter in order to place the items
        String userID=getUserId();
        ExercisesListAdapterProgress adapter= new ExercisesListAdapterProgress(showExercises(userID));
        listExercises.setAdapter(adapter);

        //Refresh
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String userID=getUserId();
                ExercisesListAdapterProgress adapter= new ExercisesListAdapterProgress(showExercises(userID));
                listExercises.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    //Query to the database
    public ArrayList<ExerciseClass> showExercises(String userID){
        dbHelper_Exercise dbHelper=new dbHelper_Exercise(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<ExerciseClass> listExercise=new ArrayList<>();
        ExerciseClass exercise=null;
        Cursor cursor=null;

        cursor=db.rawQuery("SELECT * FROM "+ TABLE_NAME+" WHERE userID == " + userID, null);


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


    public String getUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        String userID = sharedPreferences.getString("userID", "");
        return userID;
    }
}