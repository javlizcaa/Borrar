package com.example.borrar;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.borrar.db.BBDD_Session;
import com.example.borrar.db.dbHelper_Session;

public class ProfileFragment extends Fragment {

    TextView n_works;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        //set the total number of workouts
        n_works = view.findViewById(R.id.works);
        String number_of_workouts=String.valueOf(getSessionWorks());
        String text="Workouts completed: "+number_of_workouts;
        n_works.setText(text);
        return view;
    }

    //Query to the database to get the number of workouts
    public int getSessionWorks(){
        dbHelper_Session dbHelper=new dbHelper_Session(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String userID=getUserId();
        Cursor cursor=null;
        cursor=db.rawQuery("SELECT date FROM "+ BBDD_Session.TABLE_NAME+" WHERE userID == " + userID+ " GROUP BY date", null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public String getUserId() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        String userID = sharedPreferences.getString("userID", "");
        return userID;
    }

}