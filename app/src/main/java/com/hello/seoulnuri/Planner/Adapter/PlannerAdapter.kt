package com.hello.seoulnuri.Planner.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.hello.seoulnuri.Model.PlannerListData
import com.hello.seoulnuri.R
import org.w3c.dom.Text

/**
 * Created by VictoryWoo
 */
class PlannerAdapter(var item_list : ArrayList<PlannerListData>, var context : Context)
    : RecyclerView.Adapter<PlannerAdapter.PlannerViewHolder>(){

    var check: Int = 0
    private lateinit var onItemClick: View.OnClickListener

    fun setOnItemClickListener(l: View.OnClickListener) {
        onItemClick = l
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlannerViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.planner_list, parent, false)
        return PlannerViewHolder(view)

    }

    override fun getItemCount(): Int = item_list.size

    override fun onBindViewHolder(holder : PlannerViewHolder, position: Int) {
        holder.date_text.text = item_list[position].planner_date
        holder.location_text.text = item_list[position].planner_location
        if (check == 1) {
            holder.deleteBtn.visibility = View.VISIBLE

        } else {
            holder.deleteBtn.visibility = View.GONE
        }

        holder.deleteBtn.setOnClickListener {
            Log.v("delete position", position.toString())
            item_list.removeAt(position)
            //datas.removeAt(position)

            notifyDataSetChanged()
        }
    }

    class PlannerViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var date_text : TextView = itemView.findViewById(R.id.planner_list_date)
        var location_text : TextView = itemView.findViewById(R.id.planner_list_location)
        var deleteBtn: ImageButton = itemView.findViewById(R.id.delete_btn)
    }
    fun change(checkNum: Int) {
        check = checkNum
        notifyDataSetChanged()
    }
}