package com.imb.swat.generics;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.imb.swat.Bean.BeanImb;
import com.imb.swat.R;
import com.imb.swat.helper.Constants;

import roboguice.inject.InjectView;

public class FragmentMap
        extends BaseFragment {
    @InjectView(R.id.mapView)
    private MapView   mMapView;
    private GoogleMap googleMap;
    private BeanImb   bean;
    //    private LocationManager locationManager;
    //    private static final long  MIN_TIME     = 400;
    //    private static final float MIN_DISTANCE = 1000;

    public FragmentMap(BeanImb bean) {
        this.bean = bean;
    }

    @Override
    public int layout() {
        return R.layout.fragment_map;
    }

    @Override
    public int menuLayout() {
        return 0;
    }

    @Override
    public void setView(View view, Bundle savedInstanceState) {
        if (bean != null)
            setTitle(bean.getName());

        initMap(savedInstanceState);
    }

    // ================================================================================
    // Map
    // ================================================================================
    private void initMap(Bundle savedInstanceState) {
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();
        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.setMyLocationEnabled(true);

        addMarker();
    }

    private void addMarker() {
        // create marker
        String address= bean.getAddress().replaceAll("(.{40})", "$1\n");
        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(bean.getLatitude(), bean.getLongitude())).title(bean.getName()).snippet(address);

        // adding marker
        googleMap.addMarker(marker);

        zoomToMarker();
    }

    private void zoomToMarker() {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(bean.getLatitude(), bean.getLongitude()),
                Constants.DEFAULT_ZOOM_MAPS));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(bean.getLatitude(), bean.getLongitude()))
                .zoom(13) // Sets the zoom
                .build(); // Creates a CameraPosition from the builder
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
