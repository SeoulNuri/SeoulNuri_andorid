package com.hello.seoulnuri.info;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.hello.seoulnuri.commentItem;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class CommentActivity extends Activity implements OnClickListener {

    private ListView cmtlist;
    private EditText cmtEdit;
    private Date cmt_date;
    private Button btn_cmtin;
    private ArrayList<commentItem> cmt_infos;
    private LinearLayout layout_alllist;
    private RelativeLayout layout_footer;
    private CommentAdapter cmtAdapter;
    private commentItem cmt_info;
    private commentItem [] info;
    private TextView tv_cmtNum;
    private InputMethodManager inputManager;
    private LayoutInflater inflater;
    private  Window win;
    private LinearLayout.LayoutParams params;

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

        info = new commentItem[4]; //< -- 서버 데이터 넣을때 참고
        info[0] = new commentItem("이은경", "저는 야간개장 했을 때 가봤었는데, 정말정말정말\n" +
                "좋았어요! 낮도 좋지만 밤에는 더 예쁜거 같아요ㅎㅎ", Calendar.getInstance().getTime());
        info[1] = new commentItem("임지연", "좋은 여행지! 추천합니다. :)", Calendar.getInstance().getTime());

        info[2] = new commentItem("LULU", "가족들과 가서 행복한 시간을 보내고 왔습니다~\n" +
                "고궁 좋아하시는 분들 한 번 가보셔요 @}>->----", Calendar.getInstance().getTime());

        info[3] = new commentItem("서연", "저는 야간개장 했을 때 가봤었는데, 정말정말정말\n" +
                "좋았어요! 낮도 좋지만 밤에는 더 예쁜거 같아요ㅎㅎ", Calendar.getInstance().getTime());


        for(int i =0 ; i< info.length; i++) cmt_infos.add(info[i]);
        tv_cmtNum.setText(cmt_infos.size()+"");// <-- 이 부분 구현 필요

        cmtAdapter = new CommentAdapter(getApplicationContext(), R.layout.cmts_layout,cmt_infos);
        cmtlist.setAdapter(cmtAdapter);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_cmtin:

                String inputtext = cmtEdit.getText().toString();
                if(inputtext.equals("")){
                    Toast.makeText(CommentActivity.this, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show();
                }else{

                    //EditText의 빈칸이 없을 경우 등록!
                    cmt_info = new commentItem("닉네임(login.name)",inputtext, Calendar.getInstance().getTime());
                    cmtAdapter.addItem(cmt_info);

                    cmtAdapter.notifyDataSetChanged();
                    cmtEdit.setText(null);
                    //edittext 초기화
                }

                break;
        }

    }

}
