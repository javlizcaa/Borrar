package com.example.borrar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.borrar.Classes.ExerciseClass;
import com.example.borrar.db.BBDD_Exercise;
import com.example.borrar.db.BBDD_Serie;
import com.example.borrar.db.dbHelper_Exercise;
import com.example.borrar.db.dbHelper_serie;

public class AddSerie extends AppCompatActivity {

    TextView repetitions,wieght,rest,notes;
    Button ButtonAdd;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_serie);

        repetitions=findViewById(R.id.repetitions);
        wieght=findViewById(R.id.weight);
        rest=findViewById(R.id.rest);
        notes=findViewById(R.id.notes);

        ButtonAdd=(Button)findViewById(R.id.Add_firstSerie);

        ButtonAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    dbHelper_serie helper=new dbHelper_serie(AddSerie.this);
                    SQLiteDatabase db=helper.getWritableDatabase();

                    ContentValues values=new ContentValues();

                    Bundle datos=getIntent().getExtras();
                    int id=datos.getInt("id");
                    values.put(BBDD_Serie.COLUMN_exercise,id);
                    values.put(BBDD_Serie.COLUMN_repetitions,Integer.parseInt(repetitions.getText().toString()));
                    values.put(BBDD_Serie.COLUMN_weights,wieght.getText().toString());
                    values.put(BBDD_Serie.COLUMN_rest,rest.getText().toString());
                    values.put(BBDD_Serie.COLUMN_notes,notes.getText().toString());

                    long newRowId=db.insert(BBDD_Serie.TABLE_NAME,null,values);
                    if(newRowId==-1){
                        Toast.makeText(getApplicationContext(),"Error -1", Toast.LENGTH_LONG).show();

                    }else{Toast.makeText(getApplicationContext(),"The exercise has been saved", Toast.LENGTH_LONG).show();}

                }catch(Exception e) {Toast.makeText(getApplicationContext(),"Error when adding the exercise", Toast.LENGTH_LONG).show();}

            }
        });
    }
}