package com.pce_mason.qi.mapfragmenttest;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

//@@//
public class BlankFragment extends Fragment implements OnMapReadyCallback {
//@@//
//@@//
    private float mMapZoomLevel = 14;
    GpsInfo gpsInfo;
    GoogleMap mMap;
    static View view; // 프래그먼트의 뷰 인스턴스
    Context context;
//@@//

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BlankFragment() {
        // Required empty public constructor

    }


    public static BlankFragment newInstance(Context context) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

//@@//
        fragment.context = context;
        fragment.gpsInfo = new GpsInfo(context);
        fragment.gpsInfo.getLocation();
        if (!fragment.gpsInfo.isGetLocation()) {
            fragment.gpsInfo.showSettingsAlert();     // GPS setting Alert
        }
//@@//
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //@@//
        try {
            view = inflater.inflate(R.layout.fragment_blank, container, false);
        }catch (InflateException e){

        }// Inflate the layout for this fragment
        final MapFragment mapFragment = (MapFragment) getActivity().getFragmentManager()
                .findFragmentById(R.id.fragmentMap);
        mapFragment.getMapAsync(this);

        return view;
        //@@//
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

//@@//
    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mMap = googleMap;
        LatLng location = new LatLng(gpsInfo.getLatitude(), gpsInfo.getLongitude());
        mMap.addMarker(new MarkerOptions().position(location));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, mMapZoomLevel));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(mMapZoomLevel));
    }
//@@//

}
