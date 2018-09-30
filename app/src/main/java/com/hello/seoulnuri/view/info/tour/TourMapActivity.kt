package com.hello.seoulnuri.view.info.tour

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
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

import com.google.android.gms.maps.model.MarkerOptions
import com.hello.seoulnuri.R
import com.hello.seoulnuri.base.Init
import com.hello.seoulnuri.model.planner.PlannerImageResponse
import com.hello.seoulnuri.network.ApplicationController
import com.hello.seoulnuri.network.NetworkService
import com.hello.seoulnuri.utils.SharedPreference
import com.hello.seoulnuri.utils.ToastMaker
import kotlinx.android.synthetic.main.activity_tour_map.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*

class TourMapActivity : AppCompatActivity(), OnMapReadyCallback, Init, View.OnClickListener, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener {
    override fun onMyLocationButtonClick(): Boolean {

        val markerOptions = MarkerOptions()
        markerOptions.position(Gyeonghui_Palace)
        markerOptions.title("인천")
        markerOptions.snippet("내가 사는 곳")

        mMap!!.addMarker(markerOptions)
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(location!!))
        mMap!!.animateCamera(CameraUpdateFactory.zoomTo(15f))
        ToastMaker.makeLongToast(this, "MyLocation button clicked")
        return false
    }

    override fun onMyLocationClick(p0: Location) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onClick(v: View?) {
        when (v!!) {
            show_tour_tour_info_btn -> {
                ToastMaker.makeShortToast(this, "준비 중입니다:)")
            }
        }
    }

    override fun init() {
        Log.v("520 11", "init")
        networkService = ApplicationController.instance!!.networkService
        SharedPreference.instance!!.load(this)
        show_tour_tour_info_btn.setOnClickListener(this)
        list = listOf<String>(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
        Log.v("520 22", "init")


    }

    fun settingData() {
        Log.v("520 33", "settingData")
        val intent = getIntent()
        tourIndex = intent.getIntExtra("tour_idx",0)
        tourName = intent.getStringExtra("tour_name")
        tourAddr = intent.getStringExtra("tour_addr")
        tourStar = intent.getIntExtra("tour_star", 0)
        tourStarCount = intent.getIntExtra("tour_star_count", 0)
        place = tourName

        tour_item_map_title.text = tourName
        tour_item_map_addr.text = tourAddr
        tour_item_map_rating_star.rating = tourStar.toFloat()
        tour_item_map_rating_txt.text = tourStarCount.toString()
        Log.v("520 44", "settingData")
    }

    override fun onMapReady(map: GoogleMap?) {


        mMap = map
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mMap!!.isMyLocationEnabled = true
            //mapFragment!!.getMapAsync(this)

            val main_icon = BitmapDescriptorFactory.fromResource(R.drawable.button_spot_select)
            val sub_icon = BitmapDescriptorFactory.fromResource(R.drawable.button_spot)


            val markerOptions = MarkerOptions()


            Log.v("woo location",location!!.toString())
            markerOptions
                    .position(location!!)
                    .title(place)
                    .snippet("검색한 곳")
                    .icon(main_icon)

            mMap!!.addMarker(markerOptions)
            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(location!!))
            mMap!!.animateCamera(CameraUpdateFactory.zoomTo(15f))

            mMap!!.setOnMyLocationButtonClickListener(this)
            mMap!!.setOnMyLocationClickListener(this)
        }
    }

    lateinit var networkService: NetworkService
    var tourName = ""
    var tourAddr = ""
    var tourStar: Int = 0
    var tourStarCount: Int = 0
    var tourIndex = 0
    //var tour_map_coder: Geocoder = Geocoder(this, Locale.KOREAN)
    lateinit var tourMap_coder: Geocoder
    var place: String? = null
    val Gyeonghui_Palace: LatLng = LatLng(37.570369, 126.969009)
    var location: LatLng? = null
    private var mMap: GoogleMap? = null
    lateinit var mapFragment: SupportMapFragment
    val PERMISSION_REQUST_CODE: Int = 100
    lateinit var list: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tour_map)

        tourMap_coder = Geocoder(this, Locale.KOREAN)

        init()
        settingData()
        location = findAddressLocation(place!!)
        requestImage()


        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUST_CODE)
        mapFragment = supportFragmentManager.findFragmentById(R.id.tour_map) as SupportMapFragment


    }

    fun requestImage(){
        val response  = networkService
                .getImage(SharedPreference.instance!!.getPrefStringData("data")!!
                        , tourIndex)
        response.enqueue(object : Callback<PlannerImageResponse>{
            override fun onFailure(call: Call<PlannerImageResponse>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<PlannerImageResponse>?, response: Response<PlannerImageResponse>?) {
                if(response!!.isSuccessful){
                    Log.v("746 woo",response!!.body()!!.data.tour_idx.toString())
                    Log.v("746 woo",response!!.body()!!.data.tour_planner_img)
                    Glide.with(this@TourMapActivity).load(response!!.body()!!.data.tour_planner_img).into(tour_item_map_img)

                }
            }

        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_REQUST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            mapFragment!!.getMapAsync(this)
        } else {
            // Permission was denied. Display an error message.
        }
    }

    fun findAddressLocation(spot : String): LatLng { //입력된 스트링의 주소를 검색하고 그 결과를 위도경도로 반환
        var addr: List<Address>? = null
        var loc: LatLng? = null

        try {
            addr = tourMap_coder.getFromLocationName(spot, 5)
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
        } else {
            Toast.makeText(this, "해당되는 주소 정보가 없습니다. 다시 입력하세요.", Toast.LENGTH_SHORT).show()
        }
        return loc!!
    }
}
