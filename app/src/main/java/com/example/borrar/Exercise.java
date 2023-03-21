package com.example.borrar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Exercise extends AppCompatActivity {

    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        Intent intent = getIntent();
        String nameText = intent.getStringExtra("name");

        name=findViewById(R.id.title_EX_Act);
        name.setText(nameText);
    }
}