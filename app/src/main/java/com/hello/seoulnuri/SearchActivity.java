package com.hello.seoulnuri;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ArrayList<rowItem> arrayList = new ArrayList<>();
        /* tmp */
        rowItem item1 = new rowItem();
        item1.favorite = "경복궁";
        arrayList.add(item1);
        rowItem item2 = new rowItem();
        item2.favorite = "서울시립미술관";
        arrayList.add(item2);
        rowItem item3 = new rowItem();
        item3.favorite = "창덕궁";
        arrayList.add(item3);

        /*
            Add favorite list
        */

        final ListView listView = (ListView)findViewById(R.id.searchListView);
        final SearchListViewAdapter adapter = new SearchListViewAdapter(arrayList);
        final TextView textView = (TextView)findViewById(R.id.searchTextView);
        listView.setAdapter(adapter);

        final ImageButton btnCancel = (ImageButton)findViewById(R.id.searchCancelButton);
        ImageButton btnBack = (ImageButton)findViewById(R.id.searchBackButton);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        final EditText editText = (EditText)findViewById(R.id.searchEditText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnCancel.setVisibility(View.VISIBLE);
                textView.setText("검색결과");

                String[] result = {"List1", "List2", "List3", "List4", "List5"};
                /*
                send to Server
                */

                ArrayAdapter adapterResult = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, result);
                listView.setAdapter(adapterResult);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setAdapter(adapter);   // It doesn't work.......
                editText.setText("");
                btnCancel.setVisibility(View.INVISIBLE);
                textView.setText("즐겨찾기");
            }
        });
    }
}
