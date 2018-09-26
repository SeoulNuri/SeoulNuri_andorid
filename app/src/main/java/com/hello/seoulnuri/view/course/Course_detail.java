package com.hello.seoulnuri.view.course;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.hello.seoulnuri.base.BaseModel;
import com.hello.seoulnuri.commentItem;
import com.hello.seoulnuri.info.CommentActivity;
import com.hello.seoulnuri.info.Info_Detail_Intro;
import com.hello.seoulnuri.info.popUpActivity;
import com.hello.seoulnuri.model.CourseItem;
import com.hello.seoulnuri.model.Position;
import com.hello.seoulnuri.model.course.CourseBookmarkData;
import com.hello.seoulnuri.model.course.CourseBookmarkResponse;
import com.hello.seoulnuri.model.course.CourseCmtData;
import com.hello.seoulnuri.model.course.CourseCmtRequest;
import com.hello.seoulnuri.model.course.CourseCmtResponse;
import com.hello.seoulnuri.model.course.CourseDetailData;
import com.hello.seoulnuri.model.course.CourseDetailResponse;
import com.hello.seoulnuri.model.course.CourseStarData;
import com.hello.seoulnuri.model.course.CourseStarModify;
import com.hello.seoulnuri.model.course.TourInfo;
import com.hello.seoulnuri.network.ApplicationController;
import com.hello.seoulnuri.network.NetworkService;
import com.hello.seoulnuri.utils.SharedPreference;
import com.hello.seoulnuri.view.course.adapter.CourseCommentAdapter;
import com.hello.seoulnuri.view.course.adapter.Course_info_list_adapter;
import com.hello.seoulnuri.view.course.adapter.ExpandableListAdapter;
import com.hello.seoulnuri.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hello.seoulnuri.view.course.CourseCommentActivity.TOKEN_DATA;
import static com.hello.seoulnuri.view.course.adapter.CourseAdapter.COURSE_EAR;
import static com.hello.seoulnuri.view.course.adapter.CourseAdapter.COURSE_ELDER;
import static com.hello.seoulnuri.view.course.adapter.CourseAdapter.COURSE_EYE;
import static com.hello.seoulnuri.view.course.adapter.CourseAdapter.COURSE_WHEEL;

/**
 * Created by shineeseo on 2018. 8. 21..
 */

public class Course_detail extends AppCompatActivity {

    private RecyclerView recyclerview;
    private Intent intent;
    private ImageView btn_course_bookmark;
    private ImageView btn_course_share;
    private ImageView btn_course_map;
    private ImageView btn_course_comment;
    private Dialog bookmark_Dialog;
    private BottomSheetDialog link_share_dialog;
    private Button btn_bookmark_ok;
    private ViewGroup course_kakao_share;
    private ViewGroup course_link_share;
    private ViewGroup course_more_share;
    private ArrayList<CourseItem> courseStarList;
    private RatingBar course_path_rate_star;
    private TabHost tabHost1;
    private NetworkService networkService;
    private CourseDetailData courseDetailData;
    private int select_type;
    private List<ExpandableListAdapter.Item> places;
    private ExpandableListView elv;
    private ArrayList<Position> courses_list;
    private TextView course_item_txt;
    private TextView course_path_rate_txt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance();

        course_item_txt = (TextView) findViewById(R.id.course_item_txt);
        TextView course_type_txt = (TextView) findViewById(R.id.course_type_txt);

        intent = new Intent(this.getIntent());
        select_type = intent.getIntExtra("select_type", 3);
        courseStarList = (ArrayList<CourseItem>)intent.getSerializableExtra("courseList");



        Log.v("courseStarList", "value == " +courseStarList.get(0).getCour_star());
        Log.d("select_type", "selecttype = " + select_type);

        //추천코스 소개 대표 이미지
        ImageView course_info_item_img = (ImageView) findViewById(R.id.course_img);
        //선택 유형에 따라 달라져야함

        //추천코스 소개 대표 설명
        TextView course_info_item_txt = (TextView) findViewById(R.id.course_txt);
        TextView course_item_addr = (TextView) findViewById(R.id.course_item_addr);
        course_path_rate_txt = (TextView) findViewById(R.id.course_path_rate_txt);
        course_path_rate_star = (RatingBar) findViewById(R.id.course_path_rate_star);

        course_path_rate_star.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(Course_detail.this, popUpActivity.class);
//                intent.putExtra("ratingbar_infos",course_path_rate_star.getRating());
                startActivityForResult(intent,1);

                return false;
            }
        });

        switch (select_type) {
            case COURSE_EYE:
                course_path_rate_star.setRating((float)courseStarList.get(0).getCour_star());
                course_path_rate_txt.setText("("+courseStarList.get(0).getCour_star_count()+")");
                course_item_addr.setText("서울특별시 서대문구 통일로 251");
                course_info_item_txt.setText(getResources().getString(R.string.course_info_txt_eye));
                course_item_txt.setText("독립운동의 역사");
                course_type_txt.setText("시각장애인 여행 추천 코스");
                break;
            case COURSE_EAR:
                course_path_rate_star.setRating((float)courseStarList.get(2).getCour_star());
                course_path_rate_txt.setText("("+courseStarList.get(2).getCour_star_count()+")");
                course_item_addr.setText("서울특별시 송파구 올림픽로 424");
                course_info_item_txt.setText(getResources().getString(R.string.course_info_txt_ear));
                course_item_txt.setText("8호선 지하철 여행");
                course_type_txt.setText("청각장애인 여행 추천 코스");
                break;
            case COURSE_WHEEL:
                course_path_rate_star.setRating((float)courseStarList.get(1).getCour_star());
                course_path_rate_txt.setText("("+courseStarList.get(1).getCour_star_count()+")");
                course_item_addr.setText("서울특별시 중구 덕수궁길 61");
                course_info_item_txt.setText(getResources().getString(R.string.course_info_txt_wheel));
                course_item_txt.setText("지식과 함께, 박물관");
                course_type_txt.setText("지체장애인 여행 추천 코스");
                break;
            case COURSE_ELDER:
                course_path_rate_star.setRating((float)courseStarList.get(3).getCour_star());
                course_path_rate_txt.setText("("+courseStarList.get(3).getCour_star_count()+")");
                course_item_addr.setText("서울특별시 종로구 율곡로 99");
                course_info_item_txt.setText(getResources().getString(R.string.course_info_txt_elder));
                course_item_txt.setText("고궁과 한의학");
                course_type_txt.setText("어르신 여행 추천 코스");
                break;

        }

        getCourseBookmarkList();
        tabHost1 = (TabHost) findViewById(R.id.tabHost);
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

        //tab host 선택 메뉴 색상 바꾸기
        TextView tp = (TextView) tabHost1.getTabWidget().getChildAt(tabHost1.getCurrentTab()).findViewById(android.R.id.title);
        tp.setTextColor(Color.parseColor("#ff6e2c"));

        tabHost1.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                //선택되지 않은 것
                for (int i = 0; i < tabHost1.getTabWidget().getChildCount(); i++) {//탭의 각 제목의 색깔을 바꾸기 위한 부분
                    TextView tv = (TextView) tabHost1.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                    tv.setTextColor(Color.parseColor("#2c2c2c"));
                }
                //선택된 것
                //선택되는 탭에 대한 제목 색깔을 바꾸는 부분
                TextView tp = (TextView) tabHost1.getTabWidget().getChildAt(tabHost1.getCurrentTab()).findViewById(android.R.id.title);
                tp.setTextColor(Color.parseColor("#ff6e2c"));
            }

        });
        //

        //코스 댓글
        btn_course_comment = (ImageView) findViewById(R.id.btn_course_comment);

        btn_course_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Course_detail.this,CourseCommentActivity.class);
                // intent.
                intent.putExtra("course_title", course_item_txt.getText().toString());
                intent.putExtra("course_idx", 1);
                startActivity(intent);
            }
        });

        //코스 북마크
        btn_course_bookmark = (ImageView) findViewById(R.id.btn_course_bookmark);

        btn_course_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Course_bookmark_custom_dialog();
            }
        });

        //코스 공유
        btn_course_share = (ImageView) findViewById(R.id.btn_course_share);

        btn_course_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Course_link_share_custom_dialog();
            }
        });

        //코스 위치
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

        //코스탭
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        places = new ArrayList<>();

        elv = (ExpandableListView) findViewById(R.id.elv);



        Networking();


    }

    public void Course_bookmark_custom_dialog() {
        bookmark_Dialog = new Dialog(this);
        bookmark_Dialog.setContentView(R.layout.course_bookmark_dialog);
//        bookmark_Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        bookmark_Dialog.setTitle("bookmark dialog");

        btn_bookmark_ok = (Button) bookmark_Dialog.findViewById(R.id.btn_bookmark_ok);
//        btn_bookmark_ok.setEnabled(true);
        RegistBookmark();
        getCourseBookmarkList();
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
//                ClipboardManager clipboardManager = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
//                ClipData clipData = ClipData.newPlainText("label", "복사할 텍스트");
//                clipboardManager.setPrimaryClip(clipData);

                Toast toast = Toast.makeText(Course_detail.this, "클립보드에 복사되었습니다", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, -100);
                toast.setView(layout);
                toast.show();
            }
        });
        link_share_dialog.show();

    }
        private ArrayList<Position> getData(int theme_num) {
            ArrayList<Position> allposition = new ArrayList<>();
            Position p1;
            Position p2;
            Position p3;
            switch (theme_num) {
                case COURSE_EYE :
                    p1 = new Position("서대문형무소역사관");
                    p1.course_info.add("서대문형무소역사관");

                    p2 = new Position("백범김구기념관");
                    p2.course_info.add("백범김구기념관");

                    allposition.add(p1);
                    allposition.add(p2);
                    break;
                case COURSE_WHEEL :
                    p1 = new Position("서울시립미술관");
                    p1.course_info.add("서울시립미술관");

                    p2 = new Position("한국은행 본관");
                    p2.course_info.add("한국은행 본관");

                    allposition.add(p1);
                    allposition.add(p2);
                    break;
                case COURSE_EAR :
                    p1 = new Position("서울올림픽기념관");
                    p1.course_info.add("서울올림픽기념관");

                    p2 = new Position("몽촌토성");
                    p2.course_info.add("몽촌토성");

                    allposition.add(p1);
                    allposition.add(p2);
                    break;
                case COURSE_ELDER :
                    p1 = new Position("창덕궁");
                    p1.course_info.add("창덕궁");

                    p2 = new Position("창경궁");
                    p2.course_info.add("창경궁");

                    p3 = new Position("춘원당 한방박물관");
                    p3.course_info.add("춘원당 한방박물관");

                    allposition.add(p1);
                    allposition.add(p2);
                    allposition.add(p3);
                    break;
            }

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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                //데이터 받기
                Float result = data.getFloatExtra("result",0);
                Log.v("course star result", result + "");
                postCourseStarData((double)result);
                reloadCourseStar();
//                course_path_rate_star.setRating(result);
                // 서버에 데이터 보내기
            }
        }
    }

    public void postCourseStarData(Double result){

        CourseStarModify courseStarModify = new CourseStarModify(result,select_type -2 );
        Call<BaseModel> requestDetail = networkService.postCourseStar(TOKEN_DATA, courseStarModify);

        requestDetail.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                if(response.isSuccessful()) {
                    Log.v("course star modi code", response.body().getCode().toString());
                    Log.v("course star modi status", response.body().getStatus().toString());
                    Log.v("course star modi", response.body().getMessage().toString());

                }
                else {
                    System.out.printf("fail response",response.toString());

                    Log.v("fail", "fail");
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }
    public void reloadCourseStar(){

        Call<CourseDetailResponse> requestDetail = networkService.getCourseDetail(TOKEN_DATA,select_type -3);

        requestDetail.enqueue(new Callback<CourseDetailResponse>() {
            @Override
            public void onResponse(Call<CourseDetailResponse> call, Response<CourseDetailResponse> response) {
                if(response.isSuccessful()) {
                    Log.v("course star reload code", response.body().getCode().toString());
                    Log.v("course star status", response.body().getStatus().toString());
                    Log.v("course star reload", response.body().getMessage().toString());

                    courseDetailData = response.body().getData();

                    course_path_rate_star.setRating((float)courseDetailData.getCourse_star());
                    course_path_rate_txt.setText("(" + courseDetailData.getCourse_star_count() + ")");

                }

            }

            @Override
            public void onFailure(Call<CourseDetailResponse> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }
    public void Networking(){

        Call<CourseDetailResponse> requestDetail = networkService.getCourseDetail(TOKEN_DATA,select_type -3);
        requestDetail.enqueue(new Callback<CourseDetailResponse>() {
            @Override
            public void onResponse(Call<CourseDetailResponse> call, Response<CourseDetailResponse> response) {
                if(response.isSuccessful()) {
                    ArrayList<TourInfo> tour_info;
                    courseDetailData = response.body().getData();

                    Log.v("course_idx_get", courseDetailData.getCourse_idx() + "");
                    tour_info = courseDetailData.getCourse_schedule();

                    Log.v("tour_info_get", tour_info.get(0).getTour_idx() + "");
                    Log.v("tour_info_get", tour_info.get(0).getTour_name());
                    Log.v("tour_info_get", tour_info.get(0).getTour_image());
                    Log.v("tour_detail_get", tour_info.get(0).getTour_info_detail());

//                    if(tour_info.image != ""){
//                        Glide.with(getApplicationContext())
//                                .load(response.body().result.image)
//                                .into(imgView);
//                    }

//                    for (int i = 0; i < tour_info.size(); i++) {//서버 데이터가 들어가면 구현
                    switch (courseDetailData.getCourse_theme() + 3) {
                        case COURSE_EYE :
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "서대문형무소역사관",R.drawable.img_gyeongbok_course,0));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "1층 : 추모의 장 - 영상실, 기획전시실, 자료실",14));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "2층 : 역사의 장 - 민족저항실, 형무소 역사실, 옥중생활실",14));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "3층 : 체험의 장 - 임시구금실과 고문실",14));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "백범김구기념관",R.drawable.img_gyeong_hee_course,1));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "이봉창 의사 동상"));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "백범광장"));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "전시관 1층, 2층"));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "백범 김구 선생 묘소"));
                            courses_list = getData(COURSE_EYE);
                            break;
                        case COURSE_WHEEL :
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "서울시립미술관",R.drawable.img_gyeongbok_course,0));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "지하1층 : 제1강의실, 제2강의실, 제3강의실, 세마홀",8));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "1층 : 전시실, 휴식공간",8));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "2층 : 전시실, 자료실, 천경자실",8));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "3층 : 전시실, 크리스탈 상영실, 프로젝트 갤러리",8));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "야외 : 야외조각공원",8));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "한국은행 본관",R.drawable.img_gyeong_hee_course,1));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "1층 : 우리의 중앙은행, 화폐의 일생, 돈과 나라경제, 화폐광장, 상평통보 갤러리"));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "MF : 옛 총재실, 화폐박물관 건축실, 옛 금융통화위원회 회의실"));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "2층 : 모형금고, 한은갤러리, 세계의 화폐실, 체험학습실, 기획전시실"));
                            courses_list = getData(COURSE_WHEEL);
                            break;
                        case COURSE_EAR :
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "서울올림픽기념관",R.drawable.img_gyeongbok_course,0));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "지하 1층 : 올림픽자료실",18));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "1층 : 평화의장(상설전시장), 기획전시실",18));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "2층 : 화합의장 및 번영의장 (상설전시장), 영광의장(라이드 영상관)",18));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "몽촌토성",R.drawable.img_gyeong_hee_course,1));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD,"어검당"));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "제 1~3전시관"));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "제 5~6전시관"));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "아틀리에"));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "전통다원"));
                            courses_list = getData(COURSE_EAR);
                            break;
                        case COURSE_ELDER :
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "창덕궁",R.drawable.img_gyeongbok_course,0));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "돈화문",12));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "궐내각사",12));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "금천교",12));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "인정전",12));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "선정전",12));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "희정당",12));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "대조전",12));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "낙선재",12));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "창경궁",R.drawable.img_gyeong_hee_course,1));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "홍화문",6));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "명정문",6));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "명정전",6));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "통명전",6));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "통명전",6));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "춘원당 한방박물관",R.drawable.img_geoncheon_course,2));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "약제실 및 탕전실"));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "약재품실 검사실"));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "삼국시대부터 조선시대까지 \n" + "한의학 유물전시"));
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "춘원당 역사유물 전시"));
                            courses_list = getData(COURSE_ELDER);
                            break;

                    }

                    recyclerview.setAdapter(new ExpandableListAdapter(places));


                    //create and bind to adatper
                    Course_info_list_adapter adapter = new Course_info_list_adapter(Course_detail.this,select_type, courses_list);
                    elv.setAdapter(adapter);


                    setExpandableListViewHeight(elv, -1);
                    elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                        @Override
                        public boolean onGroupClick(ExpandableListView parent, View v, int position, long id) {
                            setExpandableListViewHeight(parent, position);
                            return false;
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<CourseDetailResponse> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }
    public void RegistBookmark(){

//        if (SharedPreference.Companion.getInstance().getPrefStringData("data") != null) {
//            Call<BaseModel> requestDetail = networkService.postCourseCmt(SharedPreference.Companion.getInstance().getPrefStringData("data"),courseCmtRequest);
//
//        }

//        if (Session.getCurrentSession().isOpened()) {
//            Call<BaseModel> requestDetail = networkService.postCourseCmt(Session.getCurrentSession().getTokenInfo().getAccessToken(),courseCmtRequest);
//        }

        Call<BaseModel> requestDetail = networkService.registCourseBookmark(TOKEN_DATA, select_type - 2);

        Log.v("course_idx", select_type -2 + "");
        requestDetail.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                if(response.isSuccessful()) {

                    Log.v("course bookmark code", response.body().getCode().toString());
                    Log.v("course bookmark status", response.body().getStatus().toString());
                    Log.v("course bookmark message", response.body().getMessage().toString());

                }
                else {
                    Log.v("fail", "fail");
                }

            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }
    public void getCourseBookmarkList(){

//        if (SharedPreference.Companion.getInstance().getPrefStringData("data") != null) {
//            Call<BaseModel> requestDetail = networkService.postCourseCmt(SharedPreference.Companion.getInstance().getPrefStringData("data"),courseCmtRequest);
//
//        }

//        if (Session.getCurrentSession().isOpened()) {
//            Call<BaseModel> requestDetail = networkService.postCourseCmt(Session.getCurrentSession().getTokenInfo().getAccessToken(),courseCmtRequest);
//        }
        Call<CourseBookmarkResponse> requestDetail = networkService.getCourseBookmarks(TOKEN_DATA);

        requestDetail.enqueue(new Callback<CourseBookmarkResponse>() {
            @Override
            public void onResponse(Call<CourseBookmarkResponse> call, Response<CourseBookmarkResponse> response) {
                if(response.isSuccessful()) {

                    Log.v("course bookmark2 code", response.body().getCode().toString());
                    Log.v("course bookmark2 status", response.body().getStatus().toString());
                    Log.v("course bookmark2 ", response.body().getMessage().toString());

                    ArrayList<CourseBookmarkData> courseBookmarks = response.body().getData();

                    ArrayList<Integer> bookmark_idx = new ArrayList<>();
                    for (int i = 0; i < courseBookmarks.size(); i++) {
                        bookmark_idx.add(courseBookmarks.get(i).getCourse_idx());
                    }

                    int course_idx = select_type - 2;
                    for (int i = 0; i < bookmark_idx.size(); i++) {
                        Log.v("bookmark index", bookmark_idx.get(i) + "");
                        if (course_idx == bookmark_idx.get(i)) {
                            btn_course_bookmark.setImageResource(R.drawable.button_oval_bookmark_active);
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<CourseBookmarkResponse> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }


}

