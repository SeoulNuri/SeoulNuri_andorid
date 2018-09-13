package com.hello.seoulnuri.view.course.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
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

    int select_type;

    int cnt = 0;

    public ExpandableListAdapter(int type, List<Item> data) {
        select_type = type;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = null;

        context = parent.getContext();
        float dp = context.getResources().getDisplayMetrics().density;
        int subItemPaddingLeft = (int) (18 * dp);
        int subItemPaddingTopAndBottom = (int) (5 * dp);
        switch (type) {
            case HEADER:
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.course_head, parent, false);
                ListHeaderViewHolder header = new ListHeaderViewHolder(view);
                cnt = 0;
                return header;

            case CHILD:
//                TextView itemTextView = new TextView(context);
//                itemTextView.setPadding(subItemPaddingLeft, subItemPaddingTopAndBottom, 0, subItemPaddingTopAndBottom);
//                itemTextView.setTextColor(0x88000000);
//                itemTextView.setLayoutParams(
//                        new ViewGroup.LayoutParams(
//                                ViewGroup.LayoutParams.MATCH_PARENT,
//                                ViewGroup.LayoutParams.WRAP_CONTENT));
//                return new RecyclerView.ViewHolder(itemTextView) {
//                };
                inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.course_child, parent, false);
                ViewHolder child = new ViewHolder(view);
//                LinearLayout course_child_linear1 = (LinearLayout) view.findViewById(R.id.course_child_linear1);
                LinearLayout.LayoutParams marginControl = (LinearLayout.LayoutParams)child.line.getLayoutParams();

                if (cnt == 0) {
                    child.child_time_txt.setText("23분");
                    child.child_time_txt.setVisibility(View.VISIBLE);
                    marginControl.leftMargin = (int) (21 * dp);;
                    cnt++;
                }
                else {
                    marginControl.leftMargin = (int) (50 * dp);;
                }
                child.line.setLayoutParams(marginControl);
                return child;
        }
        return null;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Item item = data.get(position);

        switch (item.type) {
            case HEADER:
                final ListHeaderViewHolder itemController = (ListHeaderViewHolder) holder;
                itemController.refferalItem = item;
                itemController.header_title.setText(item.text);

                if (itemController.header_title.getText().toString().equals("경복궁")) {
                    itemController.number.setImageResource(header_indicator[0]);
                    itemController.title_img.setImageResource(R.drawable.img_gyeongbok_course);
                }
                else if (itemController.header_title.getText().toString().equals("경희궁")) {
                    itemController.number.setImageResource(header_indicator[1]);
                    itemController.title_img.setImageResource(R.drawable.img_gyeong_hee_course);
                }
                else if (itemController.header_title.getText().toString().equals("북촌문화센터")) {
                    itemController.number.setImageResource(header_indicator[2]);
                    itemController.title_img.setImageResource(R.drawable.img_geoncheon_course);
                }

                itemController.btn_expand_toggle.setImageResource(R.drawable.ic_nextarrow_g);


                break;
            case CHILD:
                final ViewHolder childController = (ViewHolder) holder;
                childController.childItem = data.get(position);
                childController.child_txt.setText(childController.childItem.text);
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
        public ImageView number;
        public ImageView title_img;
        public TextView header_title;
        public ImageView btn_expand_toggle;
        public Item refferalItem;

        public ListHeaderViewHolder(View itemView) {
            super(itemView);
            number = (ImageView) itemView.findViewById(R.id.number);
            title_img = (ImageView) itemView.findViewById(R.id.title_img);
            header_title = (TextView) itemView.findViewById(R.id.header_title);
            btn_expand_toggle = (ImageView) itemView.findViewById(R.id.btn_expand_toggle);
        }
    }
    private class ViewHolder extends RecyclerView.ViewHolder {
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
        public int type;
        public String text;
        public List<Item> invisibleChildren; //place

        public Item() {
        }

        public Item(int type, String text ) {
            this.type = type;
            this.text = text;
        }
    }

}
