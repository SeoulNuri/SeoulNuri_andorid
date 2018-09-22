package com.hello.seoulnuri.view.course.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hello.seoulnuri.model.Position;
import com.hello.seoulnuri.R;

import java.util.ArrayList;

/**
 * Created by shineeseo on 2018. 8. 29..
 */

public class Course_info_list_adapter extends BaseExpandableListAdapter {

        private Context mContext;
        private ArrayList<Position> courses_list;
        private LayoutInflater inflater;

        private int[] header_indicator = {R.drawable.order_1, R.drawable.order_2, R.drawable.order_3};

        //class Constructor
        public Course_info_list_adapter (Context mContext, ArrayList<Position> courses_list) {
            this.mContext = mContext;
            this.courses_list = courses_list;
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getGroupCount() {
            return courses_list.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return courses_list.get(groupPosition).course_info.size();
        }

        //get position
        @Override
        public Object getGroup(int groupPosition) {
            return courses_list.get(groupPosition);
        }

        //this is where we get the information of player
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return courses_list.get(groupPosition).course_info.get(childPosition);
        }

        //position ID
        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        //where to get player's id
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        //get parent row
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            HeadViewHolder viewHolder;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.course_info_list_header, null);

                viewHolder = new HeadViewHolder();
                viewHolder.btn_expand_toggle = (ImageView) convertView.findViewById(R.id.btn_expand_toggle);
                viewHolder.header_title = (TextView) convertView.findViewById(R.id.header_title);
                viewHolder.numberImage = (ImageView) convertView.findViewById(R.id.number);

                convertView.setTag(viewHolder);
            }

            //캐시된 뷰 홀더가 있을 경우 사용
            else
            {
                viewHolder = (HeadViewHolder) convertView.getTag();
            }

            //get position
            Position course_info_list_header = (Position) getGroup(groupPosition);

            //set positionName
            String course_info_list_header_title = course_info_list_header.course_info_item;

            viewHolder.numberImage.setImageResource(header_indicator[groupPosition]);

            viewHolder.header_title.setText(course_info_list_header_title);

            if(isExpanded){
                viewHolder.btn_expand_toggle.setImageResource(R.drawable.ic_uparrow_g);
            } else {
                viewHolder.btn_expand_toggle.setImageResource(R.drawable.ic_downtarrow_g);
            }

            convertView.setBackgroundColor(convertView.getResources().getColor(R.color.expandableListColor));
            return convertView;
        }

        public class HeadViewHolder {
            public ImageView numberImage;
            public TextView header_title;
            public ImageView btn_expand_toggle;

        }

        //get child_list.xml (View)
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildViewHolder viewHolder;

            //inflate the layout
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.course_info_list_child, null);

                viewHolder = new ChildViewHolder();
                viewHolder.course_item_child_pic = (ImageView) convertView.findViewById(R.id.course_item_child_pic);
                viewHolder.course_item_child_txt = (TextView) convertView.findViewById(R.id.course_item_child_txt);

                convertView.setTag(viewHolder);
            }

            //캐시된 뷰 홀더가 있을 경우 사용
            else
            {
                viewHolder = (ChildViewHolder) convertView.getTag();
            }

            viewHolder.course_item_child_txt.setText(mContext.getResources().getString(R.string.course_info_child_txt));

            //get child name
            String child = (String) getChild(groupPosition, childPosition);
            //get header name
            String positionName = (String) getGroup(groupPosition).toString();
            if (positionName == "경복궁") {
                if (child == "경복궁") {
                    viewHolder.course_item_child_pic.setImageResource(R.drawable.img_gyeongbok_info);
                }
            } else if (positionName == "경희궁") {
                if (child == "경희궁") {
                    viewHolder.course_item_child_pic.setImageResource(R.drawable.img_gyeongbok_info);
                }
            } else if (positionName == "북촌문화센터") {
                if (child == "북촌문화센터") {
                    viewHolder.course_item_child_pic.setImageResource(R.drawable.img_gyeongbok_info);
                }
            }
            return convertView;
        }

        public class ChildViewHolder {
            public TextView course_item_child_txt;
            public ImageView course_item_child_pic;

        }
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }
