package com.hello.seoulnuri.view.mypage.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.hello.seoulnuri.R
import com.hello.seoulnuri.model.InfoData
import com.hello.seoulnuri.model.info.tour.InfoTourResponseData
import com.hello.seoulnuri.model.mypage.MypageBookmarkCourseData
import java.util.ArrayList

/**
 * Created by VictoryWoo
 */
class MypageCourseAdapter(var context: Context, var infoList: ArrayList<MypageBookmarkCourseData>)
    : RecyclerView.Adapter<MypageCourseAdapter.ItemViewHolder>() {

    lateinit var datas: ArrayList<InfoData>

    internal var drawable: Drawable? = null
    private lateinit var onItemClick: View.OnClickListener

    fun setOnItemClickListener(l : View.OnClickListener){
        onItemClick = l
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        //recycler view에 반복될 아이템 레이아웃 연결
        val v = LayoutInflater.from(parent.context).inflate(R.layout.info_view, null)
        v.setOnClickListener(onItemClick)
        return ItemViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ItemViewHolder, position: Int) {
        //val item = infoList[i]

        Glide.with(context).load(infoList[position].course_idx).into(viewHolder.tour_image)

        viewHolder.title.text = infoList[position].course_name
        viewHolder.ratingBar.rating = infoList[position].course_star.toFloat()
        viewHolder.count.setText("(" + infoList[position].course_star_count + ")") // comment num

    }

    override fun getItemCount(): Int {
        return this.infoList.size
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var tour_image: ImageView
        internal var title: TextView
        internal var ratingBar: RatingBar
        internal var count: TextView
        internal var cardview: CardView

        init {
            tour_image = itemView.findViewById<View>(R.id.cd_img) as ImageView
            title = itemView.findViewById<View>(R.id.tv_infotitle) as TextView
            ratingBar = itemView.findViewById<View>(R.id.rb_info) as RatingBar
            count = itemView.findViewById<View>(R.id.tv_cmt) as TextView
            cardview = itemView.findViewById<View>(R.id.cinfo) as CardView
        }
    }
}