package com.example.finalproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.DetailActivity;
import com.example.finalproject.R;
import com.example.finalproject.model.Exercise;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private Context context;
    private List<Exercise> exerciseList;

    public RecyclerViewAdapter(Context context, List<Exercise> exerciseList){
        this.context = context;
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Exercise exercise = exerciseList.get(position);

        String set = "set : " + exercise.getExerciseRep();
        String rep = "reps : " + exercise.getExerciseSet();
        holder.exName.setText(exercise.getExerciseName());
        holder.exRep.setText(set);
        holder.exSet.setText(rep);
        //holder.exDetail.setText(exercise.getExerciseDetail());
        //holder.workoutDay.setText(exercise.getWorkoutDay());
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView exName;
        private TextView exRep;
        private TextView exSet;
        //private TextView exDetail;
        //private TextView workoutDay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            exName = itemView.findViewById(R.id.RV_exName);
            exRep = itemView.findViewById(R.id.RV_rep);
            exSet = itemView.findViewById(R.id.RV_set);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Exercise exercise = exerciseList.get(position);
            Log.d("Clicked", "onClick:"+ exercise.getExerciseName());

            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("id",exercise.getId());
            intent.putExtra("workoutDay",exercise.getWorkoutDay());
            intent.putExtra("name", exercise.getExerciseName());
            intent.putExtra("rep",exercise.getExerciseRep());
            intent.putExtra("set",exercise.getExerciseSet());
            intent.putExtra("detail",exercise.getExerciseDetail());
            intent.putExtra("location","training");

            context.startActivity(intent);
        }
    }


}
