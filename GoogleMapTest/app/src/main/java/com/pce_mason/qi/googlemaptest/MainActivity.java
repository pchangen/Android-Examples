package com.pce_mason.qi.googlemaptest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                MapFragment mapFragment = MapFragment.newInstance(MainActivity.this);
                fragmentTransaction.replace(R.id.fragments_layout,mapFragment);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            GpsInfo gpsInfo = new GpsInfo(MainActivity.this);
            if (gpsInfo.isGetLocation()) {  // GPS On, NN.XX
                LatLng current_location = new LatLng(gpsInfo.getLatitude(), gpsInfo.getLongitude());
                Toast.makeText(MainActivity.this, String.valueOf(current_location.latitude), Toast.LENGTH_SHORT).show();
            } else {
                gpsInfo.showSettingsAlert();     // GPS setting Alert
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
