package com.example.borrar.Adapter;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.borrar.Classes.SeriesClass;
import com.example.borrar.R;
import com.example.borrar.db.BBDD_Serie;
import com.example.borrar.db.dbHelper_serie;

import java.util.ArrayList;

public class SeriesListAdapterProgress extends RecyclerView.Adapter<SeriesListAdapterProgress.SerieViewHolder> {

    ArrayList<SeriesClass> listSeries;
    public SeriesListAdapterProgress(ArrayList<SeriesClass> listSeries){
        this.listSeries=listSeries;
    }

    @NonNull
    @Override
    public SerieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Set the items from onBindViewHolder on yhe objects from ExerciseViewHolder on the selected xml file
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.serie_list_itemprogress,null,false);
        return new SerieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SerieViewHolder holder, int position) {
        //For the item i of the query we get the name program values
        holder.viewRepes.setText(String.valueOf(listSeries.get(position).getRepetitions()));
        holder.viewWeight.setText(listSeries.get(position).getWeight());
        holder.viewRest.setText(listSeries.get(position).getRest());
        holder.viewNotes.setText(listSeries.get(position).getNotes());



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
