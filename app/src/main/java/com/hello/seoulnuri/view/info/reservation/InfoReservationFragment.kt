package com.hello.seoulnuri.view.info.reservation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hello.seoulnuri.R
import com.hello.seoulnuri.base.Init
import com.hello.seoulnuri.model.info.reservation.InfoTourReservation
import com.hello.seoulnuri.model.info.reservation.InfoTourReservationData
import com.hello.seoulnuri.network.ApplicationController
import com.hello.seoulnuri.network.NetworkService
import com.hello.seoulnuri.utils.SharedPreference
import com.hello.seoulnuri.utils.ToastMaker
import com.hello.seoulnuri.view.info.adapter.InfoReservationAdapter
import com.hello.seoulnuri.view.info.adapter.InfoTourAdapter
import com.hello.seoulnuri.view.main.MainActivity
import kotlinx.android.synthetic.main.fragment_info_reservation.*
import kotlinx.android.synthetic.main.fragment_info_reservation.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList


/**
 * Created by VictoryWoo
 */
class InfoReservationFragment : Fragment(), View.OnClickListener, Init {
    override fun init() {
        networkService = ApplicationController.instance!!.networkService
        SharedPreference.instance!!.load(context!!)
    }

    override fun onClick(v: View?) {
        when(v!!){
            v->{
                var index_position = reservation_list.getChildAdapterPosition(v!!)
                ToastMaker.makeShortToast(context, index_position.toString())
                startActivity(Intent(context, InfoReservationDetailActivity::class.java))
            }

        }
    }

    lateinit var info_reservation_list : ArrayList<InfoTourReservationData>
    lateinit var info_tour_adpater : InfoReservationAdapter
    lateinit var networkService : NetworkService
    lateinit var fContext : MainActivity

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        fContext = (activity as MainActivity?)!!
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_info_reservation, container, false)

        init()
        info_reservation_list = ArrayList()


        //requestInfoReservation()








        return view
    }

    fun requestInfoReservation(){
        val responseReservation = networkService.getInfoReservation(SharedPreference.instance!!.getPrefStringData("data")!!)
        responseReservation.enqueue(object : Callback<InfoTourReservation>{
            override fun onFailure(call: Call<InfoTourReservation>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<InfoTourReservation>?, response: Response<InfoTourReservation>?) {
                if(response!!.isSuccessful){

                    Log.v("woo 426",response!!.message())


                    /*FIXME
                    * 지금 더미 데이터가 없어서 값을 넣을 수가 없음
                    * 글라이드에서 터지기 때문!!
                    * 통신은 성공 값만 체크하면됨!
                    *
                    * */
                    /*info_tour_adpater = InfoReservationAdapter(context!!,infoList = info_reservation_list)
                    info_tour_adpater.setOnItemClickListener(this@InfoReservationFragment)
                    reservation_list.setHasFixedSize(true);
                    reservation_list.layoutManager = GridLayoutManager(activity, 2)
                    reservation_list.adapter = info_tour_adpater*/
                }
            }

        })
    }
}