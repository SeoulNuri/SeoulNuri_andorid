package com.hello.seoulnuri.view.course.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.icu.text.IDNA;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hello.seoulnuri.R;
import com.hello.seoulnuri.info.Info_Detail_Intro;
import com.hello.seoulnuri.view.info.InfoTourFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shineeseo on 2018. 8. 22..
 */

public class ExpandableListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int HEADER = 0;
    public static final int CHILD = 1;

    Context context;

    private List<Item> data;

    private int[] header_indicator = {R.drawable.order_1, R.drawable.order_2, R.drawable.order_3};

    int flag = 0; //header 판별 (소요시간)

    public ExpandableListAdapter(List<Item> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = null;

        context = parent.getContext();

        switch (type) {
            case HEADER:
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.course_head, parent, false);
                ListHeaderViewHolder header = new ListHeaderViewHolder(view);

                return header;

            case CHILD:
                inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.course_child, parent, false);
                ViewHolder child = new ViewHolder(view);

                return child;
        }
        return null;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Item item = data.get(position);
        float dp = context.getResources().getDisplayMetrics().density;
        Integer time = (Integer)item.course_time;

        switch (item.type) {
            case HEADER:
                final ListHeaderViewHolder itemController = (ListHeaderViewHolder) holder;

                itemController.header_title.setText(item.text);
                itemController.title_img.setImageResource(item.image);

                itemController.number.setImageResource(header_indicator[item.order]);

                itemController.btn_expand_toggle.setImageResource(R.drawable.ic_nextarrow_g);

                itemController.btn_expand_toggle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, Info_Detail_Intro.class);
//                        intent.putExtra("tour_idx", ) //서버에서 받은 값 전달
                        context.startActivity(intent);
                    }
                });
                flag = 0;

                break;
            case CHILD:
                final ViewHolder childController = (ViewHolder) holder;
                childController.childItem = data.get(position);
                childController.child_txt.setText(childController.childItem.text);
                LinearLayout.LayoutParams marginControl = (LinearLayout.LayoutParams)childController.line.getLayoutParams();


                if (flag == 0) {
                    childController.child_time_txt.setText(time + "분");
                    childController.child_time_txt.setVisibility(View.VISIBLE);
                    marginControl.leftMargin = (int) (21 * dp);
                    flag = 1;
                } else {
                    childController.child_time_txt.setVisibility(View.GONE);
                    marginControl.leftMargin = (int) (50 * dp);
                }
                if (time == 0) {
                    childController.child_time_txt.setVisibility(View.GONE);
                    marginControl.leftMargin = (int) (50 * dp);
                    childController.line.setVisibility(View.INVISIBLE);
                }
                childController.line.setLayoutParams(marginControl);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class ListHeaderViewHolder extends RecyclerView.ViewHolder {
        //headerViewHolder
        public ImageView number;
        public ImageView title_img;
        public TextView header_title;
        public ImageView btn_expand_toggle;

        public ListHeaderViewHolder(View itemView) {
            super(itemView);
            number = (ImageView) itemView.findViewById(R.id.number);
            title_img = (ImageView) itemView.findViewById(R.id.title_img);
            header_title = (TextView) itemView.findViewById(R.id.header_title);
            btn_expand_toggle = (ImageView) itemView.findViewById(R.id.btn_expand_toggle);
        }
    }
    private class ViewHolder extends RecyclerView.ViewHolder {
        //childViewHolder
        public ImageView oval;
        public TextView child_txt;
        public Item childItem;
        public View line;
        public TextView child_time_txt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            line = (View) itemView.findViewById(R.id.course_path_line_item);
            oval = (ImageView) itemView.findViewById(R.id.oval);
            child_txt = (TextView) itemView.findViewById(R.id.child_txt);
            child_time_txt = (TextView) itemView.findViewById(R.id.child_time_txt);
        }
    }
    public static class Item {
        //ArrayList<Item> data
        public int type; //header, child
        public String text;
        public int order; //header count
        public int image;
        public int course_time;

        public Item(int type, String text, int course_time) {
            this.type = type;
            this.text = text;
            this.course_time = course_time;
        }

        public Item(int type, String text, int image, int order) {
            this.type = type;
            this.text = text;
            this.image = image;
            this.order = order;
        }

        public Item(int type, String text ) {
            this.type = type;
            this.text = text;
        }
    }

}
