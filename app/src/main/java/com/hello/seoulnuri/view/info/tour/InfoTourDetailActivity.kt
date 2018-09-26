package com.hello.seoulnuri.view.info.tour

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import com.hello.seoulnuri.R
import com.hello.seoulnuri.base.Init
import com.hello.seoulnuri.info.CommentActivity
import com.hello.seoulnuri.model.info.tour.InfoTourResponse
import com.hello.seoulnuri.model.info.tour.introduce.InfoTourIntroduce
import com.hello.seoulnuri.model.info.tour.introduce.TourBottomData
import com.hello.seoulnuri.network.ApplicationController
import com.hello.seoulnuri.network.NetworkService
import com.hello.seoulnuri.utils.SharedPreference
import com.hello.seoulnuri.utils.custom.BookmarkDialog
import com.hello.seoulnuri.utils.custom.ShareDialog
import kotlinx.android.synthetic.main.activity_info_tour_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class InfoTourDetailActivity : AppCompatActivity(), Init, InfoTourIntroduceFragment.setting, View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!){
            info_tour_detail_comment->{
                startActivity(Intent(this, CommentActivity::class.java))
            }

            info_tour_detail_bookmark->{
                val bookmark_dialog = BookmarkDialog(this@InfoTourDetailActivity)
                bookmark_dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                bookmark_dialog.show()
            }
            info_tour_detail_share->{
                val share_dialog : BottomSheetDialog = ShareDialog(this@InfoTourDetailActivity)
                share_dialog.setContentView(ShareDialog.LAYOUT)
                share_dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                share_dialog.setTitle("공유하기")
                //shard_dialog.setCanceledOnTouchOutside(true)
                share_dialog.show()
            }
        }
    }

    override fun setData(introImage: String, introContent: String) {
        Log.v("woo 7","7")
        makeIntroFragment(introImage, introContent)
    }

    fun makeIntroFragment(introImage: String, introContent: String) {
        Log.v("woo 8","8")
        anotherFragment(introImage, introContent)
        Log.v("makeIntroFragment woo",introImage)
        Log.v("makeIntroFragment woo",introContent)
    }

    fun anotherFragment(image: String, detail: String) {
        Log.v("woo 8","8")
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        Log.v("woo 9","9")
        var infoTourIntroduceFragment = InfoTourIntroduceFragment.newFragment(image, detail)
        Log.v("woo 10","10")
        Log.v("anothreFragment woo",image)
        Log.v("anothreFragment woo",detail)
        transaction.replace(R.id.info_tour_frame, infoTourIntroduceFragment)
        Log.v("woo 11","11")
        transaction.commit()
    }

    fun replaceFragment(fragment: Fragment) {
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.replace(R.id.info_tour_frame, fragment)
        transaction.commit()
    }


    override fun init() {
        info_tour_detail_comment.setOnClickListener(this)
        info_tour_detail_bookmark.setOnClickListener(this)
        info_tour_detail_share.setOnClickListener(this)
        networkService = ApplicationController.instance!!.networkService
        SharedPreference.instance!!.load(this)
    }


    fun getData() {
        var getIntent = getIntent()
        index = getIntent.getIntExtra("index", 0)
        Log.v("woovic", index.toString())
    }


    var index = 0
    lateinit var networkService: NetworkService
    lateinit var tourBottom: TourBottomData
    lateinit var detailImage :String
    lateinit var detailText  :String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_tour_detail)

        init()
        getData()

        info_tour_detail_tab.addTab(info_tour_detail_tab.newTab().setText("소개"))
        info_tour_detail_tab.addTab(info_tour_detail_tab.newTab().setText("이용방법"))
        info_tour_detail_tab.addTab(info_tour_detail_tab.newTab().setText("무장애정보"))

        requestDetailInfomation()
        //replaceFragment(InfoTourIntroduceFragment())

        // 선택된 혹은 선택되지 않은 탭의 색상 변화
        info_tour_detail_tab.setTabTextColors(
                ContextCompat.getColor(this, R.color.unselected_text_color), // 선택되지 않은 텍스트 컬러
                ContextCompat.getColor(this, R.color.selected_text_color) // 선택된 텍스트 컬러
        )


        // 탭이 선택될 때 프래그먼트 생성!
        info_tour_detail_tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> setData(detailImage, detailText)
                    1 -> replaceFragment(InfoTourUseFragment())
                    2 -> replaceFragment(InfoTourFaultInfomationFragment())
                }
            }

        })


    }

    fun requestDetailInfomation() {
        val DetailResponse = networkService.getInfoTourIntroduce(SharedPreference.instance!!.getPrefStringData("data")!!, index)
        DetailResponse.enqueue(object : Callback<InfoTourIntroduce> {
            override fun onFailure(call: Call<InfoTourIntroduce>?, t: Throwable?) {


            }

            override fun onResponse(call: Call<InfoTourIntroduce>?, response: Response<InfoTourIntroduce>?) {
                if (response!!.isSuccessful) {
                    info_tour_detail_title.text = response!!.body()!!.data.tour_common.tour_name
                    info_tour_detail_address.text = response!!.body()!!.data.tour_common.tour_addr
                    info_tour_detail_intro_rating.rating = response!!.body()!!.data.tour_common.tour_star.toFloat()
                    info_tour_detail_count.text = "(${response!!.body()!!.data.tour_common.tour_star_count})"


                    detailImage = response!!.body()!!.data.tour_bottom.tour_image
                    detailText = response!!.body()!!.data.tour_bottom.tour_info_detail
                    setData(detailImage,detailText)


                }
            }

        })

    }


}
