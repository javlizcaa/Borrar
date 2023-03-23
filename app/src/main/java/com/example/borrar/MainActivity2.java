package com.example.borrar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view2);
        bottomNavigationView.setOnItemSelectedListener(this::onNavigationItemSelected);
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

}