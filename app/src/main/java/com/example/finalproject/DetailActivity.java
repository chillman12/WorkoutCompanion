package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.data.DatabaseHandler;
import com.example.finalproject.fragment.TrainingPlan;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView workday;
    private TextView exName;
    private TextView exRep;
    private TextView exSet;
    private TextView exDetail;

    private Button modify;
    private Button delete;

    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        workday = findViewById(R.id.dayOfWeekDetail);
        exName = findViewById(R.id.exerciseNameDetail);
        exRep = findViewById(R.id.repetitionDetail);
        exSet = findViewById(R.id.setDetail);
        exDetail = findViewById(R.id.detailsDetail);

        modify = findViewById(R.id.modifyButton);
        delete = findViewById(R.id.deleteButton);

        modify.setOnClickListener(this);
        delete.setOnClickListener(this);

        bundle = getIntent().getExtras();


        if (bundle != null) {
            String workoutDay = "Day of the week : " + bundle.getInt("workoutDay");
            String exName2 = "Exercise name : " + bundle.getString("name");
            String exRep2 = "Repetition(s) : " + bundle.getInt("rep");
            String exSet2 = "Set(s) : " + bundle.getInt("set");
            String exDetail2 = "Detail : " + bundle.getString("detail");

            workday.setText(workoutDay);
            exName.setText(exName2);
            exRep.setText(exRep2);
            exSet.setText(exSet2);
            exDetail.setText(exDetail2);
        }

    }

    @Override
    public void onClick(View view) {

        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        switch (view.getId()) {
            case R.id.modifyButton:
                Intent intent = new Intent(getApplicationContext(), ModifyActivity.class);
                intent.putExtra("id", bundle.getInt("id"));
                intent.putExtra("workoutDay", bundle.getInt("workoutDay"));
                intent.putExtra("name", bundle.getString("name"));
                intent.putExtra("rep", bundle.getInt("rep"));
                intent.putExtra("set", bundle.getInt("set"));
                intent.putExtra("detail", bundle.getString("detail"));
                db.close();
                startActivity(intent);

                break;
            case R.id.deleteButton:
                db.deleteExercise(bundle.getInt("id"));
                Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                Toast.makeText(getApplicationContext(), "Successfully deleted ! ", Toast.LENGTH_SHORT).show();
                startActivity(intent2);
                db.close();
                break;
            default:
                db.close();
                Toast.makeText(getApplicationContext(), "Unexpected error", Toast.LENGTH_LONG).show();
                break;

        }
    }
}
