package com.hello.seoulnuri.view.course;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hello.seoulnuri.R;

import java.util.ArrayList;

public class CourseMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Double> latLang; // 경복궁 위도 경도
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //마커 옵션
//        MarkerOptions makerOptions = new MarkerOptions();

//        makerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.button_spot_select))));
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

        Intent intent = new Intent(this.getIntent());
//        latLang = intent.getDoubleArrayExtra("latLang_list");
        latLang = (ArrayList<Double>) intent.getSerializableExtra("latLang_list");

        Log.d("LatLang", "Lat = " + latLang.get(0) + " Lang = "  + latLang.get(1) );

        // Add a marker in Sydney and move the camera
        LatLng course_item_position = new LatLng(latLang.get(0), latLang.get(1));
        mMap.addMarker(new MarkerOptions().position(course_item_position).title("Marker in Sydney")
        .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.button_spot_select))));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(course_item_position));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
    }
}
