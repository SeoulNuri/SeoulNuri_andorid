package com.hello.seoulnuri.view.planner

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.hello.seoulnuri.R
import com.hello.seoulnuri.base.Init
import com.hello.seoulnuri.model.planner.PlannerAddRequest
import com.hello.seoulnuri.model.planner.PlannerAddResponse
import com.hello.seoulnuri.network.ApplicationController
import com.hello.seoulnuri.network.NetworkService
import com.hello.seoulnuri.utils.SharedPreference
import kotlinx.android.synthetic.main.activity_planner_add_four.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class PlannerAddFourActivity : AppCompatActivity(), Init, View.OnClickListener {

    lateinit var networkService: NetworkService

    override fun onClick(v: View?) {
        when (v!!) {
            planner_add_four_finish_btn -> {
                var plan_date = SharedPreference.instance!!.getPrefStringData("plan_date","")!!
                var list = ArrayList<Int>()
                list.add(1)
                list.add(2)
                addPlanner(plan_date,list)
                addPlanner("2018-07-11",list)

                finish()
            }
        }
    }

    override fun init() {
        planner_add_four_finish_btn.setOnClickListener(this)
        networkService = ApplicationController.instance!!.networkService
        SharedPreference.instance!!.load(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planner_add_four)
        init()

    }

    fun addPlanner(date: String, indexlist: ArrayList<Int>){


        var token = SharedPreference.instance!!.getPrefStringData("data","")!!
        var plannerAddRequest = PlannerAddRequest(date,indexlist);
        var plannerAddResponse = networkService.addPlanner(token,plannerAddRequest)


        plannerAddResponse.enqueue(object : Callback<PlannerAddResponse> {
            override fun onFailure(call: Call<PlannerAddResponse>?, t: Throwable?) {
                Log.v("yong",t!!.message)
            }

            override fun onResponse(call: Call<PlannerAddResponse>?, response: Response<PlannerAddResponse>?) {
                if(response!!.isSuccessful){

                    Log.v("yong","플래너 추가 성공")

                } else{

                    Log.v("yong","response is not successful")

                }
            }

        })

    }
}
