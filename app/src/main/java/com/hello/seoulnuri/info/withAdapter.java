
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

public class withAdapter extends RecyclerView.Adapter<withAdapter.ItemViewHolder> {

    LayoutInflater inflater;
    Context context;
    private ArrayList<InfoData> datas;

    withcontainAdapter wadapter;

    public withAdapter(Context context, ArrayList<InfoData> datas) {
        this.context = context;
        this.datas = datas;
    }
    @Override
    public withAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //recycler view에 반복될 아이템 레이아웃 연결
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_detail_withtour, null);
        return new withAdapter.ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull withAdapter.ItemViewHolder viewHolder, int i) {
        final InfoData data = datas.get(i); // subtitle 정보 연결되는 부분

        viewHolder.subtitle.setText("공통"); // maintitle 부분 정보 받아오면 여기서 변경
        wadapter = new withcontainAdapter();
        viewHolder.listView.setAdapter(wadapter);

        wadapter.addItem(data.title,data.text);

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
            subtitle=(TextView)itemView.findViewById(R.id.tv_withtitle);
            listView=(ListView) itemView.findViewById(R.id.lv_withinfo);
        }
    }
}

