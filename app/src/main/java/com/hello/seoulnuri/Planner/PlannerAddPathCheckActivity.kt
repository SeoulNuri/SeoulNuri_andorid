package com.hello.seoulnuri.Planner

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.support.v4.widget.SlidingPaneLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.hello.seoulnuri.Model.PlannerPathData
import com.hello.seoulnuri.Planner.Adapter.PlannerPathAdapter
import com.hello.seoulnuri.R
import kotlinx.android.synthetic.main.activity_planner_add_path_check.*
import kotlinx.android.synthetic.main.fragment_planner.*
import kotlinx.android.synthetic.main.sliding_layout.*
import java.util.*

class PlannerAddPathCheckActivity : AppCompatActivity() {

    lateinit var plannerPathAdapter: PlannerPathAdapter
    lateinit var item_list : ArrayList<PlannerPathData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planner_add_path_check)

        item_list = ArrayList()
        item_list.add(PlannerPathData("5분",1,"경복궁"
                ,"서울 종로구 사직로 161 경복궁",1,21))
        item_list.add(PlannerPathData("13분",2,"광화문"
                ,"서울 종로구 사직로 161",1,11))
        item_list.add(PlannerPathData("20분",3,"북촌문화센터"
                ,"서울 종로구 계동길 37",1,40))
        item_list.add(PlannerPathData("5분",4,"갤러리룩스"
                ,"서울 종로구 필운대로7길 12",1,25))
        item_list.add(PlannerPathData("5분",4,"갤러리룩스"
                ,"서울 종로구 필운대로7길 12",1,25))
        item_list.add(PlannerPathData("5분",4,"갤러리룩스"
                ,"서울 종로구 필운대로7길 12",1,25))
        item_list.add(PlannerPathData("5분",4,"갤러리룩스"
                ,"서울 종로구 필운대로7길 12",1,25))
        item_list.add(PlannerPathData("5분",4,"갤러리룩스"
                ,"서울 종로구 필운대로7길 12",1,25))
        item_list.add(PlannerPathData("5분",4,"갤러리룩스"
                ,"서울 종로구 필운대로7길 12",1,25))

        planner_nestedScroll.scrollTo(0,0)

        plannerPathAdapter = PlannerPathAdapter(item_list)
        plannerPathRecyclerview.layoutManager = LinearLayoutManager(this)
        plannerPathRecyclerview.adapter = plannerPathAdapter
        plannerPathRecyclerview.isNestedScrollingEnabled = false

        planner_path_bottom_layout.setOnTouchListener(object : View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when(event!!.action){
                    MotionEvent.ACTION_MOVE->{

                    }
                    MotionEvent.ACTION_DOWN->{
                        planner_nestedScroll.scrollTo(0,0)
                    }
                }
                return true
            }

        })

        Log.v("943",slidingLayout.overScrollMode.toString())
        Log.v("944",slidingLayout.isOverlayed.toString())

        planner_path_bottom_layout.overScrollMode



        if(slidingLayout.overScrollMode != 1){
            Log.v("945",slidingLayout.overScrollMode.toString())
            planner_add_path_bar_image.visibility = View.GONE
            planner_path_down_btn.visibility = View.VISIBLE
        }else{
            Log.v("946",slidingLayout.overScrollMode.toString())
            planner_add_path_bar_image.visibility = View.VISIBLE
            planner_path_down_btn.visibility = View.GONE
        }
        Log.v("947",slidingLayout.overScrollMode.toString())
    }
}
