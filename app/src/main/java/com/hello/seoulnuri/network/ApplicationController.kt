package com.hello.seoulnuri.network

import android.app.Application
import com.kakao.auth.KakaoSDK
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.app.Activity
import com.hello.seoulnuri.view.login.adpater.KaKaoSDKAdapter


/**
 * Created by VictoryWoo
 */
class ApplicationController : Application() {


    //private val instance: ApplicationController? = null

    //private val currentActivity: Activity? = null

    lateinit var networkService: NetworkService
    private val baseUrl = "http://52.78.129.27:3001/"

    companion object {
        var instance: ApplicationController? = null

        var globalApplicationContext: ApplicationController? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        globalApplicationContext = this
        KakaoSDK.init(KaKaoSDKAdapter())
        buildNetwork()
    }

    fun buildNetwork() {

        val builder = Retrofit.Builder()
        val retrofit = builder
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        networkService = retrofit.create(NetworkService::class.java)
    }




    /**
     * singleton 애플리케이션 객체를 얻는다.
     * @return singleton 애플리케이션 객체
     */


    /**
     * 애플리케이션 종료시 singleton 어플리케이션 객체 초기화한다.
     */
    override fun onTerminate() {
        super.onTerminate()
        instance = null
    }
}
