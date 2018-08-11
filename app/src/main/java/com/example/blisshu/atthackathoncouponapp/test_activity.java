package com.example.blisshu.atthackathoncouponapp;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class test_activity extends AppCompatActivity {
    Toolbar mActionBarToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);

        mActionBarToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Setup an Appointment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String coupon = getIntent().getStringExtra("coupon_brand");

        Bundle bundle = new Bundle();
        bundle.putString("coupon_brand", coupon);

        MapViewFragment shit = new MapViewFragment();
        shit.setArguments(bundle);

        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragContainer, shit);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}
