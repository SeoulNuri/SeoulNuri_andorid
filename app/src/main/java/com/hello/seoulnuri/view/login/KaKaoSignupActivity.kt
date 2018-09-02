package com.hello.seoulnuri.view.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.service.autofill.UserData
import android.util.Log
import com.hello.seoulnuri.MainActivity
import com.hello.seoulnuri.utils.SharedPreference
import com.kakao.auth.ErrorCode
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.response.model.UserProfile
import com.kakao.network.ErrorResult
import com.kakao.network.ApiErrorCode.CLIENT_ERROR_CODE
import com.kakao.usermgmt.callback.MeResponseCallback
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.helper.log.Logger
import java.util.*


/**
 * Created by VictoryWoo
 */
class KaKaoSignupActivity : Activity() {

    /*
    * Main으로 넘길지 가입 페이지를 그릴지 판단하기 위해서
    * requestMe라는 함수를 호출합니다.
    * @param savedInstanceState 기존 session 정보가 저장된 객체
    * */
    lateinit var userData : UserData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreference.instance!!.load(this)
        requestMe()

    }
    fun requestMe(){
        var list : ArrayList<String> = ArrayList()
        list.add("kakao_account.birthday")
        list.add("properties.nickname")
        list.add("kakao_account.age_range")
        /*FIXME
        * me()라는 함수를 사용해야지 이 함수의 매개변수로 내가 원하는 정보를 요청해서 받을 수 있다.
        * requestMe()는 비슷하게 동작하는 거 같은데, 매개변수의 순서가 다르고 마지막에 boolean 값을 추가해줘야 하는데
        * 이 부분이 뭔지 모르겠다..;;
        * */
        UserManagement.getInstance().me(list, object : MeV2ResponseCallback(){
            override fun onSuccess(result: MeV2Response?) {
                Log.v("UserProfile1 : ",result!!.toString())

                Log.v("UserProfile2 : ",result!!.nickname.toString())
                Log.v("UserProfile3 : ",result!!.kakaoAccount.birthday.toString())
                Log.v("UserProfile4 : ",result!!.properties.toString())
                Log.v("UserProfile5 : ",result!!.kakaoAccount.ageRange.toString())


                redirectMainActivity()
            }

            override fun onSessionClosed(errorResult: ErrorResult?) {
                redirectLoginActivity()
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
        /*UserManagement.getInstance().requestMe(object : MeResponseCallback(){
            override fun onSuccess(result: UserProfile?) {
                Logger.d("UserProfile : " + result);
                Log.v("UserProfile : ",result!!.toString())
                //Log.v("Birthdat : ",result!!.getProperty("age"))



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

        }, list,fa)*/
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