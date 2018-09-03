package com.example.blisshu.atthackathoncouponapp;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.common.data.DataBufferUtils;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBufferResponse;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ResultBuffer extends AsyncTask<Void, Void, Void>{
//    String place, LatLngBounds mBounds, AutocompleteFilter mPlaceFilter
    private String place;
    private LatLngBounds mBounds;
    private AutocompleteFilter mPlaceFilter;
    private GeoDataClient mGeoDataClient;
    private OnTaskCompleted listener;
    private ArrayList<AutocompletePrediction> res;

    public ResultBuffer(String place, LatLngBounds mBounds, AutocompleteFilter mPlaceFilter, GeoDataClient mGeoDataClient, OnTaskCompleted listener){
        this.place = place;
        this.mBounds = mBounds;
        this.mPlaceFilter = mPlaceFilter;
        this.mGeoDataClient = mGeoDataClient;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Task<AutocompletePredictionBufferResponse> results =
                mGeoDataClient.getAutocompletePredictions(place, mBounds, mPlaceFilter);

        try {
            Tasks.await(results, 60, TimeUnit.SECONDS);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }

        try {
            AutocompletePredictionBufferResponse autocompletePredictions = results.getResult();

            Log.i("tag", "Query completed. Received " + autocompletePredictions.getCount()
                    + " predictions.");

            // Freeze the results immutable representation that can be stored safely.
            res = DataBufferUtils.freezeAndClose(autocompletePredictions);

            Log.i("tag", "passed freezing/closing autocompletePredictions");

            for(int i = 0;i < res.size();i++){
                AutocompletePrediction autoprediction = res.get(i);
                Log.i("tag", "FULL TEXT OF LOC: "+autoprediction.getFullText(null).toString());
                Log.i("tag", "PRIMARY: "+autoprediction.getPrimaryText(null).toString());
                Log.i("tag", "SECOND: "+autoprediction.getSecondaryText(null).toString());
            }

            return null;

        } catch (RuntimeExecutionException e) {
            // If the query did not complete successfully return null
            Log.e("tag", "Error getting autocomplete prediction API call", e);
            return null;
        }
    }

    protected void onPreExecute(){
        //Setup is done here
    }

    protected void onProgressUpdate(Integer... params){
        //Update a progress bar here, or ignore it, it's up to you
    }
    protected void onPostExecute(Bitmap img){
        listener.onTaskCompleted(res);
    }
    protected void onCancelled(){
    }
}