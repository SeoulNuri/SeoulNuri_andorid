package com.hello.seoulnuri;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by shineeseo on 2018. 8. 18..
 */

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    Context context;
    ArrayList<CourseItem> courseList; //공지사항 정보 담겨있음

    public CourseAdapter(Context context, ArrayList<CourseItem> courseList) {
        this.context = context;
        this.courseList = courseList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //recycler view에 반복될 아이템 레이아웃 연결
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_card_item, null);
        return new ViewHolder(v);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
/** 정보 및 이벤트 처리는 이 메소드에서 구현 **/
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CourseItem item=courseList.get(position);

        Drawable drawable=context.getResources().getDrawable(item.getImage());
        holder.image1.setBackground(drawable);

        drawable=context.getResources().getDrawable(item.getIcon());
        holder.image2.setBackground(drawable);
        holder.title.setText(item.getContent());
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Course_detail.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.courseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image1;
        ImageView image2;
        TextView title;
        CardView cardview;

        public ViewHolder(View itemView) {
            super(itemView);
            image1=(ImageView)itemView.findViewById(R.id.iv_image);
            image2 = (ImageView) itemView.findViewById(R.id.iv_icon);
            title=(TextView)itemView.findViewById(R.id.tv_content);
            cardview=(CardView)itemView.findViewById(R.id.cv);
        }
    }
}


