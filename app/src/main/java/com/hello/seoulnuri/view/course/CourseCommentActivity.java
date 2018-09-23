package com.hello.seoulnuri.view.course;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hello.seoulnuri.R;
import com.hello.seoulnuri.base.BaseModel;
import com.hello.seoulnuri.commentItem;
import com.hello.seoulnuri.info.CommentAdapter;
import com.hello.seoulnuri.model.CourseItem;
import com.hello.seoulnuri.model.course.CourseCmtData;
import com.hello.seoulnuri.model.course.CourseCmtRequest;
import com.hello.seoulnuri.model.course.CourseCmtResponse;
import com.hello.seoulnuri.model.course.CourseStarResponse;
import com.hello.seoulnuri.network.ApplicationController;
import com.hello.seoulnuri.network.NetworkService;
import com.hello.seoulnuri.utils.SharedPreference;
import com.hello.seoulnuri.view.course.adapter.CourseAdapter;
import com.hello.seoulnuri.view.course.adapter.CourseCommentAdapter;
import com.kakao.auth.Session;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CourseCommentActivity extends Activity implements OnClickListener {

    private ListView cmtlist;
    private EditText cmtEdit;
    private Date cmt_date;
    private Button btn_cmtin;
    private ArrayList<commentItem> cmt_infos;
    private LinearLayout layout_alllist;
    private RelativeLayout layout_footer;
    private CourseCommentAdapter cmtAdapter;
    private commentItem cmt_info;
    private commentItem [] info;
    private TextView tv_cmtNum;
    private InputMethodManager inputManager;
    private LayoutInflater inflater;
    private  Window win;
    private LinearLayout.LayoutParams params;
    private NetworkService networkService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        win = getWindow();
        win.setContentView(R.layout.commentlayout);

        inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cmtlist = (ListView)findViewById(R.id.cmt_all);
        cmt_infos = new ArrayList<commentItem>();
        layout_footer = (RelativeLayout)inflater.inflate(R.layout.cmts_footer,null);

        cmtEdit =(EditText)layout_footer.findViewById(R.id.cmt_edit);
        win.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,WindowManager.LayoutParams.FLAG_BLUR_BEHIND);;

        layout_alllist = (LinearLayout)findViewById(R.id.l_cmt_all);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
        addContentView(layout_footer, params);

        inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        layout_alllist.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                inputManager.hideSoftInputFromWindow(cmtEdit.getWindowToken(),0);
                btn_cmtin.setTextColor(Color.parseColor("#2c2c2c"));
                layout_footer.setBackgroundColor(Color.parseColor("#00000000"));
                cmtEdit.clearFocus();
                /*FIXME
                * inputManager.hideSoftInputFromWindow(cmtEdit.getWindowToken(),0);
                * cmtEdit.clearFocus();
                * 위의 코드 두 줄이 키보드 올라왔을 때 바깥 배경 클릭하면 키보드 내려가게 하는 코드!
                 * */
            }
        });

        cmtlist.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                inputManager.hideSoftInputFromWindow(cmtEdit.getWindowToken(),0);
                btn_cmtin.setTextColor(Color.parseColor("#2c2c2c"));
                layout_footer.setBackgroundColor(Color.parseColor("#00000000"));
                cmtEdit.clearFocus();
            }
        });

        cmtEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @SuppressLint("Range")
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(hasFocus == true){
                    btn_cmtin.setTextColor(Color.parseColor("#FFFF6E2C"));
                    layout_footer.setBackgroundColor(Color.parseColor("#88ffffff"));

                }
            }
        });
        btn_cmtin =(Button)layout_footer.findViewById(R.id.btn_cmtin);
        btn_cmtin.setOnClickListener(this);

        cmt_date = Calendar.getInstance().getTime(); //추후 수정 findViewById(R.id.tv_cmt_date) ;
        tv_cmtNum = (TextView)findViewById(R.id.tv_cmtNum);

        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance();
        Networking();



    }
    public void Networking(){
        Intent intent = getIntent();
        int idx = intent.getIntExtra("course_idx",1);

        Log.v("idx" , "idx = " + idx);
        Call<CourseCmtResponse> requestDetail = networkService.getCourseCmt(idx);
        requestDetail.enqueue(new Callback<CourseCmtResponse>() {
            @Override
            public void onResponse(Call<CourseCmtResponse> call, Response<CourseCmtResponse> response) {
                if(response.isSuccessful()) {
                    ArrayList<CourseCmtData> courseCmtData;
                    courseCmtData = response.body().getData();

                    Log.v("courseCmtData", courseCmtData.get(1).toString());

                    info = new commentItem[courseCmtData.size()]; //< -- 서버 데이터 넣을때 참고
                    for (int i = 0; i < courseCmtData.size(); i++) {
                        info[i] = new commentItem(courseCmtData.get(i).getUser_nickname(),courseCmtData.get(i).getContents() );
                    }

                    for (int i = 0; i < info.length; i++) {
                        Log.v("info_cmmt", info[i].getNickname());
                    }
                    for(int i =0 ; i< info.length; i++) cmt_infos.add(info[i]);
                    tv_cmtNum.setText(cmt_infos.size()+"");// <-- 이 부분 구현 필요

                    cmtAdapter = new CourseCommentAdapter(getApplicationContext(), R.layout.cmts_layout,cmt_infos);
                    cmtlist.setAdapter(cmtAdapter);
                }

            }

            @Override
            public void onFailure(Call<CourseCmtResponse> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }

    public void PostNetworking(String inputText){
        Intent intent = getIntent();
        int idx = intent.getIntExtra("course_idx",1);

        Log.v("idx" , "idx = " + idx);
        CourseCmtRequest courseCmtRequest = new CourseCmtRequest(idx, inputText);
//        if (SharedPreference.Companion.getInstance().getPrefStringData("data") != null) {
//            Call<BaseModel> requestDetail = networkService.postCourseCmt(SharedPreference.Companion.getInstance().getPrefStringData("data"),courseCmtRequest);
//
//        }
        
//        if (Session.getCurrentSession().isOpened()) {
//            Call<BaseModel> requestDetail = networkService.postCourseCmt(Session.getCurrentSession().getTokenInfo().getAccessToken(),courseCmtRequest);
//        }
        Call<BaseModel> requestDetail = networkService.postCourseCmt(courseCmtRequest);

        requestDetail.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                if(response.isSuccessful()) {

                    Log.v("course comment code", response.body().getCode().toString());
                    Log.v("course comment status", response.body().getStatus().toString());
                    Log.v("course comment message", response.body().getMessage().toString());

                }

            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_cmtin:
                String inputText = cmtEdit.getText().toString();
                PostNetworking(inputText);

                if(inputText.equals("")){
                    Toast.makeText(CourseCommentActivity.this, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show();
                }else{

                    //EditText의 빈칸이 없을 경우 등록!
                    cmt_info = new commentItem("닉네임(login.name)",inputText, Calendar.getInstance().getTime());
                    cmtAdapter.addItem(cmt_info);

                    cmtAdapter.notifyDataSetChanged();
                    cmtEdit.setText(null);
                    //edittext 초기화
                }

                break;
        }

    }

}
