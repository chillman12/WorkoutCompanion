package com.example.finalproject.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.DetailActivity;
import com.example.finalproject.MainActivity;
import com.example.finalproject.ModifyActivity;
import com.example.finalproject.R;
import com.example.finalproject.data.DatabaseHandler;
import com.example.finalproject.model.Exercise;

/**
 * A simple {@link Fragment} subclass.
 */
public class Detail extends Fragment implements View.OnClickListener {

    private Button search;
    private EditText searchText;
    private TextView error;

    public Detail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.detail, container, false);

        searchText = view.findViewById(R.id.editSearch);
        search = view.findViewById(R.id.buttonSearch);
        error = view.findViewById(R.id.errorSearch);

        search.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSearch:
                DatabaseHandler db = new DatabaseHandler(getContext());
                if (!searchText.getText().toString().equals("")) {
                    try{
                    Exercise exercise = db.getExerciseByName(searchText.getText().toString());
                    Intent intent = new Intent(getContext(), DetailActivity.class);
                    intent.putExtra("id",exercise.getId());
                    intent.putExtra("workoutDay",exercise.getWorkoutDay());
                    intent.putExtra("name", exercise.getExerciseName());
                    intent.putExtra("rep",exercise.getExerciseRep());
                    intent.putExtra("set",exercise.getExerciseSet());
                    intent.putExtra("detail",exercise.getExerciseDetail());
                    db.close();
                    startActivity(intent);
                    }catch (Exception e){
                        error.setText("Name not found");
                    }
                } else {

                }
                break;
            default:
                Toast.makeText(getContext(), "Unexpected error", Toast.LENGTH_LONG).show();
                break;

        }
    }
}
