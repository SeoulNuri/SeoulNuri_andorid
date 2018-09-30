package com.hello.seoulnuri.view.planner

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.hello.seoulnuri.R
import com.hello.seoulnuri.base.Init
import com.hello.seoulnuri.model.PlannerPathData
import com.hello.seoulnuri.utils.SharedPreference
import com.hello.seoulnuri.utils.ToastMaker
import com.hello.seoulnuri.view.planner.adapter.PlannerPathAdapter
import kotlinx.android.synthetic.main.activity_planner_add_path_check.*
import kotlinx.android.synthetic.main.sliding_layout.*
import java.util.*

class PlannerAddPathCheckActivity : AppCompatActivity(), OnMapReadyCallback, Init, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener, View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!) {
            planner_path_complete_btn -> {
                var intent = Intent(this, PlannerAddFourActivity::class.java)
                intent.putIntegerArrayListExtra("tourIdxArr", tourIdxArr)


                intent.putExtra("firstLocation", tourNameArr.get(0))
                intent.putExtra("lastLocation", tourNameArr.get(tourNameArr.size - 1))
                startActivity(intent)
                finish()

            }
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        pathGoogleMap = map

        //pathGoogleMap!!.isMyLocationEnabled = true
        //mapFragment!!.getMapAsync(this)
        val SEOUL = LatLng(37.56, 126.97)

        val markerOptions = MarkerOptions()
        markerOptions.position(SEOUL)
        markerOptions.title("서울")
        markerOptions.snippet("한국의 수도")

        pathGoogleMap!!.addMarker(markerOptions)
        pathGoogleMap!!.moveCamera(CameraUpdateFactory.newLatLng(SEOUL))
        pathGoogleMap!!.animateCamera(CameraUpdateFactory.zoomTo(10f))

        pathGoogleMap!!.setOnMyLocationButtonClickListener(this)
        pathGoogleMap!!.setOnMyLocationClickListener(this)

    }

    override fun init() {
        SharedPreference.instance!!.load(this)
        pathMapFragment = supportFragmentManager.findFragmentById(R.id.planner_add_path_map) as SupportMapFragment
        planner_path_complete_btn.setOnClickListener(this)
        sliding_plan_date.text = SharedPreference.instance!!.getPrefStringData("plan_date")

    }

    override fun onMyLocationButtonClick(): Boolean {
        val SEOUL = LatLng(37.27, 126.24)
        val markerOptions = MarkerOptions()
        markerOptions.position(SEOUL)
        markerOptions.title("인천")
        markerOptions.snippet("내가 사는 곳")

        pathGoogleMap!!.addMarker(markerOptions)
        pathGoogleMap!!.moveCamera(CameraUpdateFactory.newLatLng(SEOUL))
        pathGoogleMap!!.animateCamera(CameraUpdateFactory.zoomTo(10f))
        ToastMaker.makeLongToast(this, "MyLocation button clicked")
        return false

    }

    override fun onMyLocationClick(p0: Location) {

    }

    lateinit var plannerPathAdapter: PlannerPathAdapter
    lateinit var item_list: ArrayList<PlannerPathData>
    lateinit var pathMapFragment: SupportMapFragment
    private var pathGoogleMap: GoogleMap? = null

    lateinit var tourIdxArr: ArrayList<Int>
    lateinit var tourNameArr: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planner_add_path_check)
        init()

        tourIdxArr = ArrayList()
        tourNameArr = ArrayList()

        tourNameArr = intent.getStringArrayListExtra("tourNameArr")
        tourIdxArr = intent.getIntegerArrayListExtra("tourIdxArr")

        item_list = ArrayList()

        for (i in 0..tourIdxArr.size - 1) {

            item_list.add(PlannerPathData("", i + 1, tourNameArr[i], "", 3, 0))
        }


//        item_list.add(PlannerPathData("5분", 1, "경복궁"
//                , "서울 종로구 사직로 161 경복궁", 1, 21))
//        item_list.add(PlannerPathData("13분", 2, "광화문"
//                , "서울 종로구 사직로 161", 1, 11))
//        item_list.add(PlannerPathData("20분", 3, "북촌문화센터"
//                , "서울 종로구 계동길 37", 1, 40))
//        item_list.add(PlannerPathData("5분", 4, "갤러리룩스"
//                , "서울 종로구 필운대로7길 12", 1, 25))
//        item_list.add(PlannerPathData("5분", 4, "갤러리룩스"
//                , "서울 종로구 필운대로7길 12", 1, 25))
//        item_list.add(PlannerPathData("5분", 4, "갤러리룩스"
//                , "서울 종로구 필운대로7길 12", 1, 25))
//        item_list.add(PlannerPathData("5분", 4, "갤러리룩스"
//                , "서울 종로구 필운대로7길 12", 1, 25))
//        item_list.add(PlannerPathData("5분", 4, "갤러리룩스"
//                , "서울 종로구 필운대로7길 12", 1, 25))
//        item_list.add(PlannerPathData("5분", 4, "갤러리룩스"
//                , "서울 종로구 필운대로7길 12", 1, 25))

        planner_nestedScroll.scrollTo(0, 0)

        plannerPathAdapter = PlannerPathAdapter(item_list)
        plannerPathRecyclerview.layoutManager = LinearLayoutManager(this)
        plannerPathRecyclerview.adapter = plannerPathAdapter
        plannerPathRecyclerview.isNestedScrollingEnabled = false

        planner_path_bottom_layout.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event!!.action) {
                    MotionEvent.ACTION_MOVE -> {

                    }
                    MotionEvent.ACTION_DOWN -> {
                        planner_nestedScroll.scrollTo(0, 0)
                    }
                }
                return true
            }
        })

        Log.v("943", slidingLayout.overScrollMode.toString())
        Log.v("944", slidingLayout.isOverlayed.toString())

        planner_path_bottom_layout.overScrollMode



        if (slidingLayout.overScrollMode != 1) {
            Log.v("945", slidingLayout.overScrollMode.toString())
            planner_add_path_bar_image.visibility = View.GONE
            planner_path_down_btn.visibility = View.VISIBLE
        } else {
            Log.v("946", slidingLayout.overScrollMode.toString())
            planner_add_path_bar_image.visibility = View.VISIBLE
            planner_path_down_btn.visibility = View.GONE
        }
        Log.v("947", slidingLayout.overScrollMode.toString())
    }

}
