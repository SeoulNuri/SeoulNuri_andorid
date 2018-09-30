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
import com.hello.seoulnuri.model.mypage.MypageBookmarkTourData
import java.util.ArrayList

/**
 * Created by VictoryWoo
 */
class MypageTourAdapter(var context: Context, var infoList: ArrayList<MypageBookmarkTourData>)
    : RecyclerView.Adapter<MypageTourAdapter.ItemViewHolder>() {

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

        if(infoList[position].tour_card_img == null)
            Glide.with(context).load(R.drawable.card_graphic_none).into(viewHolder.tour_image)
        else
            Glide.with(context).load(infoList[position].tour_card_img).into(viewHolder.tour_image)

        viewHolder.title.text = infoList[position].tour_name
        viewHolder.ratingBar.rating = infoList[position].tour_star.toFloat()
        viewHolder.count.setText("(" + infoList[position].tour_star_count + ")") // comment num

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