package com.example.borrar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    RecyclerView listExercises;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listExercises=findViewById(R.id.listEx);
        listExercises.setLayoutManager(new LinearLayoutManager(this));

    }
    public void ejecutar_añadir(View v){
        Intent i=new Intent(this,AddExercise.class);
        startActivity(i);
    }
}