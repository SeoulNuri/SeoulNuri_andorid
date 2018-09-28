package com.hello.seoulnuri.view.planner

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.CalendarView
import com.hello.seoulnuri.R
import kotlinx.android.synthetic.main.activity_planner_date.*
import java.text.SimpleDateFormat
import java.util.*

class PlannerDateActivity : AppCompatActivity(), View.OnClickListener, CalendarView.OnDateChangeListener {
    override fun onSelectedDayChange(p0: CalendarView?, p1: Int, p2: Int, p3: Int) {
//        val month: String
//        val day: String
//
//        if (p2 < 9)
//            month = "0" + (p2 + 1)
//        else
//            month = "" + (p2 + 1)
//
//        if (p3 < 10)
//            day = "0$p3"
//        else
//            day = "" + p3
//
//        date = month + "월 " + day + "일"

        date = p1.toString()+"-"+(p2+1).toString()+"-"+p3.toString()
    }

    override fun onClick(v: View?) {
        when (v!!) {
            set_date_button -> {
                intent = Intent(this, PlannerStartActivity::class.java)
                intent.putExtra("date", date)
                startActivity(intent)
            }
        }
    }

    private fun setCurrentTime() {
        mNow = System.currentTimeMillis()
        mDate = Date(mNow)
        date = mFormat.format(mDate)
    }

    internal var mNow: Long = 0
    internal var mDate = Date(mNow)
    internal var mFormat = SimpleDateFormat("yyyy-MM-dd")
    var date = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planner_date)

        setCurrentTime()


        calendarView.setOnDateChangeListener(this)
        set_date_button.setOnClickListener(this);

    }
}
