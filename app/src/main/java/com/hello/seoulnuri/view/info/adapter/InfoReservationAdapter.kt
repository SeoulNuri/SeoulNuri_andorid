package com.hello.seoulnuri.view.info.adapter

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
import com.hello.seoulnuri.model.info.reservation.InfoTourReservationData
import java.util.*

/**
 * Created by VictoryWoo
 */
class InfoReservationAdapter(var context: Context, var infoList: ArrayList<InfoTourReservationData>)
    : RecyclerView.Adapter<InfoReservationAdapter.ItemViewHolder>() {

    lateinit var datas: ArrayList<InfoData>

    internal var drawable: Drawable? = null
    private lateinit var onItemClick: View.OnClickListener

    fun setOnItemClickListener(l: View.OnClickListener) {
        onItemClick = l
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        //recycler view에 반복될 아이템 레이아웃 연결
        val v = LayoutInflater.from(parent.context).inflate(R.layout.info_view, null)
        v.setOnClickListener(onItemClick)
        return ItemViewHolder(v)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        Glide.with(context).load(infoList[position].lodge_image).into(holder.reservation_image)
        holder.reservation_title.text = infoList[position].lodge_title
        holder.reservation_ratingBar.rating = infoList[position].lodge_star.toFloat()
        holder.reservation_count.setText("(" + infoList[position].lodge_star_count + ")") // comment num

    }

    override fun getItemCount(): Int {
        return this.infoList.size
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var reservation_image: ImageView
        internal var reservation_title: TextView
        internal var reservation_ratingBar: RatingBar
        internal var reservation_count: TextView
        internal var reservation_cardview: CardView

        init {
            reservation_image = itemView.findViewById<View>(R.id.cd_img) as ImageView
            reservation_title = itemView.findViewById<View>(R.id.tv_infotitle) as TextView
            reservation_ratingBar = itemView.findViewById<View>(R.id.rb_info) as RatingBar
            reservation_count = itemView.findViewById<View>(R.id.tv_cmt) as TextView
            reservation_cardview = itemView.findViewById<View>(R.id.cinfo) as CardView
        }
    }
}