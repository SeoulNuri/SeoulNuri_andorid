package com.hello.seoulnuri.view.mypage

import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import com.hello.seoulnuri.R
import com.hello.seoulnuri.base.BaseModel
import com.hello.seoulnuri.base.Init
import com.hello.seoulnuri.model.login.LoginCategoryRequest
import com.hello.seoulnuri.network.ApplicationController
import com.hello.seoulnuri.network.NetworkService
import com.hello.seoulnuri.utils.SharedPreference
import kotlinx.android.synthetic.main.activity_chage_type.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ChangeTypeActivity : AppCompatActivity(), View.OnClickListener, Init {

    // 초기화 함수
    override fun init() {
        change_type_button_start.setOnClickListener(this)
        change_type_button_eye.setOnClickListener(this)
        change_type_button_wheel.setOnClickListener(this)
        change_type_button_elder.setOnClickListener(this)
        change_type_button_ear.setOnClickListener(this)
        change_type_button_start.isEnabled = false // 시작하기 버튼 비활성화
        changeCategoryTypeList = ArrayList() // 초기화
        networkService = ApplicationController.instance!!.networkService
        SharedPreference.instance!!.load(this)
    }

    // 클릭 함수
    override fun onClick(v: View?) {
        checkEnable()
        when (v!!.id) {
            R.id.change_type_button_eye -> {
                /*FIXME
                * run은 익명함수의 느낌을 가지고 있다. 나중에 공부!
                * run 을 사용하든 사용하지 않든 동작은 똑같이 함.
                * 하지만, run을 사용하면 가독성이 더 좋아짐.
                * */
                run {
                    if (change_type_button_eye.isChecked) {
                        change_type_button_eye.setBackgroundResource(R.drawable.button_eye_active)
                    } else {
                        change_type_button_eye.setBackgroundResource(R.drawable.button_eye)
                    }
                }

                run {
                    if (change_type_button_wheel.isChecked) {
                        change_type_button_wheel.setBackgroundResource(R.drawable.button_wheel_active)

                    } else {
                        change_type_button_wheel.setBackgroundResource(R.drawable.button_wheel)

                    }
                }

                run {
                    if (change_type_button_ear.isChecked) {
                        change_type_button_ear.setBackgroundResource(R.drawable.button_ear_active)
                    } else {
                        change_type_button_ear.setBackgroundResource(R.drawable.button_ear)
                    }
                }

                run {

                    if (change_type_button_elder.isChecked) {
                        change_type_button_elder.setBackgroundResource(R.drawable.button_elder_active)
                    } else {
                        change_type_button_elder.setBackgroundResource(R.drawable.button_elder)
                    }
                }

            }
            R.id.change_type_button_wheel -> {
                run {
                    if (change_type_button_wheel.isChecked)
                        change_type_button_wheel.setBackgroundResource(R.drawable.button_wheel_active)
                    else
                        change_type_button_wheel.setBackgroundResource(R.drawable.button_wheel)
                }
                run {
                    if (change_type_button_ear.isChecked)
                        change_type_button_ear.setBackgroundResource(R.drawable.button_ear_active)
                    else
                        change_type_button_ear.setBackgroundResource(R.drawable.button_ear)
                }
                run {
                    if (change_type_button_elder.isChecked)
                        change_type_button_elder.setBackgroundResource(R.drawable.button_elder_active)
                    else
                        change_type_button_elder.setBackgroundResource(R.drawable.button_elder)
                }
            }

            R.id.change_type_button_ear -> {
                run {
                    if (change_type_button_ear.isChecked)
                        change_type_button_ear.setBackgroundResource(R.drawable.button_ear_active)
                    else
                        change_type_button_ear.setBackgroundResource(R.drawable.button_ear)
                }
                run {
                    if (change_type_button_elder.isChecked)
                        change_type_button_elder.setBackgroundResource(R.drawable.button_elder_active)
                    else
                        change_type_button_elder.setBackgroundResource(R.drawable.button_elder)
                }
            }
            R.id.change_type_button_elder -> {
                if (change_type_button_ear.isChecked)
                    change_type_button_ear.setBackgroundResource(R.drawable.button_elder_active)
                else
                    change_type_button_ear.setBackgroundResource(R.drawable.button_elder)
            }
            R.id.change_type_button_start -> {
                checkType()
            }
        }
    }


    fun checkType(){
        Log.v("757","ddd")
        if(change_type_button_eye.isChecked)
            changeCategoryTypeList.add(0)
        if(change_type_button_ear.isChecked)
            changeCategoryTypeList.add(1)
        if(change_type_button_wheel.isChecked)
            changeCategoryTypeList.add(2)
        if(change_type_button_elder.isChecked)
            changeCategoryTypeList.add(3)


        Log.v("777",changeCategoryTypeList.toString())
        requestChangedType()

    }



    fun requestChangedType(){
        Log.v("758","ddd???")
        changedType = LoginCategoryRequest(changeCategoryTypeList)
        val changeTypeResponse = networkService.changeType(SharedPreference.instance!!.getPrefStringData("data")!!, changedType)
        changeTypeResponse.enqueue(object : Callback<BaseModel>{
            override fun onFailure(call: Call<BaseModel>?, t: Throwable?) {
                Log.v("7588",t!!.message)
            }

            override fun onResponse(call: Call<BaseModel>?, response: Response<BaseModel>?) {
                if(response!!.isSuccessful){
                    Log.v("759",response!!.body()!!.message)
                    finish()
                }else{
                    Log.v("999",response!!.code().toString())
                }
            }

        })

    }


    // 텍스트 볼드체로 변경하는 함수
    fun changeTextType() {
        val sb: SpannableStringBuilder = SpannableStringBuilder()
        var str: String = "장애 유형을 변경해주세요."
        sb.append(str)
        sb.setSpan(StyleSpan(Typeface.BOLD), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        change_type_text.text = sb

    }


    lateinit var changeCategoryTypeList: ArrayList<Int>
    lateinit var networkService: NetworkService
    lateinit var changedType : LoginCategoryRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chage_type)

        changeTextType() // 텍스트 글자 볼드 처리
        init() // 초기화


    }

    fun checkEnable() {
        if (change_type_button_eye.isChecked || change_type_button_wheel.isChecked || change_type_button_ear.isChecked || change_type_button_elder.isChecked) {
            //startButton.setImageResource(R.drawable.button_start_active);
            change_type_button_start.isSelected = true
            change_type_button_start.isEnabled = true
        } else {
            //startButton.setImageResource(R.drawable.button_start_g);
            change_type_button_start.isSelected = false
            change_type_button_start.isEnabled = false
        }
    }
}
