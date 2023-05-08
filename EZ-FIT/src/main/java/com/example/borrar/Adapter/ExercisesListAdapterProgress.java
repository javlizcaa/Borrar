package com.example.borrar.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.borrar.Classes.ExerciseClass;
import com.example.borrar.ProgressExercise;
import com.example.borrar.R;

import java.util.ArrayList;

public class ExercisesListAdapterProgress extends RecyclerView.Adapter<ExercisesListAdapterProgress.ExerciseViewHolder> {

    ArrayList<ExerciseClass> listExercises;
    public ExercisesListAdapterProgress(ArrayList<ExerciseClass> listExercises){
        this.listExercises=listExercises;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Set the items from onBindViewHolder on yhe objects from ExerciseViewHolder on the selected xml file
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_list_item,null,false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        //For the item i of the query we get the name program values
        holder.viewName.setText(listExercises.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.itemView.getContext(), ProgressExercise.class);
                intent.putExtra("id",listExercises.get(holder.getAdapterPosition()).getId());
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listExercises.size();
    }

    public class ExerciseViewHolder extends RecyclerView.ViewHolder {
        // Here we match the objects with the view ofthe .xml

        TextView viewName;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);

            viewName=itemView.findViewById(R.id.viewName);
        }
    }
}
