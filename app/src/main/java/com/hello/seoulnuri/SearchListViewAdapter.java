package com.hello.seoulnuri;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchListViewAdapter extends BaseAdapter {
    LayoutInflater inflater = null;
    private ArrayList<rowItem> arrayList = null;
    private int listNum = 0;

    public SearchListViewAdapter(ArrayList<rowItem> list) {
        arrayList = list;
        listNum = list.size();
    }

    @Override
    public int getCount() {
        return listNum;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            final Context context = parent.getContext();
            if (inflater == null)
            {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.row_search, parent, false);
        }

        TextView oTextTitle = (TextView) convertView.findViewById(R.id.rowTextView);

        oTextTitle.setText(arrayList.get(position).favorite);
        return convertView;
    }
}
