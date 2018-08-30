package com.hello.seoulnuri;

import android.app.TabActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shineeseo on 2018. 8. 21..
 */

public class Course_detail extends AppCompatActivity {
    public static final int COURSE_EYE = 3;
    public static final int COURSE_EAR = 4;
    public static final int COURSE_WHEEL = 5;
    public static final int COURSE_ELDER = 6;

    private RecyclerView recyclerview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        TabHost tabHost1 = (TabHost) findViewById(R.id.tabHost);
        tabHost1.setup();

        // 첫 번째 Tab. (탭 표시 텍스트:"TAB 1"), (페이지 뷰:"content1")
        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1");
        ts1.setContent(R.id.content1);
        ts1.setIndicator("소개");
        tabHost1.addTab(ts1);

        // 두 번째 Tab. (탭 표시 텍스트:"TAB 2"), (페이지 뷰:"content2")
        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2");
        ts2.setContent(R.id.content2);
        ts2.setIndicator("코스");
        tabHost1.addTab(ts2);

//        tabHost1.setOnTabChangedListener(this);

//        // 세 번째 Tab. (탭 표시 텍스트:"TAB 3"), (페이지 뷰:"content3")
//        TabHost.TabSpec ts3 = tabHost1.newTabSpec("Tab Spec 3");
//        ts3.setContent(R.id.content3);
//        ts3.setIndicator("무장애정보");
//        tabHost1.addTab(ts3) ;

        //코스 탭
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<ExpandableListAdapter.Item> places = new ArrayList<>();

        places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "경복궁"));
        places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "홍례문"));
        places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "근정전"));
        places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "수정전"));
        places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD,"경희루"));
        places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER,"경희궁"));
        places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "승정전"));
        places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "자정전"));
        places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER,"북촌문화센터"));

//        ExpandableListAdapter.Item place = new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER,"A");
//        place.invisibleChildren = new ArrayList<>();
//        place.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "A-1"));
//        places.add(place);

        recyclerview.setAdapter(new ExpandableListAdapter(COURSE_EYE, places));


        //툴바세팅하기
//        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        toolbar.setTitleTextColor(Color.parseColor("#333000")); //제목의 칼라
//        toolbar.setSubtitle("추천코스"); //부제목 넣기
//        setSupportActionBar(toolbar); //툴바를 액션바와 같게 만들어 준다.

        ExpandableListView elv = (ExpandableListView) findViewById(R.id.elv);

        final ArrayList<Position> position = getData();

        //create and bind to adatper
        Course_info_list_adapter adapter = new Course_info_list_adapter(this, position);
        elv.setAdapter(adapter);

        //set onclick listener
        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                Toast.makeText(getApplicationContext(), position.get(groupPosition).players.get(childPosition), Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

//    @Override
//    public void onTabChanged(String tabId) {
//        // Tab 색 변경
//        for(int i = 0; i < tabHost1.getTabWidget().getChildCount(); i++) {
//            tabHost1.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#7392B5"));
//        }
//        tabHost1.getTabWidget().getChildAt(tabHost1.getCurrentTab()).setBackgroundColor(Color.parseColor("#4E4E9C"));
//    }

    private ArrayList<Position> getData() {

        Position p1 = new Position("경복궁");
        p1.courses.add("경복궁");
//        p1.players.add("Brook Raley");
//        p1.players.add("박세웅");

        Position p2 = new Position("경희궁");
        p2.courses.add("경희궁");
//        p2.players.add("안중열");

        Position p3 = new Position("북촌문화센터");
        p3.courses.add("북촌문화센터");
//        p3.players.add("박종윤");

//        Position p4 = new Position("outfield");
//        p4.players.add("Jim Adduci");
//        p4.players.add("손아섭");

        ArrayList<Position> allposition = new ArrayList<>();
        allposition.add(p1);
        allposition.add(p2);
        allposition.add(p3);
//        allposition.add(p4);

        return allposition;
    }

}
