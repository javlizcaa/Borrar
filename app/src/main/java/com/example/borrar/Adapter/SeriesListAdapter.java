package com.example.borrar.Adapter;

import static com.example.borrar.db.BBDD_Exercise.TABLE_NAME;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.borrar.Classes.ExerciseClass;
import com.example.borrar.Classes.SeriesClass;
import com.example.borrar.Exercise;
import com.example.borrar.R;
import com.example.borrar.db.BBDD_Serie;
import com.example.borrar.db.BBDD_User;
import com.example.borrar.db.dbHelper_Exercise;
import com.example.borrar.db.dbHelper_serie;

import java.util.ArrayList;

public class SeriesListAdapter extends RecyclerView.Adapter<SeriesListAdapter.SerieViewHolder> {

    ArrayList<SeriesClass> listSeries;
    public SeriesListAdapter(ArrayList<SeriesClass> listSeries){
        this.listSeries=listSeries;
    }

    @NonNull
    @Override
    public SerieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Set the items from onBindViewHolder on yhe objects from ExerciseViewHolder on the selected xml file
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.serie_list_item,null,false);
        return new SerieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SerieViewHolder holder, int position) {
        //For the item i of the query we get the name program values
        holder.viewRepes.setText(String.valueOf(listSeries.get(position).getRepetitions()));
        holder.viewWeight.setText(listSeries.get(position).getWeight());
        holder.viewRest.setText(listSeries.get(position).getRest());
        holder.viewNotes.setText(listSeries.get(position).getNotes());

        //Delete serie
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper_serie dbHelper=new dbHelper_serie(holder.itemView.getContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues values=new ContentValues();

                values.put(BBDD_Serie.COLUMN_id,listSeries.get(holder.getAdapterPosition()).getId());
                values.put(BBDD_Serie.COLUMN_exercise,listSeries.get(holder.getAdapterPosition()).getExercise());
                values.put(BBDD_Serie.COLUMN_repetitions,listSeries.get(holder.getAdapterPosition()).getRepetitions());
                values.put(BBDD_Serie.COLUMN_weights,String.valueOf(listSeries.get(holder.getAdapterPosition()).getWeight()));
                values.put(BBDD_Serie.COLUMN_rest,String.valueOf(listSeries.get(holder.getAdapterPosition()).getRest()));
                values.put(BBDD_Serie.COLUMN_notes,String.valueOf(listSeries.get(holder.getAdapterPosition()).getNotes()));
                values.put(BBDD_Serie.COLUMN_visible,0);
                String[] args = { String.valueOf(listSeries.get(holder.getAdapterPosition()).getId())}; // Valor del identificador del registro que deseas actualizar
                try{
                    db.update(BBDD_Serie.TABLE_NAME, values, "id=?", args);
                    db.close();
                    Toast.makeText(holder.itemView.getContext(), "Serie has been deleted", Toast.LENGTH_SHORT).show();

                }catch(Exception e) {Toast.makeText(holder.itemView.getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }});

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper_serie dbHelper=new dbHelper_serie(holder.itemView.getContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues values=new ContentValues();

                values.put(BBDD_Serie.COLUMN_id,listSeries.get(holder.getAdapterPosition()).getId());
                values.put(BBDD_Serie.COLUMN_exercise,listSeries.get(holder.getAdapterPosition()).getExercise());
                values.put(BBDD_Serie.COLUMN_repetitions,holder.viewRepes.getText().toString());
                values.put(BBDD_Serie.COLUMN_weights,holder.viewWeight.getText().toString());
                values.put(BBDD_Serie.COLUMN_rest,holder.viewRest.getText().toString());
                values.put(BBDD_Serie.COLUMN_notes,holder.viewNotes.getText().toString());
                try{
                    int numRows = db.update(BBDD_Serie.TABLE_NAME, values, "id = ?", new String[] { String.valueOf(listSeries.get(holder.getAdapterPosition()).getId()) });
                    if (numRows > 0) {
                        Toast.makeText(holder.itemView.getContext(), "Serie has been updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(holder.itemView.getContext(), "Serie could not be updated", Toast.LENGTH_SHORT).show();
                    }
                    db.close();

                }catch(Exception e) {Toast.makeText(holder.itemView.getContext(), "Error", Toast.LENGTH_SHORT).show();
                }


            }});

    }

    @Override
    public int getItemCount() {
        return listSeries.size();
    }

    public class SerieViewHolder extends RecyclerView.ViewHolder {
        // Here we match the objects with the view ofthe .xml

        TextView viewRepes,viewWeight,viewRest,viewNotes;
        ImageButton del,edit;

        public SerieViewHolder(@NonNull View itemView) {
            super(itemView);

            viewRepes=itemView.findViewById(R.id.repesListItem);
            viewWeight=itemView.findViewById(R.id.weightListItem);
            viewRest=itemView.findViewById(R.id.RestListItem);
            viewNotes=itemView.findViewById(R.id.NotesListItem);

            del=itemView.findViewById(R.id.delete);
            edit=itemView.findViewById(R.id.edit);



        }
    }

}
