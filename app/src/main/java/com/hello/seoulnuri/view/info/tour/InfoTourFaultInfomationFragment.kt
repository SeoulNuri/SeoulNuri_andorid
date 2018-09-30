package com.hello.seoulnuri.view.info.tour

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.hello.seoulnuri.R
import com.hello.seoulnuri.base.Init
import com.hello.seoulnuri.model.info.tour.fault.Accessibility
import com.hello.seoulnuri.model.info.tour.fault.InfoTourFaultResponse
import com.hello.seoulnuri.network.ApplicationController
import com.hello.seoulnuri.utils.SharedPreference
import com.kakao.network.NetworkService
import kotlinx.android.synthetic.main.fragment_info_tour_fault.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by VictoryWoo
 */
class InfoTourFaultInfomationFragment : Fragment(), Init{
    override fun init() {
        networkService = ApplicationController.instance!!.networkService
        SharedPreference.instance!!.load(context!!)

    }

    lateinit var networkService: com.hello.seoulnuri.network.NetworkService
    lateinit var accessibility : Accessibility

    override fun onResume() {
        super.onResume()
        init()
        requestInfoFaultData()

    }
    lateinit var progressDialog : ProgressDialog
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_info_tour_fault, container, false)
        //init()
        progressDialog = ProgressDialog(context,R.style.AppCompatAlertDialogStyle)

        return view
    }

    fun requestInfoFaultData(){
        val responseInfoFault = networkService
                .getInfoTourFault(SharedPreference.instance!!.getPrefStringData("data")!!
                        , SharedPreference.instance!!.getPrefIntegerData("tour_idx"))

        responseInfoFault.enqueue(object : Callback<InfoTourFaultResponse>{
            override fun onFailure(call: Call<InfoTourFaultResponse>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<InfoTourFaultResponse>?, response: Response<InfoTourFaultResponse>?) {
                if(response!!.isSuccessful){
                    createDialog()
                    accessibility = response!!.body()!!.data.tour_bottom.accessibility

                    classification(accessibility)

                    println("woo : data 확인 ${response!!.body()!!.data.tour_bottom.common}")
                    fault_parking.text = response!!.body()!!.data.tour_bottom.common.parking
                    fault_transportation.text = response!!.body()!!.data.tour_bottom.common.transportation
                    // fault_road.text = response!!.body()!!.data.tour_bottom.common.road 지금은 값이 null
                    fault_entrance.text = response!!.body()!!.data.tour_bottom.common.entrance
                    fault_toilet.text = response!!.body()!!.data.tour_bottom.common.toilet
                    fault_infodesk.text = response!!.body()!!.data.tour_bottom.common.infodesk
                    Log.v("824",response!!.body()!!.data.tour_bottom.toString())
                    if(response!!.body()!!.data.tour_bottom.visual.block == null){
                        Log.v("824",response!!.body()!!.data.tour_bottom.visual.toString())
                        fault_eye_block.text = "없음"
                        fault_eye_block2.text = "없음"
                        fault_eye_block3.text = "없음"
                        fault_eye_block4.text = "없음"
                        fault_eye_block5.text = "없음"
                    }

                    /*FIXME
                    * 나머지 시각, 청각, 지체, 노인 부분들은 서버에서 값 넣어주면 그거 보고 뿌리면 됨!!
                    * */
                    //Log.v("12244 woo",response!!.body()!!.data.common.toString())
                    progressDialog.dismiss()
                }
            }

        })
    }
    fun createDialog() {
        //dialog.setTitle("Loading ...")
        progressDialog.setMessage("Please waiting ...")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()
        //val handler = Handler()

    }

    fun classification(access : Accessibility){
        when(access.visual){
            "하"->{setImage(R.drawable.graphic_bottom, fault_eye_image)}
            "중"->{setImage(R.drawable.grphic_middle, fault_eye_image)}
            "상"->{setImage(R.drawable.graphic_top, fault_eye_image)}
        }
        when(access.hearing){
            "하"->{setImage(R.drawable.graphic_bottom, fault_ear_image)}
            "중"->{setImage(R.drawable.grphic_middle, fault_ear_image)}
            "상"->{setImage(R.drawable.graphic_top, fault_ear_image)}
        }
        when(access.physical){
            "하"->{setImage(R.drawable.graphic_bottom, fault_wheel_image)}
            "중"->{setImage(R.drawable.grphic_middle, fault_wheel_image)}
            "상"->{setImage(R.drawable.graphic_top, fault_wheel_image)}
        }
        when(access.older){
            "하"->{setImage(R.drawable.graphic_bottom, fault_elder_image)}
            "중"->{setImage(R.drawable.grphic_middle, fault_elder_image)}
            "상"->{setImage(R.drawable.graphic_top, fault_elder_image)}
        }


    }

    fun setImage(image : Int, id : ImageView){
        Glide.with(this@InfoTourFaultInfomationFragment).load(image).into(id)
    }
}