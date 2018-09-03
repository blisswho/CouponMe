package com.example.blisshu.atthackathoncouponapp;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.android.gms.flags.impl.DataUtils;

public class MapActivity extends AppCompatActivity {
    Toolbar mActionBarToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);

        String coupon = getIntent().getStringExtra("coupon_brand");

        Bundle bundle = new Bundle();
        bundle.putString("coupon_brand", coupon);

        mActionBarToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("See stores near you!");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MapViewFragment fragment = new MapViewFragment();
        fragment.setArguments(bundle);

        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}
