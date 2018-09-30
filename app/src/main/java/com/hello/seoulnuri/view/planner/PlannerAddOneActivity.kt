package com.hello.seoulnuri.view.planner

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.hello.seoulnuri.R
import com.hello.seoulnuri.base.Init
import com.hello.seoulnuri.model.MarkerData
import com.hello.seoulnuri.model.planner.PlannerArroundData
import com.hello.seoulnuri.model.planner.PlannerArroundResponse
import com.hello.seoulnuri.model.planner.PlannerImageResponse
import com.hello.seoulnuri.network.ApplicationController
import com.hello.seoulnuri.network.NetworkService
import com.hello.seoulnuri.utils.SharedPreference
import com.hello.seoulnuri.utils.ToastMaker
import kotlinx.android.synthetic.main.activity_planner_add_one.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*


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

        locations = ArrayList()
        locations.add(SEOUL!!)
        locations.add(Gyeonghui_Palace)
        locations.add(Chungjeongno_Station)
        locations.add(City_Hall_Station)



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


            val main_icon = BitmapDescriptorFactory.fromResource(R.drawable.button_spot_select)
            val sub_icon = BitmapDescriptorFactory.fromResource(R.drawable.button_spot)


            var list : ArrayList<Marker> = ArrayList()

            val markerOptions = MarkerOptions()

            markerArray = ArrayList()


            var token = SharedPreference.instance!!.getPrefStringData("data","")!!
            var plannerArroundResponse = networkService.getPlannerArround(token,SharedPreference.instance!!.getPrefIntegerData("search_tour_idx",10));


            plannerArroundResponse.enqueue(object : Callback<PlannerArroundResponse> {
                override fun onFailure(call: Call<PlannerArroundResponse>?, t: Throwable?) {
//                Log.v("failure ",t!!.message)
                }

                override fun onResponse(call: Call<PlannerArroundResponse>?, response: Response<PlannerArroundResponse>?) {
                    if(response!!.isSuccessful){
                        Log.v("yong","planner arround 가져오기 성공")

                        markerItems = ArrayList()
                        markerItems = response.body()!!.data

                        Log.v("yong",markerItems.toString())
                    for(i in 0..markerItems.size-1){
                        var marker = mMap!!.addMarker(MarkerOptions()
                                .position(LatLng(markerItems[i].tour_latitude,markerItems[i].tour_longitude))
                                .title(markerItems[i].tour_name)
                                .snippet("")
                                .icon(sub_icon))
                        markerArray.add(marker)
                        list.add(marker)
                    }

                    } else{
                        Log.v("yong","else")

                    }
                }

            })

            markerOptions
                    .position(SEOUL!!)
                    .title(place)
                    .snippet("여기")
                    .icon(main_icon)

            marker_seoul = mMap!!.addMarker(markerOptions)
            list.add(marker_seoul)



            for (i in 0..list.size-1){
                Log.v("Marker : ",list[i].title)
            }

            //createMarker(markerItems)

            mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL,12f))
            mMap!!.animateCamera(CameraUpdateFactory.zoomTo(12f))



            mMap!!.setOnMyLocationButtonClickListener(this)
            mMap!!.setOnMyLocationClickListener(this)

        }
    }

    fun createMarker(items : ArrayList<MarkerData>){
        var markerList = ArrayList<Marker>(items.size)
        markerOptions = ArrayList()
        for (position in 0..items.size-1){

            markerOptions[position]
                    .position(LatLng(items[position].latitude, items[position].longitude))
                    .title(items[position].title)
                    .snippet(items[position].snippet)
                    .icon(BitmapDescriptorFactory.fromResource(items[position].iconResID))
            markerList[position] = mMap!!.addMarker(markerOptions[position])

            for (i in 0..markerList.size-1){
                Log.v("Marker : ",markerList[i].title)
            }
        }


    }

    private var mMap: GoogleMap? = null
    val PERMISSION_REQUST_CODE: Int = 100
    lateinit var mapFragment: SupportMapFragment
    lateinit var list: List<String>



    var mCoder:Geocoder = Geocoder(this,Locale.KOREAN)
    var place: String = ""

    var SEOUL: LatLng? = null
    val Gyeonghui_Palace: LatLng = LatLng(37.570369, 126.969009)
    val Chungjeongno_Station: LatLng = LatLng(37.560055,126.96367199999997)
    val City_Hall_Station: LatLng = LatLng(37.5658049,126.97514610000007)
    lateinit var locations : ArrayList<LatLng>

    lateinit var marker_seoul : Marker
    lateinit var marker_Gyeonghui_Palace : Marker
    lateinit var marker_Horyu_Station : Marker
    lateinit var marker_Hyehwa_Station : Marker


    lateinit var markerArray: ArrayList<Marker>
    lateinit var markerItems : ArrayList<PlannerArroundData>
    lateinit var markerOptions : ArrayList<MarkerOptions>

    lateinit var networkService: NetworkService




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planner_add_one)

        SharedPreference.instance!!.load(this)
        networkService = ApplicationController.instance!!.networkService

        getPlannerImage()

        place = intent.getStringExtra("place")
        SEOUL = findAddressLocation()
        findAddressText(SEOUL!!)

        getPlannerArround()
        init()

        /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
             //managePermissions.checkPermissions()

         }*/



        // 권한을 요청하기
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUST_CODE)
        //managePermissions = ManagePermissions(this, list, PermissionRequestCode)

        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

//        markerItems = ArrayList()
//        markerItems.add(MarkerData(37.56,126.97,"SEOUL","한국의 수도",R.drawable.button_spot_select))
//        markerItems.add(MarkerData(37.570369,126.969009,"Gyeonghui_Palace","경희궁",R.drawable.button_spot))
//        markerItems.add(MarkerData(37.558553,126.978218,"Horyu_Station","회현역",R.drawable.button_spot))
//        markerItems.add(MarkerData(37.582163,127.001978,"Hyehwa_Station","혜화역",R.drawable.button_spot))


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


    fun findAddressLocation(): LatLng { //입력된 스트링의 주소를 검색하고 그 결과를 위도경도로 반환
        var addr: List<Address>? = null
        var loc: LatLng? = null

        try {
            addr = mCoder.getFromLocationName(place, 5)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (addr != null) { //address형태로
            for (i in addr.indices) {
                val lating = addr[i]
                val lat = lating.getLatitude()
                val lon = lating.getLongitude()
                loc = LatLng(lat, lon)
            }
        }
        else{
            Toast.makeText(this,"해당되는 주소 정보가 없습니다. 다시 입력하세요.",Toast.LENGTH_SHORT).show()
        }
        return loc!!
    }

    fun findAddressText(dragPosition: LatLng) { //위도경도를 주소로 변환
        var list: List<Address>? = null
        Log.v("yong","findAddress")
        try {
            val d1 = dragPosition.latitude
            val d2 = dragPosition.longitude

            list = mCoder.getFromLocation(
                    d1,
                    d2,
                    10)

            Log.v("yong","findAddress2")

            if(list!=null){
                Log.v("yong",list.toString())
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }



    }

    fun getPlannerImage(){
        var token = SharedPreference.instance!!.getPrefStringData("data","")!!
        var plannerImageResponse = networkService.getPlannerImage(token,10);


        plannerImageResponse.enqueue(object : Callback<PlannerImageResponse> {
            override fun onFailure(call: Call<PlannerImageResponse>?, t: Throwable?) {
//                Log.v("failure ",t!!.message)
            }

            override fun onResponse(call: Call<PlannerImageResponse>?, response: Response<PlannerImageResponse>?) {
                if(response!!.isSuccessful){
                    Log.v("yong","planner image 가져오기 성공")
                    Log.v("yong",response.body()!!.data.tour_planner_img)

                    var imageUrl = response.body()!!.data.tour_planner_img
                    if(!imageUrl.equals("없음")){
                        Glide.with(applicationContext).load(imageUrl).into(planner_add_one_image)
                    }



                } else{
                    Log.v("yong","else")

                }
            }

        })
    }

    fun getPlannerArround(){

    }




}
