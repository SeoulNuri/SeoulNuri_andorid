package com.hello.seoulnuri.planner

import android.Manifest
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.hello.seoulnuri.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.SupportMapFragment
import com.hello.seoulnuri.base.Init
import kotlinx.android.synthetic.main.activity_planner_add_one.*
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.location.Location
import android.support.v4.content.ContextCompat
import com.hello.seoulnuri.utils.ToastMaker
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.BitmapDescriptor




class PlannerAddOneActivity : AppCompatActivity(), OnMapReadyCallback, View.OnClickListener, Init, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener {
    override fun onMyLocationButtonClick(): Boolean {
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        val SEOUL = LatLng(37.27, 126.24)
        val markerOptions = MarkerOptions()
        markerOptions.position(SEOUL)
        markerOptions.title("인천")
        markerOptions.snippet("내가 사는 곳")

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

        //허가 요청
        list = listOf<String>(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)

    }

    override fun onClick(v: View?) {
        when (v!!) {
            planner_add_one_next_btn -> {
                startActivity(Intent(this, PlannerAddPathCheckActivity::class.java))
                finish()
            }
        }
    }


    override fun onMapReady(map: GoogleMap?) {

        /*FIXME
        * 두 개의 권한을 받아왔으면
        * 지도에 현재 위치 버튼 표시하고, 설정된 위도, 경도로 지도 상에 주소 표시
        * 그리고 혀재 위치 버튼 활성화하고, 마커 찍어 놓기
        * */
        mMap = map
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mMap!!.isMyLocationEnabled = true
            //mapFragment!!.getMapAsync(this)
            val SEOUL = LatLng(37.56, 126.97)
            var INCHEON = LatLng(37.01, 121.1)



            val icon = BitmapDescriptorFactory.fromResource(R.drawable.button_spot_select)
            val sydney = LatLng(-33.852, 151.211)


            // for loop를 통한 n개의 마커 생성
            for (idx in 0..9) {
                // 1. 마커 옵션 설정 (만드는 과정)
                val makerOptions2 = MarkerOptions()
                makerOptions2 // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
                        .position(LatLng(37.52487 + (idx*100), 126.92723))
                        .title("마커$idx") // 타이틀.

                // 2. 마커 생성 (마커를 나타냄)
                mMap!!.addMarker(makerOptions2)
            }

            val markerOptions = MarkerOptions()
            markerOptions.position(SEOUL).title("서울").icon(icon)
            markerOptions.title("서울")
            markerOptions.snippet("한국의 수도")

            mMap!!.addMarker(markerOptions)
            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(SEOUL))
            mMap!!.animateCamera(CameraUpdateFactory.zoomTo(10f))

            mMap!!.setOnMyLocationButtonClickListener(this)
            mMap!!.setOnMyLocationClickListener(this)

        }


    }


    private var mMap: GoogleMap? = null
    val PERMISSION_REQUST_CODE: Int = 100
    lateinit var mapFragment: SupportMapFragment
    lateinit var list: List<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planner_add_one)

        init()
        /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
             //managePermissions.checkPermissions()

         }*/


        // 권한을 요청하기
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUST_CODE)
        //managePermissions = ManagePermissions(this, list, PermissionRequestCode)

        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment


    }


    // 요청하고 결과 받아서 처리
    /*FIXME
    * 권한 요청하고 받은 결과를 처리하는 부분
    * 아래에서 결과를 받고 requestCode가 PERMISSION_REQUST_CODE와 일치하고, PERMISSION_GRANTED한 상태이면
    * mapFragment!!.getMapAsync(this)을 통해서 mapFragment에 지도 보여주기
    * */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_REQUST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            mapFragment!!.getMapAsync(this)
        } else {
            // Permission was denied. Display an error message.
        }
    }


}
