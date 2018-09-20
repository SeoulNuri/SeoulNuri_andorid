package com.hello.seoulnuri.view.info

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hello.seoulnuri.R
import com.hello.seoulnuri.model.InfoData
import com.hello.seoulnuri.model.InfoItem
import com.hello.seoulnuri.view.info.adapter.InfoReservationAdapter
import kotlinx.android.synthetic.main.fragment_info_reservation.view.*
import java.util.ArrayList

/**
 * Created by VictoryWoo
 */
class InfoReservationFragment : Fragment() {

    lateinit var info_reservation_list : ArrayList<InfoItem>
    lateinit var info_reservation_adpater : InfoReservationAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_info_reservation, container, false)

        info_reservation_list = ArrayList()
        info_reservation_list.add(InfoItem(R.drawable.kakao_default_profile_image,3.0,2,"호텔imi"))
        info_reservation_list.add(InfoItem(R.drawable.img_jj,2.0,20,"호텔jj"))
        info_reservation_list.add(InfoItem(R.drawable.img_jw,4.0,10,"호텔jw"))
        info_reservation_list.add(InfoItem(R.drawable.img_jj,3.5,8,"호텔 렐라"))

        info_reservation_adpater = InfoReservationAdapter(context!!,infoList = info_reservation_list)
        view.reservation_list.setHasFixedSize(true);
        view.reservation_list.layoutManager = GridLayoutManager(activity, 2)
        view.reservation_list.adapter = info_reservation_adpater



        return view
    }
}