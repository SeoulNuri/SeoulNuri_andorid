package com.hello.seoulnuri.view.search

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.hello.seoulnuri.R
import com.hello.seoulnuri.base.Init
import com.hello.seoulnuri.model.search.SearchResponse
import com.hello.seoulnuri.model.search.SearchTourData
import com.hello.seoulnuri.network.ApplicationController
import com.hello.seoulnuri.network.NetworkService
import com.hello.seoulnuri.utils.SharedPreference
import com.hello.seoulnuri.utils.ToastMaker
import kotlinx.android.synthetic.main.activity_search2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Search2Activity : AppCompatActivity(), Init, View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!){
            searchBackBtn->{
                finish()
            }
            searchCancelBtn->{
                searchContentEditText.setText(" ")
                searchCancelBtn.visibility = View.INVISIBLE
                searchText.text = "즐겨찾기"
            }
            v!!->{
                val itemListIndex = searchRecyclerView.getChildAdapterPosition(v!!)
                ToastMaker.makeLongToast(this, searchItems[itemListIndex].tour_idx.toString())
            }
        }
    }

    override fun init() {
        networkService = ApplicationController.instance!!.networkService
        SharedPreference.instance!!.load(this)
        searchBackBtn.setOnClickListener(this)
        searchCancelBtn.setOnClickListener(this)
    }

    lateinit var searchAdapter: SearchAdapter
    lateinit var searchItems: ArrayList<SearchTourData>
    lateinit var networkService: NetworkService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search2)

        init() // 초기화
        searchItems = ArrayList()

        searchContentEditText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(e: Editable?) {
                if(e!!.length == 0){
                    searchText.text = "즐겨찾기"
                    searchCancelBtn.visibility = View.INVISIBLE
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchCancelBtn.visibility = View.VISIBLE
                searchText.text = "검색 결과"

            }

        })

        requestSearchResponse(searchContentEditText.text.toString())
    }

    fun requestSearchResponse(word: String) {
        val searchData = networkService
                .getMainSearchData(SharedPreference.instance!!.getPrefStringData("data")!!
                        , word)

        searchData.enqueue(object : Callback<SearchResponse> {
            override fun onFailure(call: Call<SearchResponse>?, t: Throwable?) {
                Log.v("945", t!!.message)
            }

            override fun onResponse(call: Call<SearchResponse>?, response: Response<SearchResponse>?) {
                if (response!!.isSuccessful) {
                    Log.v("946", response!!.message())
                    searchItems = response!!.body()!!.data
                    searchAdapter = SearchAdapter(searchItems,this@Search2Activity)
                    searchAdapter.setOnItemClickListener(this@Search2Activity)
                    searchRecyclerView.layoutManager = LinearLayoutManager(this@Search2Activity)
                    searchRecyclerView.adapter = searchAdapter

                }
            }

        })
    }

}
