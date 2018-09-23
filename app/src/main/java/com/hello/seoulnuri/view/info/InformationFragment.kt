package com.hello.seoulnuri.view.info

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hello.seoulnuri.R
import com.hello.seoulnuri.base.Init
import com.hello.seoulnuri.network.ApplicationController
import com.hello.seoulnuri.network.NetworkService
import com.hello.seoulnuri.utils.SharedPreference
import com.hello.seoulnuri.view.info.reservation.InfoReservationFragment
import com.hello.seoulnuri.view.info.tour.InfoTourFragment

/**
 * Created by VictoryWoo
 */
class InformationFragment : Fragment(), View.OnClickListener, Init {
    override fun init() {
        networkService = ApplicationController.instance!!.networkService
        SharedPreference.instance!!.load(context!!)
    }


    override fun onClick(v: View?) {
        when (v!!) {

        }
    }

    fun replaceFragment(fragment: Fragment) {
        val fm = activity!!.supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.replace(R.id.information_frame, fragment)
        transaction.commit()
    }

    lateinit var informationTab: TabLayout
    lateinit var networkService: NetworkService
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_information, container, false)


        informationTab = view.findViewById(R.id.information_tab)
        informationTab.addTab(informationTab!!.newTab().setText("관광정보"))
        informationTab.addTab(informationTab!!.newTab().setText("숙박정보"))
        replaceFragment(InfoTourFragment())
        //requestInfoTour()

        informationTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> {
                        replaceFragment(InfoTourFragment())
                    }
                    1 -> {
                        replaceFragment(InfoReservationFragment())
                    }
                }
            }

        })

        informationTab.setTabTextColors(
                ContextCompat.getColor(context!!, R.color.unselected_text_color), // 선택되지 않은 텍스트 컬러
                ContextCompat.getColor(context!!, R.color.selected_text_color) // 선택된 텍스트 컬러
        )
        return view
    }


}