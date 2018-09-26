package com.hello.seoulnuri.view.info.tour

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.hello.seoulnuri.R
import com.hello.seoulnuri.model.info.tour.InfoTourResponse
import com.hello.seoulnuri.model.info.tour.InfoTourResponseData
import com.hello.seoulnuri.model.login.LoginCategoryRequest
import com.hello.seoulnuri.model.search.filter.FilterData
import com.hello.seoulnuri.network.ApplicationController
import com.hello.seoulnuri.network.NetworkService
import com.hello.seoulnuri.utils.SharedPreference
import com.hello.seoulnuri.utils.ToastMaker
import com.hello.seoulnuri.view.info.adapter.FilterAdapter
import com.hello.seoulnuri.view.info.adapter.InfoTourAdapter
import com.hello.seoulnuri.view.main.MainActivity
import kotlinx.android.synthetic.main.fragment_info_tour.*
import kotlinx.android.synthetic.main.fragment_info_tour.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Created by VictoryWoo
 */
class InfoTourFragment : Fragment(), View.OnClickListener {


    fun checkVisible(flag: Boolean) {
        if (flag) {
            filter_layout.visibility = View.VISIBLE
        } else {
            filter_layout.visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {
        when (v!!) {
            information_toggle_total -> { // 전체
                if (!information_toggle_total.isSelected) {
                    Log.v("537", "누르기")
                    information_toggle_total.isSelected = true
                    information_toggle_eye.isSelected = false
                    information_toggle_ear.isSelected = false
                    information_toggle_wheel.isSelected = false
                    information_toggle_elder.isSelected = false
                    filterItems.removeAll(filterItems)
                    checkVisible(true)

                    requestInfoTour(handi_types, filter)
                } else {
                    Log.v("538", "또 누르기")
                    information_toggle_total.isSelected = false
                }
                checkVisible(information_toggle_eye.isSelected || information_toggle_ear.isSelected || information_toggle_wheel.isSelected || information_toggle_elder.isSelected)

            }
            information_toggle_eye -> { // 시각
                if (!information_toggle_eye.isSelected) {
                    information_toggle_total.isSelected = false
                    information_toggle_eye.isSelected = true // 색 바꿔주기
                    checkVisible(true) // 숨겨진 보여주는것
                    Log.v("221 woo 1", "eye")
                    addFilterItem(2)
                } else {
                    information_toggle_eye.isSelected = false
                    Log.v("221 woo 2", "eye")
                    removeFilterItem(2)
                }
                checkVisible(information_toggle_eye.isSelected || information_toggle_ear.isSelected || information_toggle_wheel.isSelected || information_toggle_elder.isSelected)


            }
            information_toggle_ear -> {
                // 청각
                if (!information_toggle_ear.isSelected) {
                    information_toggle_total.isSelected = false
                    information_toggle_ear.isSelected = true
                    addFilterItem(3)
                } else {
                    information_toggle_ear.isSelected = false
                    removeFilterItem(3)
                }
                checkVisible(information_toggle_eye.isSelected || information_toggle_ear.isSelected || information_toggle_wheel.isSelected || information_toggle_elder.isSelected)


            }
            information_toggle_wheel -> {
                // 지체
                if (!information_toggle_wheel.isSelected) {
                    information_toggle_total.isSelected = false
                    information_toggle_wheel.isSelected = true
                    addFilterItem(4)

                } else {
                    information_toggle_wheel.isSelected = false
                    removeFilterItem(4)

                }
                checkVisible(information_toggle_eye.isSelected || information_toggle_ear.isSelected || information_toggle_wheel.isSelected || information_toggle_elder.isSelected)

            }
            information_toggle_elder -> {
                // 노인
                if (!information_toggle_elder.isSelected) {
                    information_toggle_total.isSelected = false
                    information_toggle_elder.isSelected = true
                    addFilterItem(5)
                } else {
                    information_toggle_elder.isSelected = false
                    removeFilterItem(5)
                }
                checkVisible(information_toggle_eye.isSelected || information_toggle_ear.isSelected || information_toggle_wheel.isSelected || information_toggle_elder.isSelected)
            }
            filter_search_btn -> {
                var temp = ArrayList<Int>()
                var handi_type = ArrayList<Int>()

                if (information_toggle_eye.isSelected) handi_type.add(1)
                if (information_toggle_ear.isSelected) handi_type.add(2)
                if (information_toggle_wheel.isSelected) handi_type.add(3)
                if (information_toggle_elder.isSelected) handi_type.add(4)
                if (information_toggle_total.isSelected) handi_type.add(9)


                for (i in 0..filterItems.size - 1) {
                    Log.v("221 woo i ", "$i")
                    if (filterItems.get(i).filter_status) {
                        temp.add(filterItems.get(i).filter_value)
                    }

                }
                Log.v("221 woo search", temp.toString())
                Log.v("221 woo search", handi_type.toString())
                requestInfoTour(handi_type,temp)


            }
            v!! -> {
                Log.v("124", "다른 버튼을 누름")
                Log.v("124", "뷰의 아이디 : " + v!!.id.toString())
                Log.v("124", "filter_list 아이디 : " + filter_list.id)
                Log.v("124", "info_tour_recyclerview 아이디 : " + info_tour_recyclerview.id)
                Log.v("124", filter_list.id.toString())
                if (v!!.id == -1) {
                    Log.v("124", "filter누름")
                    val index_position = filter_list.getChildAdapterPosition(v!!)
                    filterItems[index_position].filter_status = !filterItems[index_position].filter_status
                    filterAdapter.notifyDataSetChanged()
                    ToastMaker.makeShortToast(context!!, index_position.toString())
                } else if (v!!.id == 2131296347) {
                    Log.v("124", "관광 누름")
                    val index_tour_position = info_tour_recyclerview.getChildAdapterPosition(v!!)
                    val intent: Intent = Intent(context, InfoTourDetailActivity::class.java)
                    SharedPreference.instance!!.setPrefData("tour_idx", info_tour_list[index_tour_position].tour_idx)
                    intent.putExtra("index", info_tour_list[index_tour_position].tour_idx)
                    startActivity(intent)

                }

            }

        }
    }

    fun removeFilterItem(type: Int) {
        when (type) {
            2 -> { // 3개
                Log.v("221 woo", filterItems.toString())
                var temp = ArrayList<FilterData>()

                for (i in 0..filterItems.size - 1) {
                    Log.v("221 woo i ", "$i")
                    if (filterItems.get(i).filter_type.equals("eye")) {
                        temp.add(filterItems.get(i))
                    }
                }

                for (i in 0..temp.size - 1) {
                    if (filterItems.contains(temp.get(i))) filterItems.remove(temp.get(i))
                }
                Log.v("221 woo", filterItems.toString())
                eyeCheckStatus = false
                filterAdapter.notifyDataSetChanged()

            }
            3 -> { // 3개
                var temp = ArrayList<FilterData>()

                for (i in 0..filterItems.size - 1) {
                    Log.v("221 woo i ", "$i")
                    if (filterItems.get(i).filter_type.equals("ear")) {
                        temp.add(filterItems.get(i))
                    }
                }

                for (i in 0..temp.size - 1) {
                    if (filterItems.contains(temp.get(i))) filterItems.remove(temp.get(i))
                }

                earCheckStatus = false
                filterAdapter.notifyDataSetChanged()
            }
            4 -> { // 3개
                var temp = ArrayList<FilterData>()

                for (i in 0..filterItems.size - 1) {
                    Log.v("221 woo i ", "$i")
                    if (filterItems.get(i).filter_type.equals("wheel")) {
                        temp.add(filterItems.get(i))
                    }
                }

                for (i in 0..temp.size - 1) {
                    if (filterItems.contains(temp.get(i))) filterItems.remove(temp.get(i))
                }
                wheelCheckStatus = false
                filterAdapter.notifyDataSetChanged()
            }
            5 -> { // 3개
                var temp = ArrayList<FilterData>()

                for (i in 0..filterItems.size - 1)
                    if (filterItems.get(i).filter_type.equals("elder")) temp.add(filterItems.get(i))


                for (i in 0..temp.size - 1)
                    if (filterItems.contains(temp.get(i))) filterItems.remove(temp.get(i))

                elderCheckStatus = false
                filterAdapter.notifyDataSetChanged()
            }

        }

    }

    fun addFilterItem(type: Int) {
        when (type) {
            2 -> {

                filterItems.add(FilterData("eye", "점자블록", false, 0))
                filterItems.add(FilterData("eye", "점자홍보", false, 1))
                filterItems.add(FilterData("eye", "보조견 동반", false, 2))
                filterItems.add(FilterData("eye", "오디오가이드", false, 3))
                filterItems.add(FilterData("eye", "안내요원", false, 4))
                filterItems.add(FilterData("eye", "점자표지판(안내판)", false, 5))
                filterAdapter.notifyDataSetChanged()
            }
            3 -> {
                filterItems.add(FilterData("ear", "수화안내", false, 10))
                filterItems.add(FilterData("ear", "자막비디오 가이드", false, 11))
                filterAdapter.notifyDataSetChanged()
            }
            4 -> {

                filterItems.add(FilterData("wheel", "휠체어", false, 20))
                filterItems.add(FilterData("wheel", "엘리베이터", false, 21))
                filterItems.add(FilterData("wheel", "관람석", false, 22))
                filterAdapter.notifyDataSetChanged()
            }
            5 -> {
                filterItems.add(FilterData("elder", "휠체어", false, 30))
                filterItems.add(FilterData("elder", "엘리베이터", false, 31))
                filterAdapter.notifyDataSetChanged()
            }
        }

    }

    fun init(v: View) {
        v.information_toggle_total.setOnClickListener(this)
        v.information_toggle_eye.setOnClickListener(this)
        v.information_toggle_ear.setOnClickListener(this)
        v.information_toggle_wheel.setOnClickListener(this)
        v.information_toggle_elder.setOnClickListener(this)
        v.filter_search_btn.setOnClickListener(this)

        info_tour_list = ArrayList()
        networkService = ApplicationController.instance!!.networkService
        SharedPreference.instance!!.load(context!!)
        handi_types = ArrayList()
        filter = ArrayList()

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        fContext = (activity as MainActivity?)!!
    }

    lateinit var info_tour_list: ArrayList<InfoTourResponseData>
    lateinit var info_tour_adpater: InfoTourAdapter
    lateinit var fContext: MainActivity
    lateinit var filterAdapter: FilterAdapter
    lateinit var filterItems: ArrayList<FilterData>

    var wheelCheckStatus: Boolean = false
    var eyeCheckStatus: Boolean = false
    var earCheckStatus: Boolean = false
    var elderCheckStatus: Boolean = false
    lateinit var filter: ArrayList<Int>
    lateinit var networkService: NetworkService
    lateinit var handi_types: ArrayList<Int>
    var checkStatus: Boolean = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_info_tour, container, false)
        init(view)
        //checkStatus = information_toggle_eye.isSelected || information_toggle_ear.isSelected || information_toggle_wheel.isSelected || information_toggle_elder.isSelected

        filterItems = ArrayList()

        handi_types.add(9)
        filter.add(99)
        requestInfoTour(handi_types,filter)

        /*info_tour_list.add(InfoItem(R.drawable.img_jw, 3.0, 2, "호텔imi"))
        info_tour_list.add(InfoItem(R.drawable.img_jw, 2.0, 20, "호텔jj"))
        info_tour_list.add(InfoItem(R.drawable.img_jw, 4.0, 10, "호텔jw"))
        info_tour_list.add(InfoItem(R.drawable.img_jw, 3.5, 8, "호텔 렐라"))
        info_tour_list.add(InfoItem(R.drawable.img_jw, 3.0, 2, "호텔imi"))
        info_tour_list.add(InfoItem(R.drawable.img_jw, 2.0, 20, "호텔jj"))
        info_tour_list.add(InfoItem(R.drawable.img_jw, 4.0, 10, "호텔jw"))
        info_tour_list.add(InfoItem(R.drawable.img_jw, 3.5, 8, "호텔 렐라"))*/

        info_tour_adpater = InfoTourAdapter(context!!, infoList = info_tour_list)
        info_tour_adpater.setOnItemClickListener(this)
        view.info_tour_recyclerview.setHasFixedSize(true);
        view.info_tour_recyclerview.layoutManager = GridLayoutManager(activity, 2)
        view.info_tour_recyclerview.adapter = info_tour_adpater

        filterAdapter = FilterAdapter(filterItems, context!!)
        filterAdapter.setOnItemClickListener(this)
        view.filter_list.layoutManager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
        view.filter_list.adapter = filterAdapter
        return view
    }

    fun requestInfoTour(handi : ArrayList<Int>, filtering : ArrayList<Int>) {

        Log.v("130", handi_types.toString())
        Log.v("130", filter.toString())
        println("130, 장애유형 ${handi_types}")
        println("130, 필터링 ${filter}")
        var infoTourResponse = networkService
                .getInfoTour(SharedPreference.instance!!.getPrefStringData("data")!!, handi.toString(), filtering.toString())

        infoTourResponse.enqueue(object : Callback<InfoTourResponse> {
            override fun onFailure(call: Call<InfoTourResponse>?, t: Throwable?) {
                Log.v("11588 : ", t!!.message)
            }

            override fun onResponse(call: Call<InfoTourResponse>?, response: Response<InfoTourResponse>?) {
                if (response!!.code() == 200) {
                    //Log.v("11599 : ",response!!.body()!!.data.size.toString())
                    info_tour_list = response!!.body()!!.data
                    println("11599 data size : ${response!!.body()!!.data.size}")
                    println("11599 data message : ${response!!.message()}")
                    println("11599 data  : ${response!!.body()!!.status}")
                    info_tour_adpater = InfoTourAdapter(context!!, infoList = info_tour_list)
                    info_tour_adpater.setOnItemClickListener(this@InfoTourFragment)
                    info_tour_recyclerview.setHasFixedSize(true)
                    info_tour_recyclerview.layoutManager = GridLayoutManager(activity, 2)
                    info_tour_recyclerview.adapter = info_tour_adpater


                } else {
                    Log.v("11600 : ", response!!.message())
                }
            }

        })

    }

   /* fun requestInfoTour(handi_type : ArrayList<Int>,temp : ArrayList<Int>){

    }*/
}