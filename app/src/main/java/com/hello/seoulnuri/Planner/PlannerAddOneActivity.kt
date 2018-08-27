package com.hello.seoulnuri.Planner

import android.Manifest
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.hello.seoulnuri.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.SupportMapFragment
import com.hello.seoulnuri.Base.Init
import kotlinx.android.synthetic.main.activity_planner_add_one.*
import android.content.pm.PackageManager
import android.Manifest.permission
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.support.v4.app.ActivityCompat
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.location.Location
import android.os.Build
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.hello.seoulnuri.Base.ManagePermissions
import com.hello.seoulnuri.utils.ToastMaker


class PlannerAddOneActivity : AppCompatActivity(), OnMapReadyCallback, View.OnClickListener, Init, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener {
    override fun onMyLocationButtonClick(): Boolean {
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        val SEOUL = LatLng(37.27, 126.24)
        val markerOptions = MarkerOptions()
        markerOptions.position(SEOUL)
        markerOptions.title("서울")
        markerOptions.snippet("한국의 수도")

        mMap!!.addMarker(markerOptions)
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(SEOUL))
        mMap!!.animateCamera(CameraUpdateFactory.zoomTo(10f))
        ToastMaker.makeLongToast(this, "MyLocation button clicked")
        return false
    }

    override fun onMyLocationClick(location: Location) {

        ToastMaker.makeLongToast(this, "Current location : " + location)

    }

    override fun init() {
        planner_add_one_next_btn.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v!!) {
            planner_add_one_next_btn -> {
                startActivity(Intent(this, PlannerAddFourActivity::class.java))
            }
        }
    }


    override fun onMapReady(map: GoogleMap?) {

        mMap = map
        val SEOUL = LatLng(37.56, 126.97)

        val markerOptions = MarkerOptions()
        markerOptions.position(SEOUL)
        markerOptions.title("서울")
        markerOptions.snippet("한국의 수도")

        mMap!!.addMarker(markerOptions)
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(SEOUL))
        mMap!!.animateCamera(CameraUpdateFactory.zoomTo(10f))
        try {
            mMap!!.setMyLocationEnabled(true)
            mMap!!.isMyLocationEnabled = true
        }catch (e : Exception){
            e.printStackTrace()
        }

        mMap!!.setOnMyLocationButtonClickListener(this);
        mMap!!.setOnMyLocationClickListener(this);


    }


    private var mMap: GoogleMap? = null
    val PermissionRequestCode = 11
    lateinit var fragmentManager: FragmentManager
    lateinit var managePermissions : ManagePermissions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planner_add_one)

        init()



        //허가 요청
        val list = listOf<String>(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
        )

        managePermissions = ManagePermissions(this, list, PermissionRequestCode)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            managePermissions.checkPermissions()
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)


    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        if (requestCode == PermissionRequestCode) {
            if (permissions.size == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mMap!!.isMyLocationEnabled = true
                mMap!!.setMyLocationEnabled(true)
            } else {
                // Permission was denied. Display an error message.
            }
        }


    }

}
