package com.hello.seoulnuri.view.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.hello.seoulnuri.view.main.MainActivity
import com.hello.seoulnuri.model.login.LoginUserData
import com.hello.seoulnuri.model.login.LoginUserResponse
import com.hello.seoulnuri.network.ApplicationController
import com.hello.seoulnuri.network.NetworkService
import com.hello.seoulnuri.utils.SharedPreference
import com.kakao.auth.ErrorCode
import com.kakao.usermgmt.UserManagement
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.helper.log.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
    lateinit var loginUserData: LoginUserData
    lateinit var kakao_age : String
    lateinit var networkService: NetworkService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreference.instance!!.load(this)
        networkService = ApplicationController.instance!!.networkService

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
                kakao_age = result!!.kakaoAccount.ageRange.toString().substring(4,6)

                /*SharedPreference.instance!!.setPrefData("id",result!!.id)
                SharedPreference.instance!!.setPrefData("nickname",result!!.nickname)
                SharedPreference.instance!!.setPrefData("birthday",result!!.kakaoAccount.birthday.toString())
                SharedPreference.instance!!.setPrefData("age",kakao_age)
                Log.v("UserProfile6",kakao_age)*/

                if(SharedPreference.instance!!.getPrefStringData("data")!!.isEmpty()){
                    loginPost(kakao_age, result!!.kakaoAccount.birthday.toString(),result!!.nickname,result!!.id.toString())
                }else{
                    redirectLoginCategoryActivity()
                }
                //redirectMainActivity()


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

    // 로그인 요청
    fun loginPost(age : String, birthday : String, nickname : String, id : String){
        Log.v("25","25")
        loginUserData = LoginUserData(age, birthday, nickname, id)
        Log.v("25555 data",loginUserData.toString())
        var loginUserResponse = networkService.signUp(loginUserData)
        var res = networkService.signUp(loginUserData)
        Log.v("255","255")
        loginUserResponse.enqueue(object : Callback<LoginUserResponse>{
            override fun onFailure(call: Call<LoginUserResponse>?, t: Throwable?) {
                Log.v("login failure 123",t!!.message)
            }

            override fun onResponse(call: Call<LoginUserResponse>?, response: Response<LoginUserResponse>?) {
                if(response!!.isSuccessful){
                    Log.v("2555","2555")
                    Log.v("login 123",response!!.code().toString())
                    Log.v("login code",response!!.body()!!.code!!.toString())
                    Log.v("login message",response!!.body()!!.message!!.toString())
                    Log.v("login statue",response!!.body()!!.status!!.toString())
                    println("2555 success : ${response!!.body()!!.data}")
                    //Log.v("25555 success",response!!.body()!!.data)
                    SharedPreference.instance!!.setPrefData("data",response!!.body()!!.data!!)
                    redirectLoginCategoryActivity()
                } else{
                    Log.v("login else 123",response!!.code().toString())
                    redirectLoginActivity()
                }
            }

        })

    }

    fun redirectLoginCategoryActivity(){
        startActivity(Intent(this, LoginCategoryActivity::class.java))
        finish()
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