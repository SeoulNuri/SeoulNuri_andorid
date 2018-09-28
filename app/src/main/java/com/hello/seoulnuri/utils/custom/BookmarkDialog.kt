package com.hello.seoulnuri.utils.custom

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import com.hello.seoulnuri.R
import com.hello.seoulnuri.base.BaseModel
import com.hello.seoulnuri.base.Init
import com.hello.seoulnuri.model.info.tour.bookmark.TourBookmarkResponse
import com.hello.seoulnuri.model.info.tour.bookmark.TourIndex
import com.hello.seoulnuri.network.ApplicationController
import com.hello.seoulnuri.network.NetworkService
import com.hello.seoulnuri.utils.SharedPreference
import com.hello.seoulnuri.view.info.tour.InfoTourDetailActivity
import kotlinx.android.synthetic.main.activity_info_tour_detail.*
import kotlinx.android.synthetic.main.course_bookmark_dialog.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.hello.seoulnuri.view.main.MainActivity



/**
 * Created by VictoryWoo
 */
class BookmarkDialog(context: Context) : Dialog(context), View.OnClickListener, Init {
    override fun init() {
        btn_bookmark_ok.setOnClickListener(this)
        SharedPreference.instance!!.load(context!!)
        networkService = ApplicationController.instance!!.networkService
        bContext = context

    }

    lateinit var bContext : Context
    override fun onClick(v: View?) {
        when (v!!) {
            btn_bookmark_ok -> {
                Log.v("446 woo click", "눌림??")
                requestTourBookmark()

            }
        }
    }


    // 북마크 통신됨! 서버에 값이 없는듯??ㅠㅠㅠ
    fun requestTourBookmark() {
        tourIndex = TourIndex(SharedPreference.instance!!.getPrefIntegerData("tour_idx"))
        Log.v("woo tour_idx", SharedPreference.instance!!.getPrefIntegerData("tour_idx").toString())
        val responseBookmark = networkService
                .postBookmark(SharedPreference.instance!!.getPrefStringData("data")!!, tourIndex)
        responseBookmark.enqueue(object : Callback<TourBookmarkResponse> {
            override fun onFailure(call: Call<TourBookmarkResponse>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<TourBookmarkResponse>?, response: Response<TourBookmarkResponse>?) {
                if (response!!.isSuccessful) {
                    Log.v("446 woo bookmark", response!!.body()!!.message)
                    //(ownerActivity as InfoTourDetailActivity).changeImageButton()
                    SharedPreference.instance!!.setPrefData("tour_booked",response!!.body()!!.data.isBooked)
                    dismiss() // 혹은 여기서 통신 진행하거나
                } else {
                    Log.v("446 woo bookmark else", response!!.message())
                }
            }
        })

    }

    lateinit var networkService: NetworkService
    lateinit var tourIndex: TourIndex
    //lateinit var activity: Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT)
        init()


    }

    companion object {
        val LAYOUT = R.layout.course_bookmark_dialog
    }
}