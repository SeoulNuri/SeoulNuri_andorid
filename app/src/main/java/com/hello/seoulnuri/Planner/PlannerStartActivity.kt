package com.hello.seoulnuri.Planner

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.hello.seoulnuri.R
import com.hello.seoulnuri.utils.ToastMaker
import kotlinx.android.synthetic.main.activity_planner_start.*


class PlannerStartActivity : AppCompatActivity(), View.OnClickListener{
    override fun onClick(v: View?) {
        when(v!!){
            planner_start_next_button->{
                if(planner_start_next_button.isSelected){
                    startActivity(Intent(this, PlannerAddOneActivity::class.java))
                }else{
                    ToastMaker.makeLongToast(this,"입력해주세요.")
                }
            }
        }
    }

    fun init(){
        planner_start_next_button.setOnClickListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planner_start)
        init()


        search_auto_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.toString().length >= 1) {
                    planner_start_next_button.isSelected = true
                    planner_start_next_button.isClickable = true
                } else {
                    planner_start_next_button.isSelected = false
                    planner_start_next_button.isClickable = false
                }
            }
        })


    }
}
