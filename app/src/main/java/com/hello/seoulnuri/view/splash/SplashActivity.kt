package com.hello.seoulnuri.view.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.hello.seoulnuri.R
import com.hello.seoulnuri.base.Init
import com.hello.seoulnuri.utils.SharedPreference
import com.hello.seoulnuri.view.login.LoginActivity
import com.hello.seoulnuri.view.main.MainActivity

class SplashActivity : AppCompatActivity(), Init {
    override fun init() {
        SharedPreference.instance!!.load(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        init()
/*
        try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
*/

        val handler = Handler()
        val intent = Intent(this, LoginActivity::class.java)

        handler.postDelayed({
            if(SharedPreference.instance!!.getPrefStringData("data")!!.isEmpty()){
                startActivity(intent)
                finish()
            }else{
                startActivity(Intent(this, MainActivity::class.java))
            }

        }, 3000)
    }

}
