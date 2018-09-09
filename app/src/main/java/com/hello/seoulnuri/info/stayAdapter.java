
package com.hello.seoulnuri.info;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hello.seoulnuri.R;
import com.hello.seoulnuri.model.InfoData;
import com.hello.seoulnuri.model.InfoItem;

import java.util.ArrayList;

public class stayAdapter  extends RecyclerView.Adapter<stayAdapter.ItemViewHolder>{

    Context context;
    ArrayList<InfoItem> infoList;
    ArrayList<InfoData> datas;

    Drawable drawable;

    public stayAdapter(Context context, ArrayList<InfoItem> infoList, ArrayList<InfoData> datas) {
        this.context = context;
        this.infoList = infoList;
        this.datas = datas;
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //recycler view에 반복될 아이템 레이아웃 연결
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_view, null);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder viewHolder, int i) {
        final InfoItem item=infoList.get(i);
        final InfoData data = datas.get(i);

        Drawable drawable=context.getResources().getDrawable(item.getImage());
        viewHolder.image1.setBackground(drawable);

        viewHolder.title.setText(item.getTitle());
        viewHolder.ratingBar.setRating((float)item.getStar()); //star
        viewHolder.coment.setText("("+item.getCmt_cnt()+")"); // comment num
        viewHolder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Info_stay_detail.class);
                intent.putExtra("titleImage",data.getImage());
                intent.putExtra("text",data.getText());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.infoList.size();
    }

    public class ItemViewHolder  extends RecyclerView.ViewHolder {
        ImageView image1;
        TextView title;
        RatingBar ratingBar;
        TextView coment;
        CardView cardview;

        public ItemViewHolder(View itemView) {
            super(itemView);
            image1=(ImageView)itemView.findViewById(R.id.cd_img);
            title=(TextView)itemView.findViewById(R.id.tv_infotitle);
            ratingBar=(RatingBar)itemView.findViewById(R.id.rb_info);
            coment=(TextView)itemView.findViewById(R.id.tv_cmt);
            cardview=(CardView)itemView.findViewById(R.id.cinfo);
        }
    }
}

