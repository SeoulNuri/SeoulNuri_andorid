package com.hello.seoulnuri.view.course.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hello.seoulnuri.model.Position;
import com.hello.seoulnuri.R;

import java.util.ArrayList;

import static com.hello.seoulnuri.view.course.adapter.CourseAdapter.COURSE_EAR;
import static com.hello.seoulnuri.view.course.adapter.CourseAdapter.COURSE_ELDER;
import static com.hello.seoulnuri.view.course.adapter.CourseAdapter.COURSE_EYE;
import static com.hello.seoulnuri.view.course.adapter.CourseAdapter.COURSE_WHEEL;

/**
 * Created by shineeseo on 2018. 8. 29..
 */

public class Course_info_list_adapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList<Position> courses_list;
    private LayoutInflater inflater;
    private int[] header_indicator = {R.drawable.order_1, R.drawable.order_2, R.drawable.order_3};

    //class Constructor
    public Course_info_list_adapter(Context mContext, ArrayList<Position> courses_list) {
        this.mContext = mContext;
        this.courses_list = courses_list; //tour_name, tour_img
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
        else {
            viewHolder = (HeadViewHolder) convertView.getTag();
        }

        //get position
        Position course_info_list_header = (Position) getGroup(groupPosition);

        //set positionName
        String course_info_list_header_title = course_info_list_header.course_info_item;

        viewHolder.numberImage.setImageResource(header_indicator[groupPosition]);

        viewHolder.header_title.setText(course_info_list_header_title);

        if (isExpanded) {
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
        else {
            viewHolder = (ChildViewHolder) convertView.getTag();
        }

        //get child name
        String child = (String) getChild(groupPosition, childPosition); //arrayList 중 course_info
        //get header name
        String positionName = (String) getGroup(groupPosition).toString(); //course_info_list_header_title

        for (int i = 0; i < courses_list.size(); i++) {
            if (positionName == courses_list.get(i).course_info_item) {
                if (child == courses_list.get(i).course_info_item) {
                    if (courses_list.get(i).course_info_image != "") {
                        Glide.with(mContext)
                                .load(courses_list.get(i).course_info_image)
                                .into(viewHolder.course_item_child_pic);
                    }
                    viewHolder.course_item_child_txt.setText(courses_list.get(i).course_info_txt);

                }
            }
        }
//                    if (positionName == courses_list.get(0).course_info_item) {
//                        if (child == courses_list.get(0).course_info_item) {
//                            if(courses_list.get(0).course_info_image != ""){
//                                Glide.with(mContext)
//                                        .load(courses_list.get(0).course_info_image)
//                                        .into(viewHolder.course_item_child_pic);
//                            }
//                            viewHolder.course_item_child_txt.setText(mContext.getResources().getString(R.string.course_info_eye_first_child_txt));
//
//                        }
//                    } else if (positionName == courses_list.get(1).course_info_item) {
//                        if (child == courses_list.get(1).course_info_item) {
//                            if(courses_list.get(1).course_info_image != ""){
//                                Glide.with(mContext)
//                                        .load(courses_list.get(1).course_info_image)
//                                        .into(viewHolder.course_item_child_pic);
//                            }
//                            viewHolder.course_item_child_txt.setText(mContext.getResources().getString(R.string.course_info_eye_sec_child_txt));
//                        }
//                    }

//                    if (positionName == courses_list.get(0).course_info_item) {
//                        if (child == courses_list.get(0).course_info_item) {
//                            if(courses_list.get(0).course_info_image != ""){
//                                Glide.with(mContext)
//                                        .load(courses_list.get(0).course_info_image)
//                                        .into(viewHolder.course_item_child_pic);
//                            }
//                            viewHolder.course_item_child_txt.setText(mContext.getResources().getString(R.string.course_info_wheel_first_child_txt));
//
//                        }
//                    } else if (positionName == courses_list.get(1).course_info_item) {
//                        if (child == courses_list.get(1).course_info_item) {
//                            if(courses_list.get(1).course_info_image != ""){
//                                Glide.with(mContext)
//                                        .load(courses_list.get(1).course_info_image)
//                                        .into(viewHolder.course_item_child_pic);
//                            }
//                            viewHolder.course_item_child_txt.setText(mContext.getResources().getString(R.string.course_info_wheel_sec_child_txt));
//
//                        }
//                    }


//                    if (positionName == courses_list.get(0).course_info_item) {
//                        if (child == courses_list.get(0).course_info_item) {
//                            if(courses_list.get(0).course_info_image != ""){
//                                Glide.with(mContext)
//                                        .load(courses_list.get(0).course_info_image)
//                                        .into(viewHolder.course_item_child_pic);
//                            }
//                            viewHolder.course_item_child_txt.setText(mContext.getResources().getString(R.string.course_info_ear_first_child_txt));
//
//                        }
//                    } else if (positionName == courses_list.get(1).course_info_item) {
//                        if (child == courses_list.get(1).course_info_item) {
//                            if(courses_list.get(1).course_info_image != ""){
//                                Glide.with(mContext)
//                                        .load(courses_list.get(1).course_info_image)
//                                        .into(viewHolder.course_item_child_pic);
//                            }
//                            viewHolder.course_item_child_txt.setText(mContext.getResources().getString(R.string.course_info_ear_sec_child_txt));
//
//                        }
//                    }


//                    if (positionName == courses_list.get(0).course_info_item) {
//                        if (child == courses_list.get(0).course_info_item) {
//                            if(courses_list.get(0).course_info_image != ""){
//                                Glide.with(mContext)
//                                        .load(courses_list.get(0).course_info_image)
//                                        .into(viewHolder.course_item_child_pic);
//                            }
////                            viewHolder.course_item_child_txt.setText(mContext.getResources().getString(R.string.course_info_elder_first_child_txt));
//                            viewHolder.course_item_child_txt.setText(courses_list.get(0).course_info_txt);
//
//                        }
//                    } else if (positionName == courses_list.get(1).course_info_item) {
//                        if (child == courses_list.get(1).course_info_item) {
//                            if(courses_list.get(1).course_info_image != ""){
//                                Glide.with(mContext)
//                                        .load(courses_list.get(1).course_info_image)
//                                        .into(viewHolder.course_item_child_pic);
//                            }
//                            viewHolder.course_item_child_txt.setText(mContext.getResources().getString(R.string.course_info_elder_sec_child_txt));
//
//                        }
//                    } else if (positionName == courses_list.get(2).course_info_item) {
//                        if (child == courses_list.get(2).course_info_item) {
//                            if(courses_list.get(2).course_info_image != ""){
//                                Glide.with(mContext)
//                                        .load(courses_list.get(2).course_info_image)
//                                        .into(viewHolder.course_item_child_pic);
//                            }
//                            viewHolder.course_item_child_txt.setText(mContext.getResources().getString(R.string.course_info_elder_thd_child_txt));
//
//                        }
//                    }


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
