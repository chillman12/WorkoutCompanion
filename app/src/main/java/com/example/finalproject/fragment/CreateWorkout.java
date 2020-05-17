package com.example.finalproject.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.finalproject.R;
import com.example.finalproject.data.DatabaseHandler;
import com.example.finalproject.model.Exercise;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateWorkout extends Fragment {

    private Button addWorkout;
    private EditText exName;
    private EditText exRep;
    private EditText exSet;
    private EditText exDetail;
    private Spinner workoutDay;
    private TextView error;

    public CreateWorkout() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.create, container, false);

        workoutDay = view.findViewById(R.id.dayOfWeekSpinner);
        exName = view.findViewById(R.id.exerciseNameUpdate);
        exRep = view.findViewById(R.id.repetitionUpdate);
        exSet = view.findViewById(R.id.setUpdate);
        exDetail = view.findViewById(R.id.detailUpdate);
        addWorkout = view.findViewById(R.id.addExercise);
        error = view.findViewById(R.id.error);


        //-----------Setup Spinner-----------


        //Change color of arrow on spinner
        //spinner.getBackground().setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_ATOP);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.dayOfWeekArray, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        workoutDay.setAdapter(adapter);
        //--------------Database Handler----------

        addWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler db = new DatabaseHandler(view.getContext());

                int exRepInt = 0;
                int exSetInt = 0;
                int workoutDayInt = Integer.parseInt(workoutDay.getSelectedItem().toString());
                String exNameString = exName.getText().toString();

                try {
                    exRepInt = Integer.parseInt(exRep.getText().toString());
                } catch (Exception e) {
                    error.setText("Please enter repetition(s)");
                }

                try {
                    exSetInt = Integer.parseInt(exSet.getText().toString());
                } catch (Exception e) {
                    error.setText("Please enter Set(s)dfsdf");
                }

                String exDetailString = exDetail.getText().toString();

                Exercise exercise = new Exercise(workoutDayInt, exNameString, exRepInt, exSetInt, exDetailString);
                Log.d("Testing", "Exercise name : " + exNameString);
                Log.d("Testing", "Set value : " + exSetInt);
                Log.d("Testing", "Rep value : " + exRepInt);

                if (!exNameString.equals("")) {
                    if (exRepInt > 0) {
                        if (exSetInt > 0) {
                            if (!exDetailString.equals("")) {
                                try {
                                    db.addExercise(exercise);
                                    db.close();
                                }catch(Exception e){
                                    Toast.makeText(getContext(), "Unexpected error, Try again !", Toast.LENGTH_LONG).show();
                                }
                                exName.setText("");
                                exRep.setText("");
                                exSet.setText("");
                                exDetail.setText("");
                                Toast.makeText(getContext(), "Exercise added !", Toast.LENGTH_LONG).show();
                            } else {
                                error.setText("Invalid Detail");
                            }
                        } else {
                            error.setText("Number invalid on set(s)");
                        }
                    } else {
                        error.setText("Number invalid on repetition(s)!");
                    }
                } else {
                    error.setText("Invalid name");
                }


            }
        });

        return view;
    }

}
