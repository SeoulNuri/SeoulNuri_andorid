package com.hello.seoulnuri.view.info.tour

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
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
import com.hello.seoulnuri.model.info.tour.TourCommonData
import com.hello.seoulnuri.model.info.tour.introduce.InfoTourIntroduce
import com.hello.seoulnuri.model.info.tour.introduce.TourBottomData
import com.hello.seoulnuri.network.ApplicationController
import com.hello.seoulnuri.network.NetworkService
import com.hello.seoulnuri.utils.SharedPreference
import com.hello.seoulnuri.utils.custom.BookmarkDialog
import com.hello.seoulnuri.utils.custom.ShareDialog
import com.hello.seoulnuri.view.course.CourseMapActivity
import kotlinx.android.synthetic.main.activity_course.*
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
                info_tour_detail_bookmark.setImageResource(R.drawable.button_oval_bookmark_active)
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
            info_tour_detail_location->{
                val intent = Intent(this, TourMapActivity::class.java)
                intent.putExtra("tour_idx",tourCommonData.tour_idx)
                intent.putExtra("tour_name",tourCommonData.tour_name)
                intent.putExtra("tour_addr",tourCommonData.tour_addr)
                intent.putExtra("tour_star",tourCommonData.tour_star)
                intent.putExtra("tour_star_count",tourCommonData.tour_star_count)
                startActivity(intent)
            }
        }
    }


    override fun onResume() {
        super.onResume()
        init()
        getData()
        //requestDetailInfomation()
        if(booked == 1)
            info_tour_detail_bookmark.isSelected = true
        else if(booked == 0)
            info_tour_detail_bookmark.isSelected = false

    }

    fun changeImageButton(){

        info_tour_detail_bookmark.setImageResource(R.drawable.button_oval_bookmark_active)
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
        //transaction.addToBackStack(null)
        transaction.commit()
    }


    override fun init() {
        info_tour_detail_comment.setOnClickListener(this)
        info_tour_detail_bookmark.setOnClickListener(this)
        info_tour_detail_share.setOnClickListener(this)
        info_tour_detail_location.setOnClickListener(this)
        networkService = ApplicationController.instance!!.networkService
        SharedPreference.instance!!.load(this)
        //replaceFragment(InfoTourUseFragment())
        //replaceFragment(InfoTourFaultInfomationFragment())


    }


    fun getData() {
        var getIntent = getIntent()
        index = getIntent.getIntExtra("index", 0)
        booked = getIntent.getIntExtra("tour_booked",0)
        Log.v("woovic index", index.toString())
        Log.v("woovic booked", booked.toString())

        if(booked == 1)
            info_tour_detail_bookmark.isSelected = true
        else if(booked == 0)
            info_tour_detail_bookmark.isSelected = false
    }


    var index = 0
    var booked = 0
    lateinit var networkService: NetworkService
    lateinit var tourBottomData: TourBottomData
    lateinit var detailImage :String
    lateinit var detailText  :String
    lateinit var tourCommonData: TourCommonData
    lateinit var progressDialog : ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_tour_detail)

        init()
        getData()
        progressDialog = ProgressDialog(this,R.style.AppCompatAlertDialogStyle)


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
                    0 -> {
                        createDialog()
                        setData(detailImage, detailText)
                    }
                    1 -> {
                        createDialog()
                        replaceFragment(InfoTourUseFragment())
                    }
                    2 -> {
                        createDialog()
                        replaceFragment(InfoTourFaultInfomationFragment())
                    }
                }
            }

        })


    }

    fun createDialog(){
        //dialog.setTitle("Loading ...")
        progressDialog.setMessage("Please wait ...")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()
        val handler = Handler()

        handler.postDelayed({
            progressDialog.dismiss()

        }, 1000)


    }

    fun requestDetailInfomation() {
        val DetailResponse = networkService.getInfoTourIntroduce(SharedPreference.instance!!.getPrefStringData("data")!!, index)
        DetailResponse.enqueue(object : Callback<InfoTourIntroduce> {
            override fun onFailure(call: Call<InfoTourIntroduce>?, t: Throwable?) {


            }

            override fun onResponse(call: Call<InfoTourIntroduce>?, response: Response<InfoTourIntroduce>?) {
                if (response!!.isSuccessful) {
                    // 뷰에 값 뿌리기
                    tourCommonData = response!!.body()!!.data.tour_common
                    settingTourCommonData()

                    // 함수로 빼기!
                    /*info_tour_detail_title.text = response!!.body()!!.data.tour_common.tour_name
                    info_tour_detail_address.text = response!!.body()!!.data.tour_common.tour_addr
                    info_tour_detail_intro_rating.rating = response!!.body()!!.data.tour_common.tour_star.toFloat()
                    info_tour_detail_count.text = "(${response!!.body()!!.data.tour_common.tour_star_count})"*/

                    tourBottomData = response!!.body()!!.data.tour_bottom
                    if(tourBottomData.tour_info_img == null)
                        detailImage = R.drawable.img_jw.toString()
                    else
                        detailImage = tourBottomData.tour_info_img

                    if(tourBottomData.tour_info_detail == "없음")
                        detailText = "비어 있는 값입니다."
                    else
                        detailText = response!!.body()!!.data.tour_bottom.tour_info_detail

                    //SharedPreference.instance!!.setPrefData("tour_booked",response!!.body()!!.data!!.tour_common.tour_booked)
                    //Log.v("woo tour_booked", response!!.body()!!.data!!.tour_common.tour_booked.toString())
                    Log.v("woo tour_name", response!!.body()!!.data!!.tour_common.tour_name)
                    setData(detailImage,detailText)


                }
            }

        })

    }

    fun settingTourCommonData(){
        info_tour_detail_title.text = tourCommonData.tour_name
        info_tour_detail_address.text = tourCommonData.tour_addr
        info_tour_detail_intro_rating.rating = tourCommonData.tour_star.toFloat()
        info_tour_detail_count.text = "(${tourCommonData.tour_star_count})"
        SharedPreference.instance!!.setPrefData("tour_booked",tourCommonData.tour_booked)

    }


}
