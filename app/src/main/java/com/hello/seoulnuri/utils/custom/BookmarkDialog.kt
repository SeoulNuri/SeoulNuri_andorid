package com.hello.seoulnuri.utils.custom

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.hello.seoulnuri.R
import com.hello.seoulnuri.base.Init
import kotlinx.android.synthetic.main.course_bookmark_dialog.*

/**
 * Created by VictoryWoo
 */
class BookmarkDialog(context : Context) : Dialog(context), View.OnClickListener,Init{
    override fun init() {
        btn_bookmark_ok.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!){
            btn_bookmark_ok->{
                dismiss() // 혹은 여기서 통신 진행하거나
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT)
        init()


    }

    companion object {
        val LAYOUT = R.layout.course_bookmark_dialog
    }
}