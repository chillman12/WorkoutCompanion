package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.data.DatabaseHandler;
import com.example.finalproject.fragment.Detail;
import com.example.finalproject.model.Exercise;

public class ModifyActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner workday;
    private EditText exName;
    private EditText exRep;
    private EditText exSet;
    private EditText exDetail;
    private TextView error;

    private int workoutDay;
    private String exNameString;
    private int exRepInt;
    private int exSetInt;
    private String exDetailString;

    private Button update;
    private Button back;

    private Bundle bundle;

    private Exercise exercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        workday = findViewById(R.id.dayOfWeekSpinnerUpdate);
        exName = findViewById(R.id.exerciseNameUpdate);
        exRep = findViewById(R.id.repetitionUpdate);
        exSet = findViewById(R.id.setUpdate);
        exDetail = findViewById(R.id.detailUpdate);
        error = findViewById(R.id.errorUpdate);

        update = findViewById(R.id.updateExercise);
        back = findViewById(R.id.goBack);

        update.setOnClickListener(this);
        back.setOnClickListener(this);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            String exName2 = bundle.getString("name");
            String exRep2 = String.valueOf(bundle.getInt("rep"));
            String exSet2 = String.valueOf(bundle.getInt("set"));
            String exDetail2 = bundle.getString("detail");


            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                    R.array.dayOfWeekArray, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            workday.setAdapter(adapter);
            int spinnerPosition = adapter.getPosition(String.valueOf(bundle.getInt("workoutDay")));
            workday.setSelection(spinnerPosition);

            exName.setText(exName2);
            exRep.setText(exRep2);
            exSet.setText(exSet2);
            exDetail.setText(exDetail2);
        } else {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            Toast.makeText(getApplicationContext(), "And Error occurred, please try again !", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onClick(View view) {
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        switch (view.getId()) {
            case R.id.updateExercise:

                workoutDay = Integer.parseInt(workday.getSelectedItem().toString());
                exNameString = exName.getText().toString();

                try {
                    exRepInt = Integer.parseInt(exRep.getText().toString());
                } catch (Exception e) {
                    error.setText("Invalid repetition(s)");
                }

                try {
                    exSetInt = Integer.parseInt(exSet.getText().toString());
                } catch (Exception e) {
                    error.setText("Invalid set(s)");
                }

                exDetailString = exDetail.getText().toString();

                if (!exNameString.equals("")) {
                    if (exRepInt > 0) {
                        if (exSetInt > 0) {
                            if (!exDetailString.equals("")) {
                                try {
                                    // Do stuff here
                                    Exercise exercise = new Exercise(bundle.getInt("id"), workoutDay, exNameString, exRepInt, exSetInt, exDetailString);

                                    Log.d("Update", "id" + bundle.getInt("id"));
                                    Log.d("Update", "workoutDay" + bundle.getInt("workoutDay"));
                                    Log.d("Update", "name" + bundle.getInt("name"));
                                    Log.d("Update", "rep" + bundle.getInt("rep"));
                                    Log.d("Update", "set" + bundle.getInt("set"));
                                    Log.d("Update", "detail" + bundle.getInt("detail"));

                                    db.updateExercise(exercise);
                                    db.close();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    Toast.makeText(getApplicationContext(), "Successfully updated !", Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), "Unexpected error, Try again !", Toast.LENGTH_LONG).show();
                                }
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


                break;
            case R.id.goBack:

                Intent intent = getIntent();
                intent.setClass(getApplicationContext(), DetailActivity.class);
                startActivity(intent);

                break;
            default:
                Toast.makeText(getApplicationContext(), "Unexpected error", Toast.LENGTH_LONG).show();
                break;
        }

    }
}
