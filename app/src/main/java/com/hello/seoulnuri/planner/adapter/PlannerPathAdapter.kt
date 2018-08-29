package com.hello.seoulnuri.planner.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import com.hello.seoulnuri.model.PlannerPathData
import com.hello.seoulnuri.R

/**
 * Created by VictoryWoo
 */
class PlannerPathAdapter(var items: ArrayList<PlannerPathData>) : RecyclerView.Adapter<PlannerPathAdapter.PlannerPathViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlannerPathViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.planner_path_item, parent, false)
        return PlannerPathViewHolder(view)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PlannerPathViewHolder, position: Int) {

        holder.plannerPathNumber.text = (position + 1).toString()
        holder.plannerPathLocation.text = items[position].plannerLocation
        holder.plannerPathAddress.text = items[position].plannerAddress
        holder.plannerPathReviewCount.text = items[position].plannerReviewCount.toString()

        if (items.size - 1 == position) {
            holder.plannerPathLine.visibility = View.INVISIBLE
            holder.plannerPathTime.visibility = View.INVISIBLE
        } else {
            holder.plannerPathLine.visibility = View.VISIBLE
            holder.plannerPathTime.text = items[position].plannerTime
        }


    }


    class PlannerPathViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var plannerPathTime: TextView = itemView.findViewById(R.id.planner_path_time_item)
        var plannerPathNumber: TextView = itemView.findViewById(R.id.planner_path_number_item)
        var plannerPathLocation: TextView = itemView.findViewById(R.id.planner_path_location_name_item)
        var plannerPathAddress: TextView = itemView.findViewById(R.id.planner_path_address_item)
        var plannerPathRating: RatingBar = itemView.findViewById(R.id.planner_path_rating_item)
        var plannerPathReviewCount: TextView = itemView.findViewById(R.id.planner_path_review_count_item)
        var plannerPathLine: View = itemView.findViewById(R.id.planner_path_line_item) // 선
    }
}