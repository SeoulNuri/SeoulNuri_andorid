// info Detail intro
package com.hello.seoulnuri.info;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.hello.seoulnuri.CourseMapActivity;
import com.hello.seoulnuri.R;
import com.hello.seoulnuri.model.InfoData;

import java.util.ArrayList;

public class Info_stay_detail extends AppCompatActivity{

    private RecyclerView recyclerview;
    private ArrayList<InfoData> datas;
    private ArrayList<InfoData> datas2;
    private LinearLayoutManager manager;
    private ImageView img;
    private TextView content;
    private RatingBar btn_stars;
    private ImageButton btn_cmt;
    private ImageButton btn_course_bookmark;
    private ImageButton btn_course_share;
    private ImageButton btn_course_map;
    private Dialog bookmark_Dialog;
    private BottomSheetDialog link_share_dialog;
    private Button btn_bookmark_ok;
    ViewGroup course_kakao_share;
    ViewGroup course_link_share;
    ViewGroup course_more_share;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_detail_stay);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        btn_stars = (RatingBar) findViewById(R.id.rb_stay);
        btn_cmt = (ImageButton)findViewById(R.id.btn_cmt);

        //ratingBar
        //추후 서버에서 데이터 받아옴
        btn_stars.setRating((float)3.5);


        btn_stars.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(Info_stay_detail.this, popUpActivity.class);
                intent.putExtra("ratingbar_infos",btn_stars.getRating());
                startActivityForResult(intent,1);

                return false;
            }
        });

        // 서버 통신시 수정할 곳
        /*btn_stars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

            }
        });*/

        btn_cmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Info_stay_detail.this,CommentActivity.class);
                startActivity(intent);
            }
        });

        btn_course_bookmark = (ImageButton) findViewById(R.id.btn_bookmark);

        btn_course_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Course_bookmark_custom_dialog();
            }
        });

        btn_course_share = (ImageButton) findViewById(R.id.btn_share);

        btn_course_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Course_link_share_custom_dialog();
            }
        });

        btn_course_map = (ImageButton) findViewById(R.id.btn_map);

        btn_course_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Info_stay_detail.this, CourseMapActivity.class);
                ArrayList<Double> listDouble = new ArrayList<Double>();
                listDouble.add(37.600477);
                listDouble.add(126.977507);
                intent.putExtra("latLang_list", listDouble);
                startActivity(intent);
            }
        });



        // tab1, cnt1
        TabHost.TabSpec t1 = tabHost.newTabSpec("Tab1");
        t1.setContent(R.id.cnt_stay);
        t1.setIndicator("소개");
        tabHost.addTab(t1);

        //소개
        int titleimg = getIntent().getIntExtra("titleImage",0);
        String text = getIntent().getStringExtra("text");

        img = (ImageView)findViewById(R.id.img_stay);
        content = (TextView)findViewById(R.id.stay_story);
        img.setImageResource(titleimg);
        content.setText(text);

    }

    public void Course_bookmark_custom_dialog() {
        bookmark_Dialog = new Dialog(this);
        bookmark_Dialog.setContentView(R.layout.course_bookmark_dialog);
        bookmark_Dialog.setTitle("bookmark dialog");

        btn_bookmark_ok = (Button) bookmark_Dialog.findViewById(R.id.btn_bookmark_ok);


        btn_bookmark_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookmark_Dialog.cancel();
            }
        });

        bookmark_Dialog.show();
    }

    public void Course_link_share_custom_dialog() {

        link_share_dialog = new BottomSheetDialog(this);
        link_share_dialog.setContentView(R.layout.course_share_dialog);
        link_share_dialog.setTitle("공유하기");

        course_kakao_share = (ViewGroup) link_share_dialog.findViewById(R.id.course_kakao_share);

        course_kakao_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        course_link_share = (ViewGroup) link_share_dialog.findViewById(R.id.course_link_share);
        course_link_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        course_more_share = (ViewGroup) link_share_dialog.findViewById(R.id.course_more_share);
        course_more_share.setOnClickListener(new View.OnClickListener() {
            LayoutInflater inflater = getLayoutInflater();
            final View layout = inflater.inflate(R.layout.custom_toast,(ViewGroup)findViewById(R.id.toast_custom));

            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(Info_stay_detail.this, "클립보드에 복사되었습니다", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, -100);
                toast.setView(layout);
                toast.show();
            }
        });
        link_share_dialog.show();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                //데이터 받기
                Float result = data.getFloatExtra("result",0);
                btn_stars.setRating(result);
                // 서버에 데이터 보내기
            }
        }
    }
}

