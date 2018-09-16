package com.hello.seoulnuri.view.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

import com.hello.seoulnuri.R
import com.hello.seoulnuri.base.Init
import com.hello.seoulnuri.utils.SharedPreference
import com.hello.seoulnuri.utils.ToastMaker
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.util.exception.KakaoException
import com.kakao.util.helper.log.Logger
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener, Init {
    override fun init() {
        login_kakao_custom_btn.setOnClickListener(this)
        login_kakao_btn.setOnClickListener(this)
        SharedPreference.instance!!.load(this)
    }

    override fun onClick(v: View?) {
        when (v!!) {
            login_kakao_custom_btn -> {
                ToastMaker.makeLongToast(this, "눌리니?")
                if(!SharedPreference.instance!!.getPrefStringData("data")!!.isEmpty()){
                    redirectLoginCategory()

                }else{
                    login_kakao_btn.performClick()
                }
            }
        }
    }

    fun redirectLoginCategory(){
        startActivity(Intent(this, LoginCategoryActivity::class.java))
        finish()
    }


    private lateinit var callback: SessionCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()

        // 아래 두개의 함수는 중요합니다.
        callback = SessionCallback()
        Session.getCurrentSession().addCallback(callback)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(Session.getCurrentSession().handleActivityResult(requestCode,resultCode,data)){
            Logger.d("data : ${data}")
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        Session.getCurrentSession().removeCallback(callback)
    }

    inner class SessionCallback : ISessionCallback {
        override fun onSessionOpenFailed(exception: KakaoException?) {
            if (exception != null){
                // exception이 널이 아니라는 뜻은 예외가 있다는 뜻이기 때문에 카카오톡 로그인이 실패했다는 것!
                Log.v("exception",exception.toString())
            }
            setContentView(R.layout.activity_login)
            // 세션 연결이 실패했을 때 로그인 화면을 다시 불러옵니다.
        }

        override fun onSessionOpened() {
            // 세션 연결 성공 시 redirectSigupActivity() 호출을 통해서
            // 다른 액티비티를 띄운다.
            redirectSignupActivity()

        }

    }

    fun redirectSignupActivity(){
        val intent : Intent = Intent(this, KaKaoSignupActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        finish()
        startActivity(intent)

    }

}
