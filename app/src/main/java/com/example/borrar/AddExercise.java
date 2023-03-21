package com.example.borrar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.borrar.db.BBDD_Exercise;
import com.example.borrar.db.dbHelper_Exercise;

public class AddExercise extends AppCompatActivity {

    Button ButtonAdd;
    EditText nombre,program;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        ButtonAdd=(Button)findViewById(R.id.ADD);

        nombre=(EditText)findViewById(R.id.name_add);
        program=(EditText)findViewById(R.id.program_add);

        ButtonAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    dbHelper_Exercise helper=new dbHelper_Exercise(AddExercise.this);
                    SQLiteDatabase db=helper.getWritableDatabase();

                    ContentValues values=new ContentValues();
                    values.put(BBDD_Exercise.COLUMN_name,nombre.getText().toString());
                    int i = Integer.parseInt(String.valueOf(program.getText()));
                    values.put(BBDD_Exercise.COLUMN_program,i);

                    long newRowId=db.insert(BBDD_Exercise.TABLE_NAME,null,values);
                    if(newRowId==-1){
                        Toast.makeText(getApplicationContext(),"Error -1", Toast.LENGTH_LONG).show();

                    }else{Toast.makeText(getApplicationContext(),"The exercise has been saved", Toast.LENGTH_LONG).show();}

                }catch(Exception e) {Toast.makeText(getApplicationContext(),"Error when adding the exercise", Toast.LENGTH_LONG).show();}

            }
        });
    }
}