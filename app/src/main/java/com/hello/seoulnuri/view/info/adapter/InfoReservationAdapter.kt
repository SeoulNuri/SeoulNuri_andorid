package com.hello.seoulnuri.view.info.adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.hello.seoulnuri.R
import com.hello.seoulnuri.info.Info_stay_detail
import com.hello.seoulnuri.model.InfoData
import com.hello.seoulnuri.model.InfoItem
import java.util.ArrayList

/**
 * Created by VictoryWoo
 */
class InfoReservationAdapter(var context: Context, var infoList: ArrayList<InfoItem>)
    : RecyclerView.Adapter<InfoReservationAdapter.ItemViewHolder>() {

    lateinit var datas: ArrayList<InfoData>

    internal var drawable: Drawable? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        //recycler view에 반복될 아이템 레이아웃 연결
        val v = LayoutInflater.from(parent.context).inflate(R.layout.info_view, null)
        return ItemViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ItemViewHolder, i: Int) {
        val item = infoList[i]


        val drawable = context.resources.getDrawable(item.image)
        viewHolder.image1.setBackground(drawable)

        viewHolder.title.setText(item.title)
        viewHolder.ratingBar.setRating(item.star.toFloat()) //star
        viewHolder.coment.setText("(" + item.cmt_cnt + ")") // comment num

    }

    override fun getItemCount(): Int {
        return this.infoList.size
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var image1: ImageView
        internal var title: TextView
        internal var ratingBar: RatingBar
        internal var coment: TextView
        internal var cardview: CardView

        init {
            image1 = itemView.findViewById<View>(R.id.cd_img) as ImageView
            title = itemView.findViewById<View>(R.id.tv_infotitle) as TextView
            ratingBar = itemView.findViewById<View>(R.id.rb_info) as RatingBar
            coment = itemView.findViewById<View>(R.id.tv_cmt) as TextView
            cardview = itemView.findViewById<View>(R.id.cinfo) as CardView
        }
    }
}