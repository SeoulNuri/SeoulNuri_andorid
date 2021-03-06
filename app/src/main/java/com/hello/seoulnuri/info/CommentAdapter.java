package com.hello.seoulnuri.info;



import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.BaseAdapter;

import com.hello.seoulnuri.R;
import com.hello.seoulnuri.commentItem;

public class CommentAdapter extends BaseAdapter {

        private Context mContext;
        private int layout;
        private ArrayList<commentItem> cmt_infos;
        private int pos;
        //	private Typeface myFont;
        TextView cmt_nickname;
        TextView cmt_say;
        TextView cmt_date;

    public CommentAdapter(Context mContext, int layout, ArrayList<commentItem> arr_item) {
            this.mContext = mContext;
            this.layout = layout;
            this.cmt_infos = arr_item;
        }
        @Override
        public int getCount() {
            return cmt_infos.size();
        }
        @Override
        public Object getItem(int position) {
            return cmt_infos.get(position);
        }
        public long getItemId(int position){
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           if(convertView == null){
                LayoutInflater mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.cmts_layout, parent, false);
                cmt_nickname =(TextView)convertView.findViewById(R.id.cmt_nickname);
                cmt_say = (TextView)convertView.findViewById(R.id.tv_cmt_say);
                cmt_date = (TextView)convertView.findViewById(R.id.tv_cmt_date);

            }
            pos = position;
            if(cmt_infos.size() != 0){
                cmt_nickname.setText(cmt_infos.get(pos).getNickname());
                cmt_say.setText(cmt_infos.get(pos).getSay());
                cmt_date.setText(cmt_infos.get(pos).getDate().toString());
            }
            return convertView;
        }

    public void addItem (commentItem item) {
        cmt_infos.add(item);
    }


        // 리스트 아이템 롱 클릭시 삭제 실행??


}
