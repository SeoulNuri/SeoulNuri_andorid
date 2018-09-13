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
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton

import com.hello.seoulnuri.MainActivity
import com.hello.seoulnuri.R
import com.hello.seoulnuri.network.ApplicationController
import com.hello.seoulnuri.network.NetworkService

class LoginCategoryActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var toggle_eye: ToggleButton
    lateinit var toggle_wheel: ToggleButton
    lateinit var toggle_ear: ToggleButton
    lateinit var toggle_elder: ToggleButton
    lateinit var startButton: ImageView
    lateinit var textView: TextView
    lateinit var loginCategory: ArrayList<Int>
    lateinit var networkService: NetworkService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_category)
        loginCategory = ArrayList(); // 초기화 진행
        networkService = ApplicationController.instance!!.networkService

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


    }

    fun requestCategoryInfo(){

    }

    fun startClick(view: View) {

        requestCategoryInfo()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onClick(view: View) {
        checkEnable()
        when (view.id) {
            R.id.button_eye -> {
                run {
                    if (toggle_eye.isChecked) {
                        toggle_eye.setBackgroundResource(R.drawable.button_eye_active)
                        loginCategory.add(1)
                        Log.v("949", loginCategory.toString())
                    } else {
                        toggle_eye.setBackgroundResource(R.drawable.button_eye)
                        loginCategory.remove(1)
                        Log.v("9492", loginCategory.toString())
                    }
                }
                run {
                    if (toggle_wheel.isChecked) {
                        toggle_wheel.setBackgroundResource(R.drawable.button_wheel_active)
                        loginCategory.add(2)
                    } else {
                        toggle_wheel.setBackgroundResource(R.drawable.button_wheel)
                        loginCategory.remove(2)
                    }
                }
                run {
                    if (toggle_ear.isChecked) {

                        toggle_ear.setBackgroundResource(R.drawable.button_ear_active)
                        loginCategory.add(3)
                    } else {
                        toggle_ear.setBackgroundResource(R.drawable.button_ear)
                        loginCategory.remove(3)
                    }
                }
                run {
                    if (toggle_elder.isChecked) {

                        toggle_elder.setBackgroundResource(R.drawable.button_elder_active)
                        loginCategory.add(4)
                    } else {
                        toggle_elder.setBackgroundResource(R.drawable.button_elder)
                        loginCategory.remove(4)
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
