package com.hello.seoulnuri.view.info.tour

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.hello.seoulnuri.R
import kotlinx.android.synthetic.main.activity_info_tour_detail.*

class InfoTourDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_tour_detail)

        info_tour_detail_tab.addTab(info_tour_detail_tab.newTab().setText("소개"))
        info_tour_detail_tab.addTab(info_tour_detail_tab.newTab().setText("이용방법"))
        info_tour_detail_tab.addTab(info_tour_detail_tab.newTab().setText("무장애정보"))

        replaceFragment(InfoTourIntroduceFragment())

        info_tour_detail_tab.setTabTextColors(
                ContextCompat.getColor(this, R.color.unselected_text_color), // 선택되지 않은 텍스트 컬러
                ContextCompat.getColor(this, R.color.selected_text_color) // 선택된 텍스트 컬러
        )

        info_tour_detail_tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.position){
                    0->replaceFragment(InfoTourIntroduceFragment())
                    1->replaceFragment(InfoTourUseFragment())
                    2->replaceFragment(InfoTourFaultInfomationFragment())
                }
            }

        })

    }

    fun replaceFragment(fragment: Fragment){
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.replace(R.id.info_tour_frame, fragment)
        transaction.commit()
    }

}
