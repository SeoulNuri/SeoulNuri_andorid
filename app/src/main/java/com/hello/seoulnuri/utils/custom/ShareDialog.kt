package com.hello.seoulnuri.utils.custom

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.view.View
import com.hello.seoulnuri.R

/**
 * Created by VictoryWoo
 */
class ShareDialog(context : Context) : BottomSheetDialog(context), View.OnClickListener{
    override fun onClick(v: View?) {
        when(v!!){

        }
    }

    lateinit var bottomSheetDialog: BottomSheetDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomSheetDialog = BottomSheetDialog(context)
        bottomSheetDialog.setContentView(LAYOUT)
        bottomSheetDialog.setTitle("공유하기")
        //bottomSheetDialog.show()


    }

    companion object {
        val LAYOUT = R.layout.custom_share_dialog

    }
}