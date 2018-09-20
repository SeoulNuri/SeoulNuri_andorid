package com.hello.seoulnuri.view.mypage

import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hello.seoulnuri.R
import kotlinx.android.synthetic.main.fragment_tour_info.view.*

/**
 * Created by VictoryWoo
**/
class TourCourseFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tour_info, container, false)

        val sb = SpannableStringBuilder()

        val str: String = "가보고 싶은 관광코스를 \n즐겨찾기 해보세요."

        sb.append(str)
        sb.setSpan(StyleSpan(Typeface.BOLD), 13, 18, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        view.tourCourseText.text = sb

        return view
    }
}