// info Detail intro
package com.hello.seoulnuri.info;


import android.app.Dialog;
import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.Touch;
import android.util.Log;
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
import com.hello.seoulnuri.Course_detail;
import com.hello.seoulnuri.R;
import com.hello.seoulnuri.model.InfoData;

import java.util.ArrayList;

public class Info_Detail_Intro extends AppCompatActivity{

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
            setContentView(R.layout.info_detail_intro);
            btn_stars = (RatingBar) findViewById(R.id.rb_infos);
            btn_cmt = (ImageButton)findViewById(R.id.btn_cmt);

            //ratingBar
            //추후 서버에서 데이터 받아옴
            btn_stars.setRating((float)3.5);


            btn_stars.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Intent intent = new Intent(Info_Detail_Intro.this, popUpActivity.class);
                    intent.putExtra("ratingbar_infos",btn_stars.getRating());
                    startActivityForResult(intent,1);

                    return false;
                }
            });

            /*// 서버 통신시 수정할 곳
            btn_stars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                }
            });*/

            btn_cmt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Info_Detail_Intro.this,CommentActivity.class);
                    // intent.
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
                    Intent intent = new Intent(Info_Detail_Intro.this, CourseMapActivity.class);
                    ArrayList<Double> listDouble = new ArrayList<Double>();
                    listDouble.add(37.600477);
                    listDouble.add(126.977507);
                    intent.putExtra("latLang_list", listDouble);
                    startActivity(intent);
                }
            });

            TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
            tabHost.setup();

            // tab1, cnt1
            TabHost.TabSpec t1 = tabHost.newTabSpec("Tab1");
            t1.setContent(R.id.cnt1);
            t1.setIndicator("소개");
            tabHost.addTab(t1);


            // tab2, cnt2
            TabHost.TabSpec t2 = tabHost.newTabSpec("Tab2");
            t2.setContent(R.id.cnt2);
            t2.setIndicator("이용방법");
            tabHost.addTab(t2);

            // tab3, cnt3
            TabHost.TabSpec t3 = tabHost.newTabSpec("Tab3");
            t3.setContent(R.id.cnt3);
            t3.setIndicator("무장애정보");
            tabHost.addTab(t3);

            //소개
            int titleimg = getIntent().getIntExtra("titleImage",0);
            String text = getIntent().getStringExtra("text");

            img = (ImageView)findViewById(R.id.img_intro);
            content = (TextView)findViewById(R.id.info_story);
            img.setImageResource(titleimg);
            content.setText(text);

            //이용방법
            manager = new LinearLayoutManager(this);
            recyclerview = (RecyclerView) findViewById(R.id.rv_notice);
            recyclerview.setLayoutManager(manager);

            //Info adapter 연결 일부만 투입
            datas = new ArrayList<InfoData>(); // 수정 필요
            InfoData[] data = new InfoData[3]; // new InfoData[jsons.size()]
            data[0] = new InfoData("이용 시간","11~2월 | 09:00 - 17:00\n" +
                    "3~5월・9~10월 | 09:00 - 18:00\n" +
                    "6~8월 | 09:00 - 18:30");
            data[1]=new InfoData("쉬는 날", "매주 화요일");
            data[2]=new InfoData("입장료", "대인(만 25세 - 64세) | 3,000원\n" +
                    "단체(10인 이상) | 2,400원 "+"\n"+"무료 | 만 6세 이하, 만 65세 이상\n" +
                    "한복 착용자, 매월 마지막주 수요일");

            for(int i=0;i<3;i++) { datas.add(data[i]);}

            guideAdapter adapter = new guideAdapter(this,datas);
            recyclerview.setAdapter(adapter);
            adapter.notifyDataSetChanged();


            //무장애정보
            manager = new LinearLayoutManager(this);
            recyclerview = (RecyclerView) findViewById(R.id.rv_withtour);
           // recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recyclerview.setLayoutManager(manager);

            //adapter 연결 일부만 투입
            datas2 = new ArrayList<InfoData>(); // 수정 필요
            InfoData[] data2 = new InfoData[6]; // new InfoData[jsons.size()]
            data2[0] = new InfoData("주차","장애인전용 주차구역 있음");
            data2[1]=new InfoData("대중교통", "지하철 접근 가능");
            data2[2]=new InfoData("교통로", "출입통로");
            data2[3] = new InfoData("출입통로","주출입구 무단차");
            data2[4] = new InfoData("화장실","장애인 화장실 있음");
            data2[5] = new InfoData("안내데스크","있음");


            for(int i=0;i<6;i++) { datas2.add(data2[i]);}

            withAdapter adapter2 = new withAdapter(this,datas2);
            recyclerview.setAdapter(adapter2);
            adapter2.notifyDataSetChanged();

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
                Toast toast = Toast.makeText(Info_Detail_Intro.this, "클립보드에 복사되었습니다", Toast.LENGTH_SHORT);
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

