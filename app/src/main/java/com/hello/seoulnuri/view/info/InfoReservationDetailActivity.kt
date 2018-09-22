package com.hello.seoulnuri.view.info

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.view.View
import android.widget.LinearLayout
import com.hello.seoulnuri.R
import com.hello.seoulnuri.base.Init
import com.hello.seoulnuri.info.CommentActivity
import com.hello.seoulnuri.utils.custom.BookmarkDialog
import com.hello.seoulnuri.utils.custom.ShareDialog
import kotlinx.android.synthetic.main.activity_info_reservation_detail.*

class InfoReservationDetailActivity : AppCompatActivity(), View.OnClickListener, Init {
    override fun init() {
        reservation_detail_comment.setOnClickListener(this)
        reservation_detail_bookmark.setOnClickListener(this)
        reservation_detail_share.setOnClickListener(this)
        reservation_detail_location.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!){
            reservation_detail_comment->{
                startActivity(Intent(this, CommentActivity::class.java))
            }
            reservation_detail_bookmark->{
                val bookmark_dialog = BookmarkDialog(this@InfoReservationDetailActivity)
                bookmark_dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                bookmark_dialog.show()
            }
            reservation_detail_share->{
                val share_dialog : BottomSheetDialog = ShareDialog(this@InfoReservationDetailActivity)
                share_dialog.setContentView(ShareDialog.LAYOUT)
                share_dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                share_dialog.setTitle("공유하기")
                //shard_dialog.setCanceledOnTouchOutside(true)
                share_dialog.show()

            }
            reservation_detail_location->{


            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_reservation_detail)
        init()

    }
}
