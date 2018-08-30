package com.hello.seoulnuri.network

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by VictoryWoo
 */
class ApplicationController : Application(){
    lateinit var networkService: NetworkService
    private val baseUrl = "http://52.78.129.27:3001/"
    companion object {
        lateinit var instance : ApplicationController
    }

    override fun onCreate() {
        super.onCreate()
        buildNetwork()
    }

    fun buildNetwork(){

        val builder =  Retrofit.Builder()
        val retrofit = builder
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        networkService = retrofit.create(NetworkService::class.java)
    }
}
