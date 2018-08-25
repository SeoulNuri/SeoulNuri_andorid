package com.hello.seoulnuri.Planner

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.hello.seoulnuri.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.SupportMapFragment





class PlannerAddOneActivity : AppCompatActivity(), OnMapReadyCallback {
    override fun onMapReady(map: GoogleMap?) {
        val SEOUL = LatLng(37.56, 126.97)

        val markerOptions = MarkerOptions()
        markerOptions.position(SEOUL)
        markerOptions.title("서울")
        markerOptions.snippet("한국의 수도")
        map!!.addMarker(markerOptions)

        map!!.moveCamera(CameraUpdateFactory.newLatLng(SEOUL))
        map!!.animateCamera(CameraUpdateFactory.zoomTo(10f))
    }

    lateinit var fragmentManager : FragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planner_add_one)


/*
        val fragmentManager = supportFragmentManager
        val mapFragment = fragmentManager!!
                .findFragmentById(R.id.map) as MapFragment
        mapFragment!!.getMapAsync(this)*/

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

    }
}
