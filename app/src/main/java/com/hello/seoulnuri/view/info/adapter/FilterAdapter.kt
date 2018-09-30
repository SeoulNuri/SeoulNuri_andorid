package com.hello.seoulnuri.view.info.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.hello.seoulnuri.R
import com.hello.seoulnuri.model.search.filter.FilterData
import kotlinx.android.synthetic.main.filter_item.view.*
import java.util.*

/**
 * Created by VictoryWoo
 */
class FilterAdapter(var itemList : ArrayList<FilterData>, var context : Context) : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>(){

    private lateinit var onItemClick: View.OnClickListener

    fun setOnItemClickListener(l : View.OnClickListener){
        onItemClick = l
    }

    fun changeStatus(position: Int){


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.filter_item, parent, false)
        view.setOnClickListener(onItemClick)
        return FilterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder : FilterViewHolder, position: Int) {
        holder.filterText.text = itemList[position].filter_detail
        if(itemList[position].filter_status){
            holder.cardRela.background = ContextCompat.getDrawable(context,R.drawable.filter_border_active)
            holder.filterText.setTextColor(ContextCompat.getColor(context, R.color.mainWhite))
        }
        else{
            holder.cardRela.background = ContextCompat.getDrawable(context,R.drawable.filter_border)
            holder.filterText.setTextColor(ContextCompat.getColor(context, R.color.mainGray))
        }

    }

    class FilterViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var filterText : TextView = itemView.findViewById(R.id.detail_filter_text)
        var filterCardview : CardView = itemView.findViewById(R.id.filter_cardview)
        var cardRela : RelativeLayout = itemView.findViewById(R.id.card_rela)
    }

}