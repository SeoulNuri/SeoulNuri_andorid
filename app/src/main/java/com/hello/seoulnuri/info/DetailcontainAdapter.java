package com.hello.seoulnuri.info;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.hello.seoulnuri.R;
import com.hello.seoulnuri.model.InfoData;

import java.util.ArrayList;

public class DetailcontainAdapter extends BaseAdapter {

    private ArrayList<InfoData> datas = new ArrayList<InfoData>();

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.detail_guide_content, parent, false);

        }
        TextView subtitle = (TextView)convertView.findViewById(R.id.tv_subtitle);
        TextView infos = (TextView)convertView.findViewById(R.id.tv_infos);

        InfoData data = datas.get(position);

        subtitle.setText(data.title);
        infos.setText(data.text);

        return convertView;
    }

    public void addItem (String subtitle, String infos){
        InfoData data = new InfoData(subtitle,infos);

        datas.add(data);
    }
}
