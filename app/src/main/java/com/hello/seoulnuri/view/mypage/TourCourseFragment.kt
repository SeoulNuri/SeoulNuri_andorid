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
import com.hello.seoulnuri.network.ApplicationController
import com.hello.seoulnuri.network.NetworkService
import com.hello.seoulnuri.utils.SharedPreference
import com.hello.seoulnuri.view.info.adapter.InfoTourAdapter
import com.hello.seoulnuri.view.info.tour.InfoTourDetailActivity
import kotlinx.android.synthetic.main.fragment_info_tour.*
import kotlinx.android.synthetic.main.fragment_tour_info.*
import kotlinx.android.synthetic.main.fragment_tour_info.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Created by VictoryWoo
**/
open class TourCourseFragment : Fragment(), Init, View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!){
            v!!->{
                val index = mypage_tour_recyclerview.getChildAdapterPosition(v!!)
                val intent : Intent = Intent(context, InfoTourDetailActivity::class.java)
                SharedPreference.instance!!.setPrefData("tour_idx",info_tour_list[index].tour_idx)
                intent.putExtra("index",info_tour_list[index].tour_idx)
                startActivity(intent)
            }
        }
    }

    lateinit var networkService: NetworkService
    lateinit var info_tour_list: ArrayList<InfoTourResponseData>
    lateinit var info_tour_adpater: InfoTourAdapter

    override fun init() {
        networkService = ApplicationController.instance!!.networkService
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tour_info, container, false)

        init()
        requestBookmarkCourse()

        val sb = SpannableStringBuilder()

        val str: String = "가보고 싶은 관광코스를 \n즐겨찾기 해보세요."

        sb.append(str)
        sb.setSpan(StyleSpan(Typeface.BOLD), 13, 18, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        view.tourCourseText.text = sb

        return view
    }

    fun requestBookmarkCourse() {
        val response = networkService.getMypageBookmarkCourse(SharedPreference.instance!!.getPrefStringData("data")!!)
        response.enqueue(object : Callback<MypageBookmarkCourseResponse> {
            override fun onResponse(call: Call<MypageBookmarkCourseResponse>, response: Response<MypageBookmarkCourseResponse>) {
                if(response!!.code() == 200){
                    //Log.v("11599 : ",response!!.body()!!.data.size.toString())
                    info_tour_list = response!!.body()!!.data
                    println("12112 data size : ${response!!.body()!!.data.size}")
                    println("12112 data message : ${response!!.message()}")
                    println("12112 data  : ${response!!.body()!!.status}")
                    info_tour_adpater = InfoTourAdapter(context!!, infoList = info_tour_list)
                    info_tour_adpater.setOnItemClickListener(this@TourCourseFragment)
                    mypage_tour_recyclerview.setHasFixedSize(true)
                    mypage_tour_recyclerview.layoutManager = GridLayoutManager(activity, 2)
                    mypage_tour_recyclerview.adapter = info_tour_adpater


                }else{
                    Log.v("11600 : ",response!!.message())
                }
            }

            override fun onFailure(call: Call<MypageBookmarkCourseResponse>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }
}