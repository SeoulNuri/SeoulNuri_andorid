package com.hello.seoulnuri.view.course

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.hello.seoulnuri.model.course.CourseStarData
import com.hello.seoulnuri.model.course.CourseStarResponse
import com.hello.seoulnuri.model.login.LoginUserResponse
import com.hello.seoulnuri.network.ApplicationController
import com.hello.seoulnuri.network.NetworkService
import com.hello.seoulnuri.utils.SharedPreference
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback


/**
 * Created by shineeseo on 2018. 9. 12..
 */
class CourseGetDataActivity : Activity() {

    lateinit var courseStarData : CourseStarData //cour_star, cour_star_count 객체
    lateinit var networkService : NetworkService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreference.instance!!.load(this)

    }

    fun courseGetStarData() {
        networkService = ApplicationController.instance!!.networkService
        Log.v("course", "course_star_data");
//        courseStarData = CourseStarData(cour_star, cour_star_count )
        var courseStarResponse = networkService.getCourseStar()
        Log.v("course_2", "course_star_response")
        courseStarResponse.enqueue(object : retrofit2.Callback<CourseStarResponse> {
            override fun onFailure(call: Call<CourseStarResponse>?, t: Throwable?) {
                Log.v("get course star info fail",t!!.message)
            }

            override fun onResponse(call: Call<CourseStarResponse>?, response: Response<CourseStarResponse>?) {
                if(response!!.isSuccessful){
                    Log.v("course_3","course_3")
                    Log.v("course_4",response!!.code().toString())
                    Log.v("course code",response!!.body()!!.code!!.toString())
                    Log.v("course message",response!!.body()!!.message!!.toString())
                    Log.v("course statue",response!!.body()!!.status!!.toString())
                    println("course_5 success : ${response!!.body()!!.data}")
                    Log.v("course_5 success",response!!.body()!!.data!!.get(0).cour_star)
                } else{
                    Log.v("course else",response!!.code().toString())
                }
            }

        })

    }
}