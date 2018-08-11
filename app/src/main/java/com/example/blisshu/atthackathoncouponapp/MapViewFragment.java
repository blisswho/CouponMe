package com.example.blisshu.atthackathoncouponapp;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapViewFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener {

    MapView mMapView;

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private GeoDataClient mGeoDataClient;
    private OnTaskCompleted listener;

    private String couponBrand;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.location_fragment, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mGeoDataClient = Places.getGeoDataClient(getContext());

        //Determine which areas to load
        couponBrand = getArguments().getString("coupon_brand");

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                mMap = googleMap;

                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                // Add a marker in Sydney and move the camera
                LatLng sydney = new LatLng(-34, 151);
                mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

                //SHOES
                LatLng loc1 = new LatLng(Double.parseDouble("40.744843"), Double.parseDouble("-73.992050"));
                mMap.addMarker(new MarkerOptions().position(loc1).title("100 W 26th St, New York, NY 10001"));
                LatLng loc2 = new LatLng(Double.parseDouble("40.751526"), Double.parseDouble("-73.998235"));
                mMap.addMarker(new MarkerOptions().position(loc2).title("365 9th Ave, New York, NY 10001"));
                LatLng loc3 = new LatLng(Double.parseDouble("40.751592"), Double.parseDouble("-73.991978"));
                mMap.addMarker(new MarkerOptions().position(loc3).title("Penn Plaza 250 W 34Th St &, 8th Avenue, New York, NY 10119"));
                LatLng loc4 = new LatLng(Double.parseDouble("40.749384"), Double.parseDouble("-73.986318"));
                mMap.addMarker(new MarkerOptions().position(loc4).title("27 W 34th St, New York, NY 10001"));
                LatLng loc5 = new LatLng(Double.parseDouble("40.749843"), Double.parseDouble("-73.988535"));
                mMap.addMarker(new MarkerOptions().position(loc5).title("110 W 34th St, New York, NY 10001"));
                LatLng loc6 = new LatLng(Double.parseDouble("40.740028"), Double.parseDouble("-73.991310"));
                mMap.addMarker(new MarkerOptions().position(loc6).title("156 5th Ave, New York, NY 10010"));
                LatLng loc7 = new LatLng(Double.parseDouble("40.723373"), Double.parseDouble("-73.999118"));
                mMap.addMarker(new MarkerOptions().position(loc7).title("529 Broadway, New York, NY 10012"));

                //TIDE
                LatLng tide1 = new LatLng(Double.parseDouble("40.714328"), Double.parseDouble("-74.010914"));
                mMap.addMarker(new MarkerOptions().position(tide1).title("255 Greenwich St, New York, NY 10007"));
                LatLng tide2 = new LatLng(Double.parseDouble("40.814138"), Double.parseDouble("-73.983026"));
                mMap.addMarker(new MarkerOptions().position(tide2).title("543 River Rd, Edgewater, NJ 07020"));
                LatLng tide3 = new LatLng(Double.parseDouble("40.795386"), Double.parseDouble("-73.931022"));
                mMap.addMarker(new MarkerOptions().position(tide3).title("517 E 117th St #201, New York, NY 10035"));
                LatLng tide4 = new LatLng(Double.parseDouble("40.747564"), Double.parseDouble("-74.004719"));
                mMap.addMarker(new MarkerOptions().position(tide4).title("500 W 23rd St, New York, NY 10011"));
                LatLng tide5 = new LatLng(Double.parseDouble("40.759498"), Double.parseDouble("-73.995855"));
                mMap.addMarker(new MarkerOptions().position(tide5).title( "500 W 42nd St, New York, NY 10036"));
                LatLng tide6 = new LatLng(Double.parseDouble("40.743727"), Double.parseDouble("-73.991812"));
                mMap.addMarker(new MarkerOptions().position(tide6).title("750 6th Ave, New York, NY 10010"));

                Subways
//                40.754469, -73.998500
//                40.753387, -73.995835
//                40.751389, -73.993056
//                40.746044, -73.993874
//                41.046263, -73.862627
//                40.753085, -73.986768
//                40.748782, -73.986811
//                40.754951, -73.981891


                LatLng socialCenter = new LatLng(Double.parseDouble("40.751653"), Double.parseDouble("-74.007152"));
                moveToCurrentLocation(socialCenter);

//                "40.740028,-73.991310" || "40.723373", "-73.999118"

//                final LatLng home = getLocationFromAddress(getContext(), "10 Emmet Ct.");
//                mMap.addMarker(new MarkerOptions().position(home).title("Marker at home"));
//
//                LatLng away = new LatLng(home.latitude+0.25, home.longitude);
//                mMap.addMarker(new MarkerOptions().position(away).title("marker away"));
//
//                LatLng away2 = new LatLng(home.latitude-0.25, home.longitude);
//                mMap.addMarker(new MarkerOptions().position(away2).title("marker away2"));
//
//                LatLng away3 = new LatLng(home.latitude, home.longitude-0.25);
//                mMap.addMarker(new MarkerOptions().position(away3).title("marker away3"));
//
//                LatLng away4 = new LatLng(home.latitude, home.longitude+0.25);
//                mMap.addMarker(new MarkerOptions().position(away4).title("marker away4"));
//
//                LatLng southwest = new LatLng(home.latitude, home.longitude);
//                LatLng northeast = new LatLng(home.latitude, home.longitude);
//
//                final LatLngBounds newBounds = LatLngBounds.builder().include(away).include(away2).include(away3).include(away4).build();
//                final LatLngBounds new2Bounds = new LatLngBounds(southwest, northeast);

                mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                    @Override
                    public void onMapLoaded() {
                        //Your code where exception occurs goes here...
//                        moveToCurrentLocation(home, newBounds);

//                        mMap.addMarker(new MarkerOptions().position(home).title("Marker at home"));

                        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ESTABLISHMENT)
                                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_GEOCODE)
                                .setCountry("US")
//                        .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                                .build();

//                new ResultBuffer("burger king", new2Bounds, typeFilter, mGeoDataClient, listener).execute();
                    }
                });
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    private void moveToCurrentLocation(LatLng currentLocation)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,60));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);

//        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(lat, 50));

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

}