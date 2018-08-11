package com.example.blisshu.atthackathoncouponapp;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataBufferUtils;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.AutocompletePredictionBufferResponse;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private GeoDataClient mGeoDataClient;
    private OnTaskCompleted listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mGeoDataClient = Places.getGeoDataClient(getApplicationContext());

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

        final LatLng home = getLocationFromAddress(getApplicationContext(), "10 Emmet Ct.");
        mMap.addMarker(new MarkerOptions().position(home).title("Marker at home"));

        LatLng away = new LatLng(home.latitude+0.25, home.longitude);
        mMap.addMarker(new MarkerOptions().position(away).title("marker away"));

        LatLng away2 = new LatLng(home.latitude-0.25, home.longitude);
        mMap.addMarker(new MarkerOptions().position(away2).title("marker away2"));

        LatLng away3 = new LatLng(home.latitude, home.longitude-0.25);
        mMap.addMarker(new MarkerOptions().position(away3).title("marker away3"));

        LatLng away4 = new LatLng(home.latitude, home.longitude+0.25);
        mMap.addMarker(new MarkerOptions().position(away4).title("marker away4"));

        LatLng southwest = new LatLng(home.latitude, home.longitude);
        LatLng northeast = new LatLng(home.latitude, home.longitude);

        final LatLngBounds newBounds = LatLngBounds.builder().include(away).include(away2).include(away3).include(away4).build();
        final LatLngBounds new2Bounds = new LatLngBounds(southwest, northeast);

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                //Your code where exception occurs goes here...
                moveToCurrentLocation(home, newBounds);

                AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                        .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ESTABLISHMENT)
                        .setTypeFilter(AutocompleteFilter.TYPE_FILTER_GEOCODE)
                        .setCountry("US")
//                        .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                        .build();

                new ResultBuffer("burger king", new2Bounds, typeFilter, mGeoDataClient, listener).execute();
            }
        });

        }

    private void moveToCurrentLocation(LatLng currentLocation, LatLngBounds lat)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,10));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(lat, 50));


    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
