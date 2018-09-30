package com.hello.seoulnuri.utils.custom

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.hello.seoulnuri.R
import kotlinx.android.synthetic.main.custom_share_dialog.*

/**
 * Created by VictoryWoo
 */
class ShareDialog(context: Context) : BottomSheetDialog(context), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!) {
            share_more_btn -> {
                var inflater = layoutInflater
                var viewGroup = findViewById<ViewGroup>(R.id.toast_custom)
                val layout = inflater.inflate(R.layout.custom_toast,viewGroup )


                       /* LayoutInflater.from(context)
                        .inflate(R.layout.custom_toast, findViewById<View>(R.id.toast_custom) as ViewGroup,false)
*/

                val toast = Toast.makeText(context, "클립보드에 복사되었습니다", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, -100)
                toast.view = layout
                toast.show()
            }
        }
    }

    lateinit var bottomSheetDialog: BottomSheetDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomSheetDialog = BottomSheetDialog(context)
        bottomSheetDialog.setContentView(LAYOUT)
        bottomSheetDialog.setTitle("공유하기")
        //bottomSheetDialog.show()
        share_more_btn.setOnClickListener(this)


    }

    companion object {
        val LAYOUT = R.layout.custom_share_dialog

    }
}