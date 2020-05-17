package com.example.finalproject.model;

public class Exercise {
    private int id;
    private int workoutDay;
    private String exerciseName;
    private int exerciseRep;
    private int exerciseSet;
    private String exerciseDetail;


    public Exercise() {
    }

    public Exercise(int id, int workoutDay, String exerciseName, int exerciseRep, int exerciseSet, String exerciseDetail) {
        this.id = id;
        this.workoutDay = workoutDay;
        this.exerciseName = exerciseName;
        this.exerciseRep = exerciseRep;
        this.exerciseSet = exerciseSet;
        this.exerciseDetail = exerciseDetail;
    }

    public Exercise(int workoutDay, String exerciseName, int exerciseRep, int exerciseSet, String exerciseDetail) {
        this.workoutDay = workoutDay;
        this.exerciseName = exerciseName;
        this.exerciseRep = exerciseRep;
        this.exerciseSet = exerciseSet;
        this.exerciseDetail = exerciseDetail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWorkoutDay() {
        return workoutDay;
    }

    public void setWorkoutDay(int workoutDay) {
        this.workoutDay = workoutDay;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public int getExerciseRep() { return exerciseRep; }

    public void setExerciseRep(int exerciseRep) {
        this.exerciseRep = exerciseRep;
    }

    public int getExerciseSet() {
        return exerciseSet;
    }

    public void setExerciseSet(int exerciseSet) {
        this.exerciseSet = exerciseSet;
    }

    public String getExerciseDetail() {
        return exerciseDetail;
    }

    public void setExerciseDetail(String exerciseDetail) {
        this.exerciseDetail = exerciseDetail;
    }
}
