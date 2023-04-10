package com.example.borrar;

import static com.example.borrar.db.BBDD_Exercise.TABLE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.borrar.Adapter.ExercisesListAdapter;
import com.example.borrar.Adapter.SeriesListAdapter;
import com.example.borrar.Classes.ExerciseClass;
import com.example.borrar.Classes.SeriesClass;
import com.example.borrar.db.BBDD_Serie;
import com.example.borrar.db.BBDD_Session;
import com.example.borrar.db.dbHelper_Exercise;
import com.example.borrar.db.dbHelper_serie;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class Exercise extends AppCompatActivity {

    TextView name;
    RecyclerView listExercises;
    ImageView addSameSerie;
    ArrayList<ExerciseClass> listArrayExercises;
    TextView finis_ex;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        Bundle datos=getIntent().getExtras();
        String idText= String.valueOf(datos.getInt("id"));
        ExerciseClass exercise=getExercises(idText);

        name=findViewById(R.id.title_EX_Act);
        name.setText(exercise.getName());

        //Finish exercise
        finis_ex=findViewById(R.id.finish_ex);
        finis_ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<SeriesClass> listSeries;
                Bundle datos=getIntent().getExtras();
                String idText= String.valueOf(datos.getInt("id"));
                listSeries= showSeries(idText);
                for(SeriesClass serie : listSeries) {
                    try {
                        dbHelper_serie helper=new dbHelper_serie(Exercise.this);
                        SQLiteDatabase db=helper.getWritableDatabase();

                        ContentValues values=new ContentValues();
                        values.put(BBDD_Session.COLUMN_serie,serie.getId());
                        //Fecha
                        Calendar calendario = Calendar.getInstance();
                        int day = calendario.get(Calendar.DAY_OF_MONTH);
                        int month = calendario.get(Calendar.MONTH) + 1;
                        int year = calendario.get(Calendar.YEAR);
                        String fecha = String.valueOf(day);
                        values.put(BBDD_Session.COLUMN_date, fecha);


                        long newRowId=db.insert(BBDD_Session.TABLE_NAME,null,values);
                        if(newRowId==-1){
                            Toast.makeText(getApplicationContext(),"Error -1", Toast.LENGTH_LONG).show();

                        }else{Toast.makeText(getApplicationContext(),"The exercise has been saved", Toast.LENGTH_LONG).show();}

                    }catch(Exception e) {Toast.makeText(getApplicationContext(),"Error when adding the exercise", Toast.LENGTH_LONG).show();}


                }


            }
        });

        //RecyclerView
        listExercises=findViewById(R.id.listSeries);
        listExercises.setLayoutManager(new LinearLayoutManager(this));

        listArrayExercises= new ArrayList<>();


        //Pass the query below to the adapter in order to place the items
        SeriesListAdapter adapter= new SeriesListAdapter(showSeries(idText));
        listExercises.setAdapter(adapter);


        //Add serie button
        addSameSerie= findViewById(R.id.addSameserieBT);
        addSameSerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle datos=getIntent().getExtras();
                String idText= String.valueOf(datos.getInt("id"));
                addSerie(idText);

            }
        });

        //Refresh
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Bundle datos=getIntent().getExtras();
                String idText= String.valueOf(datos.getInt("id"));
                SeriesListAdapter adapter= new SeriesListAdapter(showSeries(idText));
                listExercises.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        });




    }



    //Query to the database in order to get the exercise from the id
    public ExerciseClass getExercises(String id){
        dbHelper_Exercise dbHelper=new dbHelper_Exercise(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ExerciseClass exercise=null;
        Cursor cursor=null;


        cursor=db.rawQuery("SELECT * FROM "+ TABLE_NAME+" WHERE id == "+id, null);
        if(cursor.moveToFirst()){
            do{
                exercise=new ExerciseClass();
                exercise.setId(cursor.getInt(0));
                exercise.setName(cursor.getString(1));
                exercise.setProgram(cursor.getInt(2));
            } while(cursor.moveToNext());
        }
        cursor.close();
        return exercise;
    }

    //Query to the database
    public ArrayList<SeriesClass> showSeries(String idExecise){
        dbHelper_serie dbHelper=new dbHelper_serie(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<SeriesClass> listSeries=new ArrayList<>();
        SeriesClass series=null;
        Cursor cursor=null;

        cursor=db.rawQuery("SELECT * FROM "+ BBDD_Serie.TABLE_NAME+" WHERE exercise == "+idExecise, null);
        if(cursor.moveToFirst()){
            do{
                series=new SeriesClass();
                series.setId(cursor.getInt(0));
                series.setExercise(cursor.getInt(1));
                series.setRepetitions(cursor.getInt(2));
                series.setWeight(cursor.getString(3));
                series.setRest(cursor.getString(4));
                series.setNotes(cursor.getString(5));

                listSeries.add(series);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return listSeries;
    }

    public void addSerie(String id){
        dbHelper_serie dbHelper=new dbHelper_serie(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SeriesClass serie=null;
        Cursor cursor=null;

        //get the last serie of that exercise
        try {
            cursor = db.rawQuery("SELECT * FROM " + BBDD_Serie.TABLE_NAME + " WHERE exercise == " + id, null);
            if (cursor.moveToLast()) {
                serie = new SeriesClass();
                serie.setId(cursor.getInt(0));
                serie.setExercise(cursor.getInt(1));
                serie.setRepetitions(cursor.getInt(2));
                serie.setWeight(cursor.getString(3));
                serie.setRest(cursor.getString(4));
                serie.setNotes(cursor.getString(5));
            }
            cursor.close();
        }catch(Exception e) {Toast.makeText(getApplicationContext(),"Error when adding the serie", Toast.LENGTH_LONG).show();}

        //Add the same serie
        try {
            ContentValues values=new ContentValues();
            values.put(BBDD_Serie.COLUMN_exercise,serie.getExercise());
            values.put(BBDD_Serie.COLUMN_repetitions,serie.getRepetitions());
            values.put(BBDD_Serie.COLUMN_weights,serie.getWeight() );
            values.put(BBDD_Serie.COLUMN_rest,serie.getRest() );
            values.put(BBDD_Serie.COLUMN_notes,serie.getNotes() );


            long newRowId=db.insert(BBDD_Serie.TABLE_NAME,null,values);

            if(newRowId==-1){
                Toast.makeText(getApplicationContext(),"Error -1", Toast.LENGTH_LONG).show();

            }else{Toast.makeText(getApplicationContext(),"The new serie has been saved", Toast.LENGTH_LONG).show();}

        }catch(Exception e) {Toast.makeText(getApplicationContext(),"Error when adding the serie", Toast.LENGTH_LONG).show();}
    }

    public void ejecutar_add_serie(View v){
        Intent i=new Intent(this,AddSerie.class);

        Bundle datos=getIntent().getExtras();
        int id= datos.getInt("id");
        i.putExtra("id",id);

        startActivity(i);
    }
}