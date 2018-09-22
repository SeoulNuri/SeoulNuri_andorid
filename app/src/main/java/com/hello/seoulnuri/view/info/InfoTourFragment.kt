package com.hello.seoulnuri.view.info

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.hello.seoulnuri.R
import com.hello.seoulnuri.model.search.filter.FilterData
import com.hello.seoulnuri.utils.ToastMaker
import com.hello.seoulnuri.view.info.adapter.FilterAdapter
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
            v!!->{
                val index_position = filter_list.getChildAdapterPosition(v!!)
                filterItems[index_position].filter_status = !filterItems[index_position].filter_status
                filterAdapter.notifyDataSetChanged()
                ToastMaker.makeShortToast(context!!, index_position.toString())
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

    lateinit var filterAdapter: FilterAdapter
    lateinit var filterItems : ArrayList<FilterData>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_info_tour, container, false)
        init(view)

        filterItems = ArrayList()
        filterItems.add(FilterData("휠체어", false))
        filterItems.add(FilterData("엘리베이터",false))
        filterItems.add(FilterData("관람석",false))
        filterItems.add(FilterData("자막비디오 가이드",false))
        filterItems.add(FilterData("보조견 동반",false))
        filterItems.add(FilterData("휠체어",false))
        filterItems.add(FilterData("엘리베이터",false))
        filterItems.add(FilterData("관람석",false))
        filterAdapter = FilterAdapter(filterItems, context!!)
        filterAdapter.setOnItemClickListener(this)
        view.filter_list.layoutManager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
        view.filter_list.adapter = filterAdapter
        return view
    }
}