package com.hello.seoulnuri;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by shineeseo on 2018. 8. 29..
 */

public class Course_info_list_adapter extends BaseExpandableListAdapter {

        private Context mContext;
        private ArrayList<Position> position;
        private LayoutInflater inflater;

        private int[] header_indicator = {R.drawable.order_1, R.drawable.order_2, R.drawable.order_3};

        //class Constructor
        public Course_info_list_adapter (Context mContext, ArrayList<Position> position) {

            this.mContext = mContext;
            this.position = position;
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getGroupCount() {
            return position.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return position.get(groupPosition).courses.size();
        }

        //get position
        @Override
        public Object getGroup(int groupPosition) {
            return position.get(groupPosition);
        }

        //this is where we get the information of player
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return position.get(groupPosition).courses.get(childPosition);
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
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.course_info_list_header, null);
            }

//            int list_size = getGroupCount();

            //get position
            Position position = (Position) getGroup(groupPosition);

            //set positionName
            String positionName = position.position;

            ImageView numberImage = (ImageView) convertView.findViewById(R.id.number);

            numberImage.setImageResource(header_indicator[groupPosition]);

            TextView textView = (TextView) convertView.findViewById(R.id.header_title);
            textView.setText(positionName);

            ImageView imageView = (ImageView) convertView.findViewById(R.id.btn_expand_toggle);
            if(isExpanded){
                imageView.setImageResource(R.drawable.ic_downtarrow_g);
            } else {
                imageView.setImageResource(R.drawable.ic_uparrow_g);
            }

            //convertView.setBackgroundColor(convertView.getResources().getColor(R.color.expandableListColor));
            return convertView;
        }

        //get child_list.xml (View)
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            //inflate the layout
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.course_info_list_child, null);
            }

            String child = (String) getChild(groupPosition, childPosition);

            //set the child name
            TextView name = (TextView) convertView.findViewById(R.id.name_tv);
            //get the imageView
            ImageView img = (ImageView) convertView.findViewById(R.id.coursepic);

            name.setText(child);

            //get position name
            String positionName = (String) getGroup(groupPosition).toString();
            if (positionName == "경복궁") {
                if (child == "경복궁") {
                    img.setImageResource(R.drawable.img_gyeongbok_info);
                }
            } else if (positionName == "경희궁") {
                if (child == "경희궁") {
                    img.setImageResource(R.drawable.img_gyeongbok_info);
                }
            } else if (positionName == "북촌문화센터") {
                if (child == "북촌문화센터") {
                    img.setImageResource(R.drawable.img_gyeongbok_info);
                }
            }
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }
