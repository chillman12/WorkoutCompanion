package com.example.finalproject.util;

import android.app.Activity;
import android.content.SharedPreferences;

public class PreferenceFinal {
    private SharedPreferences preferences;

    public PreferenceFinal(Activity activity){
        this.preferences = activity.getPreferences(activity.MODE_PRIVATE);
    }

    public void saveIndex(int index){
        preferences.edit().putInt("index",index).apply();
    }

    public int getindex(){return preferences.getInt("index",1); }

}
