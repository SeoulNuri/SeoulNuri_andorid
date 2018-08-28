package com.hello.seoulnuri.Planner

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.hello.seoulnuri.R
import com.hello.seoulnuri.utils.ToastMaker
import kotlinx.android.synthetic.main.activity_planner_start.*
import java.text.SimpleDateFormat
import java.util.*


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

            date_text->{
                intent = Intent(this,PlannerDateActivity::class.java)
                intent.putExtra("currentdate",getTime())
                startActivity(intent)
            }
        }
    }

    private fun getTime(): String {
        mNow = System.currentTimeMillis()
        mDate = Date(mNow)
        return mFormat.format(mDate)
    }

    fun init(){


        var selectedDate = intent.getStringExtra("date")

        if(selectedDate!=null) date_text.text = selectedDate
        else date_text.text = getTime()

        date_text.setOnClickListener(this)
        planner_start_next_button.setOnClickListener(this)
    }

    fun autoInit(){
        val colors = arrayOf(
                "Red","Green","Blue","Maroon","Magenta",
                "Gold","GreenYellow"
        )

        val adapter = ArrayAdapter<String>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                colors
        )

        search_auto_text.setAdapter(adapter)

        search_auto_text.threshold = 1

        search_auto_text.onItemClickListener = AdapterView.OnItemClickListener{
            parent,view,position,id->
            val selectedItem = parent.getItemAtPosition(position).toString()

            search_auto_text.text = Editable.Factory.getInstance().newEditable(selectedItem)

        }
    }

    internal var mNow: Long = 0
    internal var mDate = Date(mNow)
    internal var mFormat = SimpleDateFormat("MM월 dd일")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planner_start)
        init()
        autoInit()


        search_auto_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                search_auto_text.background = ColorDrawable(Color.TRANSPARENT)
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
