package com.hello.seoulnuri.view.login

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton

import com.hello.seoulnuri.view.main.MainActivity
import com.hello.seoulnuri.R
import com.hello.seoulnuri.base.BaseModel
import com.hello.seoulnuri.model.login.LoginCategoryRequest
import com.hello.seoulnuri.network.ApplicationController
import com.hello.seoulnuri.network.NetworkService
import com.hello.seoulnuri.utils.SharedPreference
import kotlinx.android.synthetic.main.activity_login_category.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class LoginCategoryActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var toggle_eye: ToggleButton
    lateinit var toggle_wheel: ToggleButton
    lateinit var toggle_ear: ToggleButton
    lateinit var toggle_elder: ToggleButton
    lateinit var startButton: ImageView
    lateinit var textView: TextView
    lateinit var loginCategory: ArrayList<Int>
    lateinit var networkService: NetworkService
    lateinit var loginCategoryRequest: LoginCategoryRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_category)
        loginCategory = ArrayList() // 초기화 진행
        networkService = ApplicationController.instance!!.networkService
        SharedPreference.instance!!.load(this)
        textView = findViewById<View>(R.id.t2) as TextView
        val sb = SpannableStringBuilder()

        val str = "장애 유형을 선택해주세요."

        sb.append(str)

        sb.setSpan(StyleSpan(Typeface.BOLD), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        textView.text = sb

        toggle_eye = findViewById<View>(R.id.button_eye) as ToggleButton
        toggle_wheel = findViewById<View>(R.id.button_wheel) as ToggleButton
        toggle_ear = findViewById<View>(R.id.button_ear) as ToggleButton
        toggle_elder = findViewById<View>(R.id.button_elder) as ToggleButton
        startButton = findViewById<View>(R.id.button_start) as ImageView

        toggle_eye.setOnClickListener(this)
        toggle_wheel.setOnClickListener(this)
        toggle_ear.setOnClickListener(this)
        toggle_elder.setOnClickListener(this)

        startButton.isEnabled = false
        Log.v("User data : ",SharedPreference.instance!!.getPrefStringData("data"))


    }

    fun requestCategoryInfo(){
        loginCategoryRequest = LoginCategoryRequest(loginCategory)
        Log.v("1994 test",loginCategoryRequest.toString())
        val categoryResponse = networkService.selectCategory(SharedPreference.instance!!.getPrefStringData("data")!!,loginCategoryRequest)
        categoryResponse.enqueue(object : Callback<BaseModel>{
            override fun onFailure(call: Call<BaseModel>?, t: Throwable?) {
                Log.v("1994 onFailure",t!!.message)
                Log.v("1994 onFailure",t!!.toString())
            }

            override fun onResponse(call: Call<BaseModel>?, response: Response<BaseModel>?) {
                if(response!!.isSuccessful){
                    Log.v("1994 category response",response.message().toString())
                    startActivity(Intent(this@LoginCategoryActivity, MainActivity::class.java))
                    finish()
                }else{
                    Log.v("1994 onResponse else",response.message().toString())
                    Log.v("1994 onResponse code",response!!.code().toString())
                }
            }

        })

    }

    fun startClick(view: View) {

        if(button_eye.isChecked)
            loginCategory.add(0)
        if(button_ear.isChecked)
            loginCategory.add(1)
        if(button_wheel.isChecked)
            loginCategory.add(2)
        if(button_elder.isChecked)
            loginCategory.add(3)

        Log.v("1994", loginCategory.toString())
        requestCategoryInfo()


        //requestCategoryInfo()

        //val intent = Intent(this, MainActivity::class.java)
        //startActivity(intent)
    }

    override fun onClick(view: View) {
        checkEnable()
        when (view.id) {
            R.id.button_eye -> {
                run {
                    if (toggle_eye.isChecked) {
                        toggle_eye.setBackgroundResource(R.drawable.button_eye_active)


                    } else {
                        toggle_eye.setBackgroundResource(R.drawable.button_eye)

                    }
                }
                run {
                    if (toggle_wheel.isChecked) {
                        toggle_wheel.setBackgroundResource(R.drawable.button_wheel_active)

                    } else {
                        toggle_wheel.setBackgroundResource(R.drawable.button_wheel)

                    }
                }
                run {
                    if (toggle_ear.isChecked) {

                        toggle_ear.setBackgroundResource(R.drawable.button_ear_active)
                    } else {
                        toggle_ear.setBackgroundResource(R.drawable.button_ear)
                    }
                }
                run {
                    if (toggle_elder.isChecked) {

                        toggle_elder.setBackgroundResource(R.drawable.button_elder_active)
                    } else {
                        toggle_elder.setBackgroundResource(R.drawable.button_elder)
                    }
                }
            }

            R.id.button_wheel -> {
                run {
                    if (toggle_wheel.isChecked)
                        toggle_wheel.setBackgroundResource(R.drawable.button_wheel_active)
                    else
                        toggle_wheel.setBackgroundResource(R.drawable.button_wheel)
                }
                run {
                    if (toggle_ear.isChecked)
                        toggle_ear.setBackgroundResource(R.drawable.button_ear_active)
                    else
                        toggle_ear.setBackgroundResource(R.drawable.button_ear)
                }
                run {
                    if (toggle_elder.isChecked)
                        toggle_elder.setBackgroundResource(R.drawable.button_elder_active)
                    else
                        toggle_elder.setBackgroundResource(R.drawable.button_elder)
                }
            }

            R.id.button_ear -> {
                run {
                    if (toggle_ear.isChecked)
                        toggle_ear.setBackgroundResource(R.drawable.button_ear_active)
                    else
                        toggle_ear.setBackgroundResource(R.drawable.button_ear)
                }
                run {
                    if (toggle_elder.isChecked)
                        toggle_elder.setBackgroundResource(R.drawable.button_elder_active)
                    else
                        toggle_elder.setBackgroundResource(R.drawable.button_elder)
                }
            }

            R.id.button_elder -> {
                if (toggle_elder.isChecked)
                    toggle_elder.setBackgroundResource(R.drawable.button_elder_active)
                else
                    toggle_elder.setBackgroundResource(R.drawable.button_elder)
            }
        }

    }

    fun checkEnable() {
        if (toggle_eye.isChecked || toggle_wheel.isChecked || toggle_ear.isChecked || toggle_elder.isChecked) {
            //startButton.setImageResource(R.drawable.button_start_active);
            startButton.isSelected = true
            startButton.isEnabled = true
        } else {
            //startButton.setImageResource(R.drawable.button_start_g);
            startButton.isSelected = false
            startButton.isEnabled = false
        }
    }
}
