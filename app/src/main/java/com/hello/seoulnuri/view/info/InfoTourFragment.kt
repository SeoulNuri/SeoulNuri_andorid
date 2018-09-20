package com.hello.seoulnuri.view.info

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hello.seoulnuri.R
import kotlinx.android.synthetic.main.fragment_info_tour.*
import kotlinx.android.synthetic.main.fragment_info_tour.view.*

/**
 * Created by VictoryWoo
 */
class InfoTourFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!){
            information_toggle_total -> {
                if (!information_toggle_total.isSelected) {
                    Log.v("537", "누르기")
                    information_toggle_total.isSelected = true
                } else {
                    Log.v("538", "또 누르기")
                    information_toggle_total.isSelected = false
                }
            }
            information_toggle_eye -> {
                if(!information_toggle_eye.isSelected){
                    information_toggle_eye.isSelected = true
                    information_toggle_total.isSelected = false
                }else{
                    information_toggle_eye.isSelected = false
                }

            }
            information_toggle_ear -> {
                if(!information_toggle_ear.isSelected){
                    information_toggle_ear.isSelected = true
                }else{
                    information_toggle_ear.isSelected = false
                }

            }
            information_toggle_wheel -> {
                if(!information_toggle_wheel.isSelected){
                    information_toggle_wheel.isSelected = true
                }else{
                    information_toggle_wheel.isSelected = false
                }

            }
            information_toggle_elder -> {
                if(!information_toggle_elder.isSelected){
                    information_toggle_elder.isSelected = true
                }else{
                    information_toggle_elder.isSelected = false
                }

            }
        }
    }
    fun init(v: View) {
        v.information_toggle_total.setOnClickListener(this)
        v.information_toggle_eye.setOnClickListener(this)
        v.information_toggle_ear.setOnClickListener(this)
        v.information_toggle_wheel.setOnClickListener(this)
        v.information_toggle_elder.setOnClickListener(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_info_tour, container, false)
        init(view)
        return view
    }
}