
package com.hello.seoulnuri.info;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.hello.seoulnuri.R;
import com.hello.seoulnuri.model.InfoData;

import java.util.ArrayList;

public class guideAdapter extends RecyclerView.Adapter<guideAdapter.ItemViewHolder> {

    LayoutInflater inflater;
    Context context;
    private ArrayList<InfoData> datas;

    DetailcontainAdapter gadapter;

    public guideAdapter(Context context, ArrayList<InfoData> datas) {
        this.context = context;
        this.datas = datas;
    }
    @Override
    public guideAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //recycler view에 반복될 아이템 레이아웃 연결
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_detail_guide, null);
        return new guideAdapter.ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull guideAdapter.ItemViewHolder viewHolder, int i) {
        final InfoData data = datas.get(i); // subtitle 정보 연결되는 부분

        viewHolder.subtitle.setText("이용안내"); // maintitle 부분 정보 받아오면 여기서 변경
        gadapter = new DetailcontainAdapter();
        viewHolder.listView.setAdapter(gadapter);

        gadapter.addItem(data.title,data.text);

    }

    @Override
    public int getItemCount() {
        return this.datas.size();
    }

    public class ItemViewHolder  extends RecyclerView.ViewHolder {

        TextView subtitle;
        ListView listView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            subtitle=(TextView)itemView.findViewById(R.id.tv_introinfo);
            listView=(ListView) itemView.findViewById(R.id.guidelist);
        }
    }
}

