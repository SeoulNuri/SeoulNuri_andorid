package com.hello.seoulnuri.view.info.tour

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hello.seoulnuri.R
import kotlinx.android.synthetic.main.info_view.*

/**
 * Created by VictoryWoo
 */
class InfoTourIntroduceFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_info_tour_intro, container, false)
        return view
    }
}