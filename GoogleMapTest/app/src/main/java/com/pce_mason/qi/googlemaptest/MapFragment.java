package com.pce_mason.qi.googlemaptest;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.maps.android.clustering.ClusterManager;


public class MapFragment extends Fragment implements OnMapReadyCallback {
    Context context;
    private ClusterManager<MyItem> mClusterManager;
    MapView map;
    GpsInfo gpsInfo;

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance(Context context) {
        MapFragment fragment = new MapFragment();
        fragment.context = context;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        gpsInfo = new GpsInfo(context);
        map = (MapView) view.findViewById(R.id.mapView);
        map.getMapAsync(this);
        locationPermissionCheck();
        if (!gpsInfo.isGetLocation()) {
            gpsInfo.showSettingsAlert();     // GPS setting Alert
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        map.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        map.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        map.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        map.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        map.onLowMemory();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //액티비티가 처음 생성될 때 실행되는 함수
        if(map != null)
        {
            map.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng QI = new LatLng(32.882118, -117.234609);
        setUpClusterer(googleMap);
//        MarkerOptions markerOptions = new MarkerOptions().position(QI).title("Calit2").snippet("Qualcomm Institute");
//        googleMap.addMarker(markerOptions);
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(QI));
//        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.mapstyle_retro));

    }
    private void setUpClusterer(GoogleMap map) {
        // Position the map.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gpsInfo.getLatitude(), gpsInfo.getLongitude()), 10));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<MyItem>(context, map);

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        map.setOnCameraIdleListener(mClusterManager);
        map.setOnMarkerClickListener(mClusterManager);

        // Add cluster items (markers) to the cluster manager.
        addItems();
    }
    private void addItems() {

        // Set some lat/lng coordinates to start with.
        double lat = gpsInfo.getLatitude();
        double lng = gpsInfo.getLongitude();
        // Add ten cluster items in close proximity, for purposes of this example.
        for (int i = 0; i < 20; i++) {
            double offset = i / 60d;
            lat = lat + offset;
            lng = lng + offset;
            MyItem offsetItem = new MyItem(lat, lng,"QI","Test");
            mClusterManager.addItem(offsetItem);
        }
    }
    public void locationPermissionCheck() {
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
        }
    }
}
