package com.hello.seoulnuri.view.info.tour

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.hello.seoulnuri.R
import com.hello.seoulnuri.model.info.tour.introduce.TourBottomData
import kotlinx.android.synthetic.main.fragment_info_tour_intro.view.*
import kotlinx.android.synthetic.main.info_view.*

/**
 * Created by VictoryWoo
 */
open class InfoTourIntroduceFragment : Fragment() {

    /*FIXME
    * companion object에 있는 변수나 함수들은 java의 static처럼 사용할 수 있다.
    * 전역 변수에 매개변수로 받은 값들을 할당하는 과정!
    * */
    companion object {
        lateinit var tourBottomData: TourBottomData

        fun newFragment(image: String, detail: String): InfoTourIntroduceFragment {
            Log.v("woo 1","1")
            var infoTourIntroduceFragment = InfoTourIntroduceFragment()
            Log.v("woo 2","2")
            infoTourIntroduceFragment.intro_image = image
            Log.v("woo 3","3")
            infoTourIntroduceFragment.intro_detail_text = detail
            Log.v("woowoo", image)
            Log.v("woowoo", detail)
            return infoTourIntroduceFragment
        }
    }


    interface setting {
        abstract fun setData(introImage: String, introContent: String)
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)

    }


    /*FIXME
    * lateinit 초기화 시점을 정확히 모르겠어서
    * 나중에 초기화를 하는 방식이 아닌 먼저 빈 문자열로 값을 할당한 후
    * 그 다음에 값을 대입하는 방식으로 구현
    * */
    var intro_image: String=" "
    var intro_detail_text: String=" "


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_info_tour_intro, container, false)
        Log.v("woo 4","4")
        println(intro_image+"몇번??")
        println(intro_detail_text+"몇번?")
        Glide.with(context!!).load(intro_image).into(view.info_tour_detail_image)
        Log.v("woo 5","5")
        view.info_tour_detail_text_content.text = intro_detail_text
        Log.v("woo 6","6")
        return view
    }
}