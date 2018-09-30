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
import com.hello.seoulnuri.view.login.LoginActivity

class SplashActivity : AppCompatActivity(), Init {
    override fun init() {
        SharedPreference.instance!!.load(this)
        //SharedPreference.instance!!.setPrefData("data", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJrYWthb19pZHgiOiI5MDg3OTE3NjYiLCJpYXQiOjE1MzgxMzAyNTh9.pddISypk5gFBFANJe3vItG4d7r3G-7Ctir3isdSbF_w")
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
        val intent = Intent(this, LoginActivity::class.java)

        handler.postDelayed({

            if (SharedPreference.instance!!.getPrefStringData("data")!!.isEmpty()) {
                Log.v("yong login", SharedPreference.instance!!.getPrefStringData("data"))
                startActivity(intent)
                finish()

            } else {
                Log.v("yong main", SharedPreference.instance!!.getPrefStringData("data"))
                startActivity(Intent(this, MainActivity::class.java))
                finish()

            }

        }, 3000)
    }

}
