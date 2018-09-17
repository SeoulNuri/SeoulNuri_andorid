package com.hello.seoulnuri.view.search

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hello.seoulnuri.R
import com.hello.seoulnuri.model.search.SearchTourData

/**
 * Created by VictoryWoo
 */
class SearchAdapter(var item_list : ArrayList<SearchTourData>, val context : Context) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private lateinit var onItemClick: View.OnClickListener

    fun setOnItemClickListener(l : View.OnClickListener){
        onItemClick = l
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_search, parent, false)
        view.setOnClickListener(onItemClick)
        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.v("1006",item_list.size.toString())
        return item_list.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.searchText.text = item_list[position].tour_name
    }

    class SearchViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var searchText : TextView = itemView.findViewById(R.id.rowTextView)
    }
}