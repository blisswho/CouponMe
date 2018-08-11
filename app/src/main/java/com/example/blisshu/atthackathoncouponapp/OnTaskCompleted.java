package com.example.blisshu.atthackathoncouponapp;

import com.google.android.gms.location.places.AutocompletePrediction;

import java.util.ArrayList;

public interface OnTaskCompleted{
    void onTaskCompleted(ArrayList<AutocompletePrediction> doc);
}