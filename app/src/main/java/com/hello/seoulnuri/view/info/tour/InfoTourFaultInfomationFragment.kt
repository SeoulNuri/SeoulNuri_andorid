package com.hello.seoulnuri.view.info.tour

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hello.seoulnuri.R
import com.hello.seoulnuri.base.Init
import com.hello.seoulnuri.model.info.tour.fault.InfoTourFaultResponse
import com.hello.seoulnuri.network.ApplicationController
import com.hello.seoulnuri.utils.SharedPreference
import com.kakao.network.NetworkService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by VictoryWoo
 */
class InfoTourFaultInfomationFragment : Fragment(), Init{
    override fun init() {
        networkService = ApplicationController.instance!!.networkService
        SharedPreference.instance!!.load(context!!)

    }

    lateinit var networkService: com.hello.seoulnuri.network.NetworkService
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_info_tour_fault, container, false)
        init()

        requestInfoFaultData()
        return view
    }

    fun requestInfoFaultData(){
        val responseInfoFault = networkService
                .getInfoTourFault(SharedPreference.instance!!.getPrefStringData("data")!!
                        , SharedPreference.instance!!.getPrefIntegerData("tour_idx"))

        responseInfoFault.enqueue(object : Callback<InfoTourFaultResponse>{
            override fun onFailure(call: Call<InfoTourFaultResponse>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<InfoTourFaultResponse>?, response: Response<InfoTourFaultResponse>?) {
                if(response!!.isSuccessful){
                    Log.v("1224 woo",response!!.body()!!.data.tour_bottom.accessibility.visual)
                }
            }

        })
    }
}