//package com.hello.seoulnuri.view.course
//
//import android.app.Activity
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
//import com.fasterxml.jackson.module.kotlin.readValue
//import com.google.gson.Gson
//import com.hello.seoulnuri.model.course.CourseStarData
//import com.hello.seoulnuri.model.course.CourseStarResponse
//import com.hello.seoulnuri.model.login.LoginUserResponse
//import com.hello.seoulnuri.network.ApplicationController
//import com.hello.seoulnuri.network.NetworkService
//import com.hello.seoulnuri.utils.SharedPreference
//import org.json.JSONObject
//import retrofit2.Call
//import retrofit2.Response
//import javax.security.auth.callback.Callback
//import com.google.gson.reflect.TypeToken
//import com.fasterxml.jackson.databind.ObjectMapper
//import com.google.gson.JsonElement
//import com.google.gson.JsonObject
//import com.google.gson.JsonParser
//import com.hello.seoulnuri.model.CourseItem
//import com.hello.seoulnuri.view.login.LoginCategoryActivity
//
///**
// * Created by shineeseo on 2018. 9. 12..
// */
//public class CourseGetDataActivity : Activity() {
//
//    lateinit var courseStarData : CourseStarData //cour_star, cour_star_count 객체
//    var map : Map<String, Map<String,CourseStarData>> = HashMap<String, Map<String,CourseStarData>>()
//    lateinit var networkService : NetworkService
//    var courseStarDataMap : Map<String, CourseStarData> = HashMap<String, CourseStarData>()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        SharedPreference.instance!!.load(this)
//        courseGetStarData();
//    }
//
//    fun courseGetStarData(){
//        networkService = ApplicationController.instance!!.networkService
//        Log.v("course", "course_star_data");
////        courseStarData = CourseStarData(cour_star, cour_star_count )
////        var courseStarResponse = networkService.getCourseStar(SharedPreference.instance!!.getPrefStringData("data")!!)
//        Log.v("course_2", "course_star_response")
//        courseStarResponse.enqueue(object : retrofit2.Callback<CourseStarResponse> {
//            override fun onFailure(call: Call<CourseStarResponse>?, t: Throwable?) {
//                Log.v("get course star info fail",t!!.message)
//            }
//
//            override fun onResponse(call: Call<CourseStarResponse>?, response: Response<CourseStarResponse>?) {
//                if(response!!.isSuccessful){
//                    Log.v("course_3","course_3")
//                    Log.v("course_4",response!!.code().toString())
//                    Log.v("course code",response!!.body()!!.code!!.toString())
//                    Log.v("course message",response!!.body()!!.message!!.toString())
//                    Log.v("course statue",response!!.body()!!.status!!.toString())
////                    Log.v("response body",response!!.body()!!.toString())
////                    var map: Map<String, CourseStarData> = HashMap<String, CourseStarData>()
//                    map = response!!.body()!!.data!!
//
//                    var i = 0;
//
//                    val item = arrayOfNulls<CourseItem>(4)
//
//                    for (keys in map.entries) {
//                        Log.v("successss", "keys = " + keys.key)
//                        Log.v("map value", "values = " + keys.value)
//                        courseStarDataMap = keys.value; //Map<String, CourstarData
////                        courseStarValues.set(i, keys.value[])
//                    }
//
//                    Log.v("map to String", map.toString())
//
//
//                } else{
//                    Log.v("course else",response!!.code().toString())
//                }
//            }
//
//        })
//
//
//    }
//
//}