package com.hello.seoulnuri.view.info.tour

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hello.seoulnuri.R
import com.hello.seoulnuri.base.Init
import com.hello.seoulnuri.model.info.tour.use.InfoTourUseReponse
import com.hello.seoulnuri.network.ApplicationController
import com.hello.seoulnuri.network.NetworkService
import com.hello.seoulnuri.utils.SharedPreference
import kotlinx.android.synthetic.main.fragment_info_tour_use.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Created by VictoryWoo
 */
class InfoTourUseFragment : Fragment(), Init {
    override fun init() {
        networkService = ApplicationController.instance!!.networkService
        SharedPreference.instance!!.load(context!!)
    }

    override fun onResume() {
        super.onResume()
        init()
        requestInfoUseData()
    }

    lateinit var networkService: NetworkService
    lateinit var progressDialog: ProgressDialog
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_info_tour_use, container, false)
        progressDialog = ProgressDialog(context, R.style.AppCompatAlertDialogStyle)


        //requestInfoUseData()
        return view

    }

    fun requestInfoUseData() {
        val responeUseData = networkService
                .getInfoTourUseMethod(SharedPreference.instance!!.getPrefStringData("data")!!
                        , SharedPreference.instance!!.getPrefIntegerData("tour_idx"))
        responeUseData.enqueue(object : Callback<InfoTourUseReponse> {
            override fun onFailure(call: Call<InfoTourUseReponse>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<InfoTourUseReponse>?, response: Response<InfoTourUseReponse>?) {
                if (response!!.isSuccessful) {
                    createDialog()
                    Log.v("woo 1202", response!!.body()!!.code.toString())

                    info_tour_use_holiday.text = response!!.body()!!.data.tour_bottom.tour_holiday
                    info_tour_use_fee.text = response!!.body()!!.data.tour_bottom.tour_fee
                    // info_tour_fee_additional.text = response!!.body()!!.data.tour_bottom.tour_fee 밑에 추가적인 거 뿌리면 됨
                    info_tour_use_parkingSpace.text = response!!.body()!!.data.tour_bottom.tour_parking_space

                    var str = response!!.body()!!.data.tour_bottom.tour_parking_fee


                    info_tour_use_parking_fee.text = str
                    //st.nextToken() + "\n" + st.nextToken() + "\n" + st.nextToken() + "\n"
                    //println("woo text check : "+st.nextToken()+"\n"+st.nextToken()+"\n"+st.nextToken()+"\n")
                    info_use_tour_book.text = response!!.body()!!.data.tour_bottom.tour_info_book
                    info_use_tour_service.text = response!!.body()!!.data.tour_bottom.tour_service
                    info_tour_toilet_location.text = response!!.body()!!.data.tour_bottom.tour_toilet_location
                    progressDialog.dismiss()


                }
            }

        })
    }

    fun createDialog() {
        //dialog.setTitle("Loading ...")
        Log.v("woo 816","들어오니??")
        progressDialog.setMessage("Please waiting ...")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()
        //val handler = Handler()

    }
}