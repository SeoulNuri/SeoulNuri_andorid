package com.hello.seoulnuri.view.info

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
                val bookmark_dialog = BookmarkDialog(this)
                bookmark_dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                bookmark_dialog.show()
            }
            reservation_detail_share->{
                val shard_dialog = ShareDialog(this)
                //shard_dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                shard_dialog.setCanceledOnTouchOutside(true)
                shard_dialog.show()

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
