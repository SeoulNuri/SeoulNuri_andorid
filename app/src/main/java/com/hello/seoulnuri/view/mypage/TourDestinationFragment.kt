package com.hello.seoulnuri.view.mypage

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hello.seoulnuri.R
import com.hello.seoulnuri.base.Init
import com.hello.seoulnuri.model.info.tour.InfoTourResponseData
import com.hello.seoulnuri.model.mypage.MypageBookmarkCourseResponse
import com.hello.seoulnuri.model.mypage.MypageBookmarkTourData
import com.hello.seoulnuri.model.mypage.MypageBookmarkTourResponse
import com.hello.seoulnuri.network.ApplicationController
import com.hello.seoulnuri.network.NetworkService
import com.hello.seoulnuri.utils.SharedPreference
import com.hello.seoulnuri.view.info.adapter.InfoTourAdapter
import com.hello.seoulnuri.view.info.tour.InfoTourDetailActivity
import com.hello.seoulnuri.view.mypage.adapter.MypageTourAdapter
import kotlinx.android.synthetic.main.fragment_tour_destination.*
import kotlinx.android.synthetic.main.fragment_tour_destination.view.*
import kotlinx.android.synthetic.main.fragment_tour_info.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Created by VictoryWoo
 */
class TourDestinationFragment: Fragment(), Init, View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!){
            v!!->{
                val index = mypage_destination_recyclerview.getChildAdapterPosition(v!!)
                val intent : Intent = Intent(context, InfoTourDetailActivity::class.java)
                SharedPreference.instance!!.setPrefData("tour_idx",mypage_tour_list[index].tour_idx)
                intent.putExtra("index",mypage_tour_list[index].tour_idx)
                startActivity(intent)
            }
        }
    }

    lateinit var networkService: NetworkService
    lateinit var mypage_tour_list: ArrayList<MypageBookmarkTourData>
    lateinit var mypage_tour_adpater: MypageTourAdapter

    override fun init() {
        networkService = ApplicationController.instance!!.networkService
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tour_destination, container, false)

        init()
        requestBookmarkDestination()

        val sb = SpannableStringBuilder()

        val str: String = "가보고 싶은 관광지를 \n즐겨찾기 해보세요."

        sb.append(str)
        sb.setSpan(StyleSpan(Typeface.BOLD), 13, 18, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        view.tourSpotText.text = sb

        return view
    }

    fun requestBookmarkDestination() {
        val response = networkService.getMypageBookmarkTour(SharedPreference.instance!!.getPrefStringData("data")!!)
        response.enqueue(object : Callback<MypageBookmarkTourResponse> {
            override fun onResponse(call: Call<MypageBookmarkTourResponse>, response: Response<MypageBookmarkTourResponse>) {
                if(response!!.code() == 200){
                    //Log.v("11599 : ",response!!.body()!!.data.size.toString())
                    mypage_tour_list = response!!.body()!!.data
                    println("12113 data size : ${response!!.body()!!.data.size}")
                    println("12113 data message : ${response!!.message()}")
                    println("12113 data  : ${response!!.body()!!.status}")
                    mypage_tour_adpater = MypageTourAdapter(context!!, infoList = mypage_tour_list)
                    mypage_tour_adpater.setOnItemClickListener(this@TourDestinationFragment)
                    mypage_destination_recyclerview.setHasFixedSize(true)
                    mypage_destination_recyclerview.layoutManager = GridLayoutManager(activity, 2)
                    mypage_destination_recyclerview.adapter = mypage_tour_adpater


                }else{
                    Log.v("11600 : ",response!!.message())
                }
            }

            override fun onFailure(call: Call<MypageBookmarkTourResponse>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }
}