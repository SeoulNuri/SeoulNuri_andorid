package com.hello.seoulnuri.view.splash

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.util.Log
import com.hello.seoulnuri.utils.SharedPreference
import com.hello.seoulnuri.view.main.MainActivity
import com.hello.seoulnuri.R
import com.hello.seoulnuri.base.Init

class SplashActivity : AppCompatActivity(), Init {
    override fun init() {
        SharedPreference.instance!!.load(this)
        SharedPreference.instance!!.setPrefData("data", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJrYWthb19pZHgiOiI5MjUzMjIyNjciLCJpYXQiOjE1Mzc5MzU0NTd9.3yDW2HD8IwOPy17TfQ3xeW-xhL07WyUVxSSvh9wI0BU")
        //Log.v("613 woo", SharedPreference.instance!!.getPrefStringData("data"))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        init()

/*
>>>>>>> 76766df9fdc4384d08b65988a6a0e426fd90fa67
        try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }*/


        val handler = Handler()
        val intent = Intent(this, MainActivity::class.java)

        handler.postDelayed({


            startActivity(intent)
            finish()
            if (SharedPreference.instance!!.getPrefStringData("data")!!.isEmpty()) {

                startActivity(intent)
                finish()

            } else {
                Log.v("yong", SharedPreference.instance!!.getPrefStringData("data"))
                startActivity(Intent(this, MainActivity::class.java))

            }

        }, 3000)
    }

}
