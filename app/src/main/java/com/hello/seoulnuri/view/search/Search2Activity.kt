package com.hello.seoulnuri.view.search

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.hello.seoulnuri.R
import com.hello.seoulnuri.base.Init
import com.hello.seoulnuri.model.bookmark.BookmarkListData
import com.hello.seoulnuri.model.bookmark.BookmarkListResponse
import com.hello.seoulnuri.model.search.SearchTourData
import com.hello.seoulnuri.network.ApplicationController
import com.hello.seoulnuri.network.NetworkService
import com.hello.seoulnuri.utils.SharedPreference
import com.hello.seoulnuri.utils.ToastMaker
import com.hello.seoulnuri.view.planner.PlannerStartActivity
import kotlinx.android.synthetic.main.activity_search2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class Search2Activity : AppCompatActivity(), Init, View.OnClickListener, TextView.OnEditorActionListener {
    override fun onEditorAction(textView: TextView?, actionId: Int, event: KeyEvent?): Boolean {

        if(textView!!.id == R.id.searchText && actionId == EditorInfo.IME_ACTION_DONE){
            requestSearchResponse(searchText.text.toString())
        }
        return false
    }

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
            searchBtn->{
                val resultText = searchContentEditText.text.toString()

                if(intent.getStringExtra("from")!=null){
                    val intent = Intent(this, PlannerStartActivity::class.java)
                    intent.putExtra("result",resultText)
                    setResult(100,intent)
                    finish()
                }else{
                    Log.v("yong","elseselsleslels")
                }



                //startActivity(intent)
                requestSearchResponse(searchContentEditText.text.toString())
            }
            v!!->{
                val itemListIndex = searchRecyclerView.getChildAdapterPosition(v!!)
                ToastMaker.makeLongToast(this, bookmarkListItems[itemListIndex].tour_idx.toString())
            }
        }
    }



    override fun init() {
        networkService = ApplicationController.instance!!.networkService
        SharedPreference.instance!!.load(this)
        searchBackBtn.setOnClickListener(this)
        searchCancelBtn.setOnClickListener(this)
        searchBtn.setOnClickListener(this)
    }

    lateinit var searchAdapter: SearchAdapter
    lateinit var searchItems: ArrayList<SearchTourData>
    lateinit var networkService: NetworkService
    lateinit var bookmarkListItems : ArrayList<BookmarkListData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search2)

        init() // 초기화
        searchItems = ArrayList()
        bookmarkListItems = ArrayList()


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
                //requestSearchResponse(searchText.text.toString())
            }

        })

        requestBookmarkList()
        //requestSearchResponse(searchContentEditText.text.toString())
    }

    // 북마크 리스트 불러오기
    fun requestBookmarkList(){
        var bookmarkListResponse = networkService.getBookmarkList(SharedPreference.instance!!.getPrefStringData("data")!!)
        bookmarkListResponse.enqueue(object : Callback<BookmarkListResponse>{
            override fun onFailure(call: Call<BookmarkListResponse>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<BookmarkListResponse>?, response: Response<BookmarkListResponse>?) {
                if(response!!.isSuccessful){
                    Log.v("1013",response!!.code().toString())
                    Log.v("1013",response!!.body()!!.data.size.toString())
                    bookmarkListItems = response!!.body()!!.data
                    searchAdapter = SearchAdapter(bookmarkListItems, this@Search2Activity)
                    searchAdapter.setOnItemClickListener(this@Search2Activity)
                    searchRecyclerView.layoutManager = LinearLayoutManager(this@Search2Activity)
                    searchRecyclerView.adapter = searchAdapter
                }else{
                    Log.v("1014",response!!.code().toString())
                }
            }

        })
    }

    // 메인에서 search
    fun requestSearchResponse(word: String) {
        Log.v("1051", "search 액티비티 함수")
        val searchData = networkService.getMainSearchData(SharedPreference.instance!!.getPrefStringData("data")!!, word)
        Log.v("1051", "search 액티비티 함수2")
        searchData.enqueue(object : Callback<BookmarkListResponse> {
            override fun onFailure(call: Call<BookmarkListResponse>?, t: Throwable?) {
                Log.v("1051", "search 액티비티 통신 실패할 때")
            }

            override fun onResponse(call: Call<BookmarkListResponse>?, response: Response<BookmarkListResponse>?) {
                if (response!!.isSuccessful) {
                    Log.v("1051", "search 액티비티 통신 실패할 때 ${response!!.message()}")
                    bookmarkListItems = response!!.body()!!.data
                    searchAdapter = SearchAdapter(bookmarkListItems,this@Search2Activity)
                    searchAdapter.setOnItemClickListener(this@Search2Activity)
                    searchAdapter.notifyDataSetChanged()
                    searchRecyclerView.layoutManager = LinearLayoutManager(this@Search2Activity)
                    searchRecyclerView.adapter = searchAdapter
                }else{
                    Log.v("1052", "search success else ${response!!.code()}")
                    Log.v("10522", response!!.body()!!.message)
                    Log.v("1052", SharedPreference.instance!!.getPrefStringData("data")!!)
                    Log.v("1052", word.toString())

                }
            }

        })
    }

}
