package com.hello.seoulnuri.view.info.tour

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hello.seoulnuri.R
import com.hello.seoulnuri.base.Init
import com.hello.seoulnuri.model.info.tour.use.InfoTourUseReponse
import com.hello.seoulnuri.network.ApplicationController
import com.hello.seoulnuri.network.NetworkService
import com.hello.seoulnuri.utils.SharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by VictoryWoo
 */
class InfoTourUseFragment: Fragment(), Init{
    override fun init() {
        networkService = ApplicationController.instance!!.networkService
        SharedPreference.instance!!.load(context!!)
    }

    lateinit var networkService: NetworkService
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_info_tour_use, container, false)
        init()

        requestInfoUseData()
        return view

    }

    fun requestInfoUseData(){
        val responeUseData = networkService
                .getInfoTourUseMethod(SharedPreference.instance!!.getPrefStringData("data")!!
                        ,SharedPreference.instance!!.getPrefIntegerData("tour_idx"))
        responeUseData.enqueue(object : Callback<InfoTourUseReponse>{
            override fun onFailure(call: Call<InfoTourUseReponse>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<InfoTourUseReponse>?, response: Response<InfoTourUseReponse>?) {
                if(response!!.isSuccessful){
                    Log.v("woo 1202",response!!.body()!!.code.toString())
                }
            }

        })
    }
}