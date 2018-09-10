package com.hello.seoulnuri.info;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RatingBar;

import com.hello.seoulnuri.R;

public class popUpActivity extends Activity {

    RatingBar rb_infos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없앰
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_stars);
        rb_infos = (RatingBar)findViewById(R.id.rb_big_star);

        //data 가져오기
        Intent intent =getIntent();
        float data = intent.getFloatExtra("ratingbar_infos",0);
        Log.e("rating infos", data + " dd ");
        getWindow().setBackgroundDrawable(new PaintDrawable(Color.TRANSPARENT));
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = (int) (display.getWidth() * 0.7); //Display 사이즈의 70%
        int height = (int) (display.getHeight() * 0.9);  //Display 사이즈의 90%
        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;

        rb_infos.setRating(data);

    }

    public void mEvaluate(View v){
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("result",rb_infos.getRating());
        setResult(RESULT_OK,intent);

        finish();
    }

    @Override
    public void onBackPressed() {
        return ;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
            return false;
        }

        return true;
    }
}
