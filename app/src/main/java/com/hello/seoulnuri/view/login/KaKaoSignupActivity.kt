package com.hello.seoulnuri.view.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.hello.seoulnuri.MainActivity
import com.kakao.auth.ErrorCode
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.response.model.UserProfile
import com.kakao.network.ErrorResult
import com.kakao.network.ApiErrorCode.CLIENT_ERROR_CODE
import com.kakao.usermgmt.callback.MeResponseCallback
import com.kakao.util.helper.log.Logger


/**
 * Created by VictoryWoo
 */
class KaKaoSignupActivity : Activity() {

    /*
    * Main으로 넘길지 가입 페이지를 그릴지 판단하기 위해서
    * requestMe라는 함수를 호출합니다.
    * @param savedInstanceState 기존 session 정보가 저장된 객체
    * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestMe()

    }
    fun requestMe(){
        UserManagement.getInstance().requestMe(object : MeResponseCallback(){
            override fun onSuccess(result: UserProfile?) {
                Logger.d("UserProfile : " + result);
                Log.v("UserProfile : ",result!!.toString())
                redirectMainActivity()


            }

            override fun onSessionClosed(errorResult: ErrorResult?) {
                redirectLoginActivity()
            }

            override fun onNotSignedUp() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onFailure(errorResult: ErrorResult?) {
                val message = "failed to get user info. msg=" + errorResult!!
                Logger.d(message)
                Log.v("fail", "fail")

                val result = ErrorCode.valueOf(errorResult.errorCode)
                if (result === ErrorCode.CLIENT_ERROR_CODE) {
                    finish()
                } else {
                    redirectLoginActivity()
                }
            }

        })
    }

    fun redirectMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    fun redirectLoginActivity(){
        val intent = Intent(this, LoginActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
        finish()
    }

}