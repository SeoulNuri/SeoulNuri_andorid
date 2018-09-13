package com.hello.seoulnuri.view.course;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.hello.seoulnuri.model.Position;
import com.hello.seoulnuri.view.course.adapter.Course_info_list_adapter;
import com.hello.seoulnuri.view.course.adapter.ExpandableListAdapter;
import com.hello.seoulnuri.R;

import java.util.ArrayList;
import java.util.List;

import static com.hello.seoulnuri.view.course.adapter.CourseAdapter.COURSE_EAR;
import static com.hello.seoulnuri.view.course.adapter.CourseAdapter.COURSE_ELDER;
import static com.hello.seoulnuri.view.course.adapter.CourseAdapter.COURSE_EYE;
import static com.hello.seoulnuri.view.course.adapter.CourseAdapter.COURSE_WHEEL;

/**
 * Created by shineeseo on 2018. 8. 21..
 */

public class Course_detail extends AppCompatActivity {

    private RecyclerView recyclerview;
    Intent intent;
    ImageView btn_course_bookmark;
    ImageView btn_course_share;
    ImageView btn_course_map;
    Dialog bookmark_Dialog;
    BottomSheetDialog link_share_dialog;
    Button btn_bookmark_ok;
    ViewGroup course_kakao_share;
    ViewGroup course_link_share;
    ViewGroup course_more_share;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        TextView course_item_txt = (TextView) findViewById(R.id.course_item_txt);

        intent = new Intent(this.getIntent());
        int select_type = intent.getIntExtra("select_type", 3);
        Log.d("select_type", "selecttype = " + select_type);

        switch (select_type) {
            case COURSE_EYE:
                course_item_txt.setText("추천 코스 (시각)");
                break;
            case COURSE_EAR:
                course_item_txt.setText("추천 코스 (청각)");
                break;
            case COURSE_WHEEL:
                course_item_txt.setText("추천 코스 (지체)");
                break;
            case COURSE_ELDER:
                course_item_txt.setText("추천 코스 (노인)");
                break;

        }

        TextView course_path_rate_txt = (TextView) findViewById(R.id.course_path_rate_txt);
        course_path_rate_txt.setText("(21)");

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

        btn_course_bookmark = (ImageView) findViewById(R.id.btn_course_bookmark);

        btn_course_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Course_bookmark_custom_dialog();
            }
        });

        btn_course_share = (ImageView) findViewById(R.id.btn_course_share);

        btn_course_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Course_link_share_custom_dialog();
            }
        });

        btn_course_map = (ImageView) findViewById(R.id.btn_course_map);

        btn_course_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Course_detail.this, CourseMapActivity.class);
                ArrayList<Double> listDouble = new ArrayList<Double>();
                listDouble.add(37.600477);
                listDouble.add(126.977507);
                intent.putExtra("latLang_list", listDouble);
                startActivity(intent);
            }
        });

//        prevTab = tabHost1.getCurrentTab();// Keep track of the default tab
//        tabHost1.setOnTabChangedListener(new TabHost.OnTabChangeListener(){ //tabhost is a variable of type TabHost, which will contain all your tabs
//            @Override
//            public void onTabChanged(String id) {
//                int tab = tabHost1.getCurrentTab();
//                TextView tv = (TextView) getTabWidget().getChildAt(tab).findViewById(android.R.id.title);
//                tv.setTextColor(Color.BLUE);//Set selected tab colour to something you want
//
//                if(prevTab!=-1){// If there was a previously selected tab, set it back to a default colour as it is now unselected
//                    TextView tv1 = (TextView) getTabWidget().getChildAt(prevTab).findViewById(android.R.id.title);
//                    tv1.setTextColor(Color.BLACK);
//                }
//                prevTab = tab; //Update this newly selected tab to the currently selected tab, for same logic to repeat for future tab changes
//            }
//        });

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
        places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "경희루"));
        places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "경희궁"));
        places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "승정전"));
        places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "자정전"));
        places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "북촌문화센터"));

        recyclerview.setAdapter(new ExpandableListAdapter(COURSE_EYE, places));


        //툴바세팅하기
//        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        toolbar.setTitleTextColor(Color.parseColor("#333000")); //제목의 칼라
//        toolbar.setSubtitle("추천코스"); //부제목 넣기
//        setSupportActionBar(toolbar); //툴바를 액션바와 같게 만들어 준다.

        //추천코스 소개 대표 이미지
        ImageView course_info_item_img = (ImageView) findViewById(R.id.course_img);
        //선택 유형에 따라 달라져야함

        //추천코스 소개 대표 설명
        TextView course_info_item_txt = (TextView) findViewById(R.id.course_txt);

        course_info_item_txt.setText(getResources().getString(R.string.course_info_txt));

        //course_info_list ExpandableListView 설정
        ExpandableListView elv = (ExpandableListView) findViewById(R.id.elv);

        final ArrayList<Position> courses_list = getData();

        //create and bind to adatper
        Course_info_list_adapter adapter = new Course_info_list_adapter(this, courses_list);
        elv.setAdapter(adapter);

        setExpandableListViewHeight(elv, -1);
        elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int position, long id) {
                setExpandableListViewHeight(parent, position);
                return false;
            }
        });


        //set onclick listener
//        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
////                Toast.makeText(getApplicationContext(), position.get(groupPosition).players.get(childPosition), Toast.LENGTH_LONG).show();
//                return false;
//            }
//        });


//    @Override
//    public void onTabChanged(String tabId) {
//        // Tab 색 변경
//        for(int i = 0; i < tabHost1.getTabWidget().getChildCount(); i++) {
//            tabHost1.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#7392B5"));
//        }
//        tabHost1.getTabWidget().getChildAt(tabHost1.getCurrentTab()).setBackgroundColor(Color.parseColor("#4E4E9C"));
//    }
    }

    public void Course_bookmark_custom_dialog() {
        bookmark_Dialog = new Dialog(this);
        bookmark_Dialog.setContentView(R.layout.course_bookmark_dialog);
//        bookmark_Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        bookmark_Dialog.setTitle("bookmark dialog");

        btn_bookmark_ok = (Button) bookmark_Dialog.findViewById(R.id.btn_bookmark_ok);

//        btn_bookmark_ok.setEnabled(true);

        btn_bookmark_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 bookmark_Dialog.cancel();
            }
        });

        bookmark_Dialog.show();
    }

    public void Course_link_share_custom_dialog() {

        link_share_dialog = new BottomSheetDialog(this);
        link_share_dialog.setContentView(R.layout.course_share_dialog);
        link_share_dialog.setTitle("공유하기");

        course_kakao_share = (ViewGroup) link_share_dialog.findViewById(R.id.course_kakao_share);

        course_kakao_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        course_link_share = (ViewGroup) link_share_dialog.findViewById(R.id.course_link_share);
        course_link_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        course_more_share = (ViewGroup) link_share_dialog.findViewById(R.id.course_more_share);
        course_more_share.setOnClickListener(new View.OnClickListener() {
            LayoutInflater inflater = getLayoutInflater();
            final View layout = inflater.inflate(R.layout.custom_toast,(ViewGroup)findViewById(R.id.toast_custom));

            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(Course_detail.this, "클립보드에 복사되었습니다", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, -100);
                toast.setView(layout);
                toast.show();
            }
        });
        link_share_dialog.show();

    }
        private ArrayList<Position> getData() {

            Position p1 = new Position("경복궁");
            p1.course_info.add("경복궁");


            Position p2 = new Position("경희궁");
            p2.course_info.add("경희궁");

            Position p3 = new Position("북촌문화센터");
            p3.course_info.add("북촌문화센터");

            ArrayList<Position> allposition = new ArrayList<>();
            allposition.add(p1);
            allposition.add(p2);
            allposition.add(p3);
//        allposition.add(p4);

            return allposition;
        }

        private static void setExpandableListViewHeight(ExpandableListView listView, int group) {
            Course_info_list_adapter listAdapter = (Course_info_list_adapter) listView.getExpandableListAdapter();

            if (listAdapter == null) {
                return;
            }

            int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            int totalHeight = 0;
            View view = null;
            for (int i = 0; i < listAdapter.getGroupCount(); i++) {
                view = listAdapter.getGroupView(i, false, view, listView);
                if (i == 0) {
                    view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
                }
                view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += view.getMeasuredHeight();
                if(((listView.isGroupExpanded(i)) && (i != group)) || ((!listView.isGroupExpanded(i)) && (i == group))) {
                    View listItem = null;
                    for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                        listItem = listAdapter.getChildView(i, j, false, listItem, listView);
                        listItem.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, View.MeasureSpec.UNSPECIFIED));
                        listItem.measure(
                                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                        totalHeight += listItem.getMeasuredHeight();
                    }
                }
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
            listView.setLayoutParams(params);
            listView.requestLayout();
        }

    }

