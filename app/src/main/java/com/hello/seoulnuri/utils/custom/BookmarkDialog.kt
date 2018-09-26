package com.hello.seoulnuri.utils.custom

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.hello.seoulnuri.R
import com.hello.seoulnuri.base.Init
import com.hello.seoulnuri.network.ApplicationController
import com.hello.seoulnuri.network.NetworkService
import com.hello.seoulnuri.utils.SharedPreference
import kotlinx.android.synthetic.main.course_bookmark_dialog.*

/**
 * Created by VictoryWoo
 */
class BookmarkDialog(context: Context) : Dialog(context), View.OnClickListener, Init {
    override fun init() {
        btn_bookmark_ok.setOnClickListener(this)
        SharedPreference.instance!!.load(context!!)
        networkService = ApplicationController.instance!!.networkService

    }

    override fun onClick(v: View?) {
        when (v!!) {
            btn_bookmark_ok -> {
                requestTourBookmark()
                dismiss() // 혹은 여기서 통신 진행하거나
            }
        }
    }


    fun requestTourBookmark(){
    }
    lateinit var networkService: NetworkService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT)
        init()


    }

    companion object {
        val LAYOUT = R.layout.course_bookmark_dialog
    }
}