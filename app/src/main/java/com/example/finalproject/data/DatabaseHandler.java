package com.example.finalproject.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.finalproject.R;
import com.example.finalproject.model.Exercise;
import com.example.finalproject.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "("
                + Util.KEY_EX_ID + " INTEGER PRIMARY KEY, "
                + Util.KEY_WORK_DAY + " INTEGER, "
                + Util.KEY_EX_NAME + " TEXT, "
                + Util.KEY_EX_REP + " INTEGER, "
                + Util.KEY_EX_SET + " INTEGER, "
                + Util.KEY_EX_DETAIL + " TEXT "
                + ")";
        db.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String DROP_TABLE = String.valueOf(R.string.db_drop);
        db.execSQL(DROP_TABLE, new String[]{Util.DATABASE_NAME});

        onCreate(db);
    }

    public void addExercise(Exercise exercise) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_EX_NAME, exercise.getExerciseName());
        values.put(Util.KEY_WORK_DAY, exercise.getWorkoutDay());
        values.put(Util.KEY_EX_REP, exercise.getExerciseRep());
        values.put(Util.KEY_EX_SET, exercise.getExerciseSet());
        values.put(Util.KEY_EX_DETAIL, exercise.getExerciseDetail());

        db.insert(Util.TABLE_NAME, null, values);
        Log.d("handler", "addExercise: " + " item added");

        db.close();
    }

    public int updateExercise(Exercise exercise) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_WORK_DAY, exercise.getWorkoutDay());
        values.put(Util.KEY_EX_NAME, exercise.getExerciseName());
        values.put(Util.KEY_EX_REP, exercise.getExerciseRep());
        values.put(Util.KEY_EX_SET, exercise.getExerciseSet());
        values.put(Util.KEY_EX_DETAIL, exercise.getExerciseDetail());

        return db.update(Util.TABLE_NAME, values, Util.KEY_EX_ID + "=?", new String[]{String.valueOf(exercise.getId())});
    }

    public Exercise getExerciseByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.KEY_EX_ID, Util.KEY_WORK_DAY, Util.KEY_EX_NAME, Util.KEY_EX_REP, Util.KEY_EX_SET, Util.KEY_EX_DETAIL}, Util.KEY_EX_NAME + "=?", new String[]{String.valueOf(name)}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            Exercise exercise = new Exercise();
            exercise.setId(cursor.getInt(0));
            exercise.setWorkoutDay(cursor.getInt(1));
            exercise.setExerciseName(cursor.getString(2));
            exercise.setExerciseRep(cursor.getInt(3));
            exercise.setExerciseSet(cursor.getInt(4));
            exercise.setExerciseDetail(cursor.getString(5));
            db.close();
            return exercise;
        } else {
            db.close();
            return null;
        }
    }


    public void deleteExercise(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Util.TABLE_NAME, Util.KEY_EX_ID + "=?", new String[]{String.valueOf(id)});
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Util.TABLE_NAME, null, null);
    }


    public List<Exercise> getAllExercises() {
        List<Exercise> exerciseList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                Exercise exercise = new Exercise();
                exercise.setId(cursor.getInt(0));
                exercise.setWorkoutDay(cursor.getInt(1));
                exercise.setExerciseName(cursor.getString(2));
                exercise.setExerciseRep(cursor.getInt(3));
                exercise.setExerciseSet(cursor.getInt(4));
                exercise.setExerciseDetail(cursor.getString(5));

                exerciseList.add(exercise);
            } while (cursor.moveToNext());
        }
        return exerciseList;
    }

    public List<Exercise> getExercisesOfDay(int index) {
        List<Exercise> exerciseList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = "SELECT * FROM " + Util.TABLE_NAME + " WHERE " + Util.KEY_WORK_DAY + "=" + String.valueOf(index);
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                Exercise exercise = new Exercise();
                exercise.setId(cursor.getInt(0));
                exercise.setWorkoutDay(cursor.getInt(1));
                exercise.setExerciseName(cursor.getString(2));
                exercise.setExerciseRep(cursor.getInt(3));
                exercise.setExerciseSet(cursor.getInt(4));
                exercise.setExerciseDetail(cursor.getString(5));

                exerciseList.add(exercise);
            } while (cursor.moveToNext());
        }
        return exerciseList;
    }
}
