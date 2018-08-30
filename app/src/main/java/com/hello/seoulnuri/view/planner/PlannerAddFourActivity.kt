package com.hello.seoulnuri.view.planner

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hello.seoulnuri.base.Init
import com.hello.seoulnuri.R
import kotlinx.android.synthetic.main.activity_planner_add_four.*

class PlannerAddFourActivity : AppCompatActivity(), Init, View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!) {
            planner_add_four_finish_btn -> {
                finish()
            }
        }
    }

    override fun init() {
        planner_add_four_finish_btn.setOnClickListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planner_add_four)
        init()


    }
}
