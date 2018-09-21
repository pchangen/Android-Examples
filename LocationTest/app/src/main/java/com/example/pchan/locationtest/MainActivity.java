package com.example.pchan.locationtest;

import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.w3c.dom.Text;

import java.security.Permission;

public class MainActivity extends AppCompatActivity {

    TextView tv_location;
    Button btn_location;
    LatLng current_location; // Latitude, Longitude to GPS
    GpsInfo gps;
    GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationPermissionCheck();

        tv_location = (TextView)findViewById(R.id.tv_location);
        btn_location = (Button) findViewById(R.id.btn_location);
        gps = new GpsInfo(MainActivity.this);
        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gps.isGetLocation()) {  // GPS On, NN.XX
                    current_location = new LatLng(gps.getLatitude(), gps.getLongitude());
                } else {
                    gps.showSettingsAlert();     // GPS setting Alert
                }
                tv_location.setText("LAT:"+ current_location.latitude +" LNG:"+current_location.longitude  );
            }
        });
    }

    public void locationPermissionCheck() {
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
        }
    }
}
