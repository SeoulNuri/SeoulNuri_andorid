package com.hello.seoulnuri.view.info.tour

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.hello.seoulnuri.R
import com.hello.seoulnuri.base.Init
import com.hello.seoulnuri.network.ApplicationController
import com.hello.seoulnuri.network.NetworkService
import com.hello.seoulnuri.utils.SharedPreference
import com.hello.seoulnuri.utils.ToastMaker
import kotlinx.android.synthetic.main.activity_tour_map.*
import java.io.IOException
import java.util.*

class TourMapActivity : AppCompatActivity(), OnMapReadyCallback, Init, View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!) {
            show_tour_tour_info_btn -> {
                ToastMaker.makeShortToast(this, "준비 중입니다:)")
            }
        }
    }

    override fun init() {
        networkService = ApplicationController.instance!!.networkService
        SharedPreference.instance!!.load(this)
        show_tour_tour_info_btn.setOnClickListener(this)

        val intent = getIntent()
        tourName = intent.getStringExtra("tour_name")
        tourAddr = intent.getStringExtra("tour_addr")
        tourStar = intent.getIntExtra("tour_star", 0)
        tourStarCount = intent.getIntExtra("tour_star_count", 0)
        place = tourName

    }

    fun settingData() {
        tour_item_map_title.text = tourName
        tour_item_map_addr.text = tourAddr
        tour_item_map_rating_star.rating = tourStar.toFloat()
        tour_item_map_rating_txt.text = tourStarCount.toString()
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


            markerOptions
                    .position(location!!)
                    .title(place)
                    .snippet("검색한 곳")
                    .icon(main_icon)
        }
    }

    lateinit var networkService: NetworkService
    var tourName = ""
    var tourAddr = ""
    var tourStar: Int = 0
    var tourStarCount: Int = 0
    var mCoder: Geocoder = Geocoder(this, Locale.KOREAN)
    var place: String? = null
    var location : LatLng? = null
    private var mMap: GoogleMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tour_map)

        init()
        settingData()

        location = findAddressLocation(place!!)

    }

    fun findAddressLocation(spot : String): LatLng { //입력된 스트링의 주소를 검색하고 그 결과를 위도경도로 반환
        var addr: List<Address>? = null
        var loc: LatLng? = null

        try {
            addr = mCoder.getFromLocationName(spot, 5)
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
