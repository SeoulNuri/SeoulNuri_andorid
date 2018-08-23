package com.hello.seoulnuri.Planner

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.hello.seoulnuri.R
import kotlinx.android.synthetic.main.activity_planner_start.*


class PlannerStartActivity : AppCompatActivity(), View.OnClickListener{
    override fun onClick(v: View?) {
        when(v!!){
            planner_start_next_button->{

            }
        }
    }

    fun init(){
        planner_start_next_button.setOnClickListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planner_start)


        search_auto_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.toString().length >= 1) {
                    planner_start_next_button.isSelected = true
                } else {
                    planner_start_next_button.isSelected = false
                }
            }
        })


    }
}
