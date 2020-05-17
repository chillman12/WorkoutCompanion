package com.example.finalproject.fragment;



import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.adapter.RecyclerViewAdapter;
import com.example.finalproject.data.DatabaseHandler;
import com.example.finalproject.model.Exercise;
import com.example.finalproject.util.PreferenceFinal;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrainingPlan extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private TextView emptyView;
    private TextView dayIndex;
    private Button dayBefore;
    private Button dayAfter;
    private ArrayList<Exercise> exerciseArrayList;
    private PreferenceFinal preferences;
    private int currentDayIndex = 1;
    private DatabaseHandler db;
    private List<Exercise> exerciseList;

    public TrainingPlan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.training, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        emptyView = view.findViewById(R.id.emptyView);
        dayIndex = view.findViewById(R.id.dayIndex);
        dayBefore = view.findViewById(R.id.dayBefore);
        dayAfter = view.findViewById(R.id.dayAfter);

        preferences = new PreferenceFinal(getActivity());
        currentDayIndex = preferences.getindex();
        dayIndex.setText("Day " + currentDayIndex);

        dayBefore.setOnClickListener(this);
        dayAfter.setOnClickListener(this);

        exerciseArrayList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //----------------DataBase Handler && Recycler View--------------

        db = new DatabaseHandler(view.getContext());

        exerciseList = db.getExercisesOfDay(currentDayIndex);
        for(Exercise exercise : exerciseList){
            Log.d("Exercise","OnCreate: " + exercise.getId());
            Log.d("Exercise", "OnCreate:" + exercise.getExerciseRep());
            exerciseArrayList.add(exercise);
        }

        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(),exerciseArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);


        replaceRViewIfEmpty();
        //-----------------Shared Prefs && Index ----------------------




        return view;
    }


    public void replaceRViewIfEmpty(){
        if(exerciseArrayList.isEmpty()){
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }else{
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.dayBefore:
                previousDay();
                break;
            case R.id.dayAfter:
                nextDay();
                break;
            default:
                Toast.makeText(getContext(), "Unexpected error", Toast.LENGTH_LONG).show();
                break;
        }
        preferences.saveIndex(currentDayIndex);
        Log.d("saveIndex", "index : " + currentDayIndex);
    }

    public void previousDay(){
        currentDayIndex--;
        if (currentDayIndex <= 0){
            currentDayIndex = 7;
        }

        dayIndex.setText("Day " + currentDayIndex);
        updateExercises();
        replaceRViewIfEmpty();

    }

    public void nextDay(){
        currentDayIndex++;
        if(currentDayIndex >= 8 ){
            currentDayIndex = 1;
        }

        dayIndex.setText("Day " + currentDayIndex);
        updateExercises();
        replaceRViewIfEmpty();
    }

    public void updateExercises(){
        exerciseArrayList.clear();
        exerciseList.clear();
        exerciseList = db.getExercisesOfDay(currentDayIndex);

        for(Exercise exercise : exerciseList){
            Log.d("Exercise","OnCreate: " + exercise.getId());
            Log.d("Exercise", "OnCreate:" + exercise.getExerciseRep());
            exerciseArrayList.add(exercise);
        }
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(),exerciseArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
}
