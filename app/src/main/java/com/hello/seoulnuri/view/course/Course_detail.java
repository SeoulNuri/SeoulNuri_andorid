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

import com.bumptech.glide.Glide;
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
import com.hello.seoulnuri.model.course.CourseIndex;
import com.hello.seoulnuri.model.course.CourseMapData;
import com.hello.seoulnuri.model.course.CourseMapSubData;
import com.hello.seoulnuri.model.course.CourseStarData;
import com.hello.seoulnuri.model.course.CourseStarModify;
import com.hello.seoulnuri.model.course.TourInfo;
import com.hello.seoulnuri.model.info.tour.TourCommonData;
import com.hello.seoulnuri.model.info.tour.bookmark.TourBookmarkResponse;
import com.hello.seoulnuri.model.info.tour.introduce.InfoTourIntroduce;
import com.hello.seoulnuri.model.info.tour.introduce.IntroduceData;
import com.hello.seoulnuri.model.info.tour.introduce.TourBottomData;
import com.hello.seoulnuri.model.mypage.MypageBookmarkCourseData;
import com.hello.seoulnuri.model.mypage.MypageBookmarkCourseResponse;
import com.hello.seoulnuri.network.ApplicationController;
import com.hello.seoulnuri.network.NetworkService;
import com.hello.seoulnuri.utils.SharedPreference;
import com.hello.seoulnuri.view.course.adapter.CourseCommentAdapter;
import com.hello.seoulnuri.view.course.adapter.Course_info_list_adapter;
import com.hello.seoulnuri.view.course.adapter.ExpandableListAdapter;
import com.hello.seoulnuri.R;

import java.lang.reflect.Array;
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

//상세코스 페이지
    //두가지 방법으로 구성 -> 서버에서 받아온 정보 (기획과는 다름), 기획에서 받아온 자료(현재 UI)
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
    private TextView course_item_addr;
    private int booked = -1;
    private ArrayList<Integer> bookmarkList;
    private ImageView course_info_item_img;
    private TourCommonData tourCommonData;
    private TourBottomData tourBottomData;
    private ArrayList<Position> allposition;
    private Course_info_list_adapter adapter;
    private TourInfo tour_info;
    private ArrayList<TourInfo> tour_info_list;
    private ArrayList<CourseMapSubData> courseMapSubDataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance();

        //코스 이름
        course_item_txt = (TextView) findViewById(R.id.course_item_txt);
        //코스 타입 (시각장애인여행코스..)
        TextView course_type_txt = (TextView) findViewById(R.id.course_type_txt);

        intent = new Intent(this.getIntent());
        //전역변수 -선택한 코스 타입(3- eye, 4- wheel , 5-ear, 6-elder)
        select_type = intent.getIntExtra("select_type", 3);
        //코스의 별점과 별점 카운트 수 (서버에서 받아온 값) -시각, 지체, 청각, 노인 순
        courseStarList = (ArrayList<CourseItem>) intent.getSerializableExtra("courseList");

        //추천코스 소개 대표 이미지
        course_info_item_img = (ImageView) findViewById(R.id.course_img);
        //선택 유형에 따라 달라져야함
        TextView course_info_item_txt = (TextView) findViewById(R.id.course_txt);
        //추천코스 소개 대표 설명
        course_item_addr = (TextView) findViewById(R.id.course_item_addr);
        course_path_rate_txt = (TextView) findViewById(R.id.course_path_rate_txt);
        course_path_rate_star = (RatingBar) findViewById(R.id.course_path_rate_star);

        //별점을 클릭하면 별점을 부여하는 activity가 실행된다. -> 왜 두 번씩 실행될까..?
        course_path_rate_star.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(Course_detail.this, popUpActivity.class);
//                intent.putExtra("ratingbar_infos",course_path_rate_star.getRating());
                startActivityForResult(intent, 1);

                return false;
            }
        });

        //코스탭
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        places = new ArrayList<>();

        //소개 탭
        elv = (ExpandableListView) findViewById(R.id.elv);
        allposition = new ArrayList<>();

        //코스 위치
        btn_course_map = (ImageView) findViewById(R.id.btn_course_map);

        //뷰를 채울 정보를 가져온다.
        Networking();
        //북마크리스트에 해당 코스가 있는지 확인 -> 있을 경우 북마크 버튼에 표시한다.
        getCourseBookmarkList();


//        ArrayList<Integer> tour_idxes = new ArrayList<>();

        switch (select_type) {
            case COURSE_EYE:
//                tour_idxes.add(36);
//                tour_idxes.add(32);
                course_info_item_txt.setText(getResources().getString(R.string.course_info_txt_eye));
                course_item_txt.setText("독립운동의 역사");
                course_type_txt.setText("시각장애인 여행 추천 코스");
                break;
            case COURSE_WHEEL:
                course_info_item_txt.setText(getResources().getString(R.string.course_info_txt_wheel));
                course_item_txt.setText("지식과 함께, 박물관");
                course_type_txt.setText("지체장애인 여행 추천 코스");
                break;
            case COURSE_EAR:
                course_info_item_txt.setText(getResources().getString(R.string.course_info_txt_ear));
                course_item_txt.setText("8호선 지하철 여행");
                course_type_txt.setText("청각장애인 여행 추천 코스");
                break;
            case COURSE_ELDER:
                course_info_item_txt.setText(getResources().getString(R.string.course_info_txt_elder));
                course_item_txt.setText("고궁과 한의학");
                course_type_txt.setText("어르신 여행 추천 코스");
                break;

        }

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


        //코스 댓글
        btn_course_comment = (ImageView) findViewById(R.id.btn_course_comment);

        btn_course_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Course_detail.this, CourseCommentActivity.class);
                intent.putExtra("course_title", course_item_txt.getText().toString());
                intent.putExtra("course_idx", courseDetailData.getCourse_idx());
                //eye -> 1, wheel -> 2, ear ->3, elder->4
                startActivity(intent);
            }
        });

        //코스 북마크
        btn_course_bookmark = (ImageView) findViewById(R.id.btn_course_bookmark);


        btn_course_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bookmark가 이미 되어있을 경우 dialog 실행 하지 않음
                if (booked == -1)
                    Course_bookmark_custom_dialog();
            }
        });

        //코스 공유
        btn_course_share = (ImageView) findViewById(R.id.btn_course_share);

        btn_course_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Course_link_share_custom_dialog();
                //어떤 링크를 복사해야할지 알 수 없음
            }
        });


        //주소를 보내 위도 경도 검색하는 방식으로 바꾸기
        btn_course_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Course_detail.this, CourseMapActivity.class);
                courseMapSubDataList = new ArrayList<>();

//                switch (courseDetailData.getCourse_idx()) {
//                    case 1:
//                        //eye
//                        courseMapSubDataList.add(new CourseMapSubData(50, 37.574484, 126.956050, R.drawable.eye_first_map_photo, "서울특별시 서대문구 천연동 통일로 251", "서대문형무소역사관"));
//                        courseMapSubDataList.add(new CourseMapSubData(46, 37.544512, 126.959231, R.drawable.eye_sec_map_photo, "서울특별시 용산구 효창동 임정로 26", "백범김구기념관"));
//                        break;
//                    case 2:
//                        //wheel
//                        courseMapSubDataList.add(new CourseMapSubData(53, 37.570353, 126.968704, R.drawable.wheel_first_map_photo, "서울특별시 종로구 사직동 새문안로 45 ", "서울시립미술관"));
//                        courseMapSubDataList.add(new CourseMapSubData(81, 37.562344, 126.980579, R.drawable.wheel_sec_map_photo, "서울특별시 중구 남대문로5가 39", "한국은행 본관"));
//                        break;
//                    case 3:
//                        //ear
//                        courseMapSubDataList.add(new CourseMapSubData(55, 37.520400, 127.115540, R.drawable.ear_first_map_photo, "서울특별시 송파구 방이동 올림픽로 424", "서울올림픽기념관"));
//                        courseMapSubDataList.add(new CourseMapSubData(43, 37.522478, 127.120831, R.drawable.ear_sec_map_photo, "서울특별시 송파구 오륜동 올림픽로 424", "몽촌토성"));
//                        break;
//                    case 4:
//                        //elder
//                        courseMapSubDataList.add(new CourseMapSubData(6, 37.579464, 126.991021, R.drawable.elder_first_map_photo, "서울특별시 종로구 와룡동 율곡로 99", "창덕궁"));
//                        courseMapSubDataList.add(new CourseMapSubData(5, 37.579000, 126.994870, R.drawable.elder_sec_map_photo, "서울특별시 종로구 와룡동 창경궁로 185", "창경궁"));
//                        courseMapSubDataList.add(new CourseMapSubData(76, 37.571992, 126.990053, R.drawable.elder_thd_map_photo, "서울특별시 낙원동 서울특별시 종로구 돈화문로9길 27 (낙원동)", "춘원당 한방박물관"));
//                        break;
//                }

                for (int i = 0; i < tour_info_list.size(); i++) {
                    courseMapSubDataList.add(new CourseMapSubData(tour_info_list.get(i).getTour_idx(), tour_info_list.get(i).getTour_latitude(),tour_info_list.get(i).getTour_longitude(),tour_info_list.get(i).getTour_planner_img(), tour_info_list.get(i).getTour_addr(),tour_info_list.get(i).getTour_name()));
                }

                CourseMapData courseMapData = new CourseMapData(courseDetailData.getCourse_idx(), courseMapSubDataList);
                intent.putExtra("course_map_data", courseMapData);
                intent.putExtra("course_item_title", course_item_txt.getText().toString());
                intent.putExtra("course_item_addr", course_item_addr.getText().toString());
                intent.putExtra("course_star", course_path_rate_star.getRating());
                intent.putExtra("course_star_count", course_path_rate_txt.getText().toString());
                startActivity(intent);
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //데이터 받기
                Float result = data.getFloatExtra("result", 0);
                Log.v("course star result", result + "");
                postCourseStarData((double) result);
//                course_path_rate_star.setRating(result);
                // 서버에 데이터 보내기
            }
        }
    }

    //평가받은 별점값을 서버에 보냄
    public void postCourseStarData(Double result) {
        //사용자 평가 별점
        Log.v("star data ", result + "");
        CourseStarModify courseStarModify = new CourseStarModify(result, select_type - 2);
        Call<BaseModel> requestDetail = networkService.postCourseStar(TOKEN_DATA, courseStarModify);

        requestDetail.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                if (response.isSuccessful()) {
                    Log.v("course star modi code", response.body().getCode().toString());
                    Log.v("course star modi status", response.body().getStatus().toString());
                    Log.v("course star modi", response.body().getMessage().toString());

                } else {
                    System.out.printf("fail response", response.toString());

                    Log.v("fail", "fail");
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getCourseBookmarkList();
        //북마크 된 course_idx 중에 해당 course_idx가 존재할 경우
        if (booked == select_type - 2) {
            btn_course_bookmark.setImageResource(R.drawable.button_oval_bookmark_active);
        }

    }

    //북마크 다이얼로그
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
                RegistBookmark();
                bookmark_Dialog.dismiss();
            }
        });

        bookmark_Dialog.show();

    }

    //링크 공유 다이얼로그
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
            final View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.toast_custom));

            @Override
            public void onClick(View v) {
//                ClipboardManager clipboardManager = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
//                ClipData clipData = ClipData.newPlainText("label", "복사할 텍스트");
//                clipboardManager.setPrimaryClip(clipData);
//                  -> 어떤 걸 복사..?

                Toast toast = Toast.makeText(Course_detail.this, "클립보드에 복사되었습니다", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, -100);
                toast.setView(layout);
                toast.show();
            }
        });
        link_share_dialog.show();

    }

    //소개 탭의 expandablelistview의 높이 조절
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
            if (((listView.isGroupExpanded(i)) && (i != group)) || ((!listView.isGroupExpanded(i)) && (i == group))) {
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

    public void Networking() {
    //course detail info 가져오기
//        Call<CourseDetailResponse> requestDetail = networkService.getCourseDetail(SharedPreference.Companion.getInstance().getPrefStringData("data"), select_type - 3);
        Call<CourseDetailResponse> requestDetail = networkService.getCourseDetail(TOKEN_DATA, select_type - 3);
        requestDetail.enqueue(new Callback<CourseDetailResponse>() {
            @Override
            public void onResponse(Call<CourseDetailResponse> call, Response<CourseDetailResponse> response) {
                if (response.isSuccessful()) {
                    courseDetailData = response.body().getData();

                    //eye -> 1, wheel -> 2, ear ->3, elder->4
                    Log.v("courseDetail data", courseDetailData.toString());
                    tour_info_list = courseDetailData.getCourse_schedule();

                    Log.v("tour_info_get", tour_info_list.toString());

                    for (int i = 0; i < tour_info_list.size(); i++) {
                        Position p1 = new Position(tour_info_list.get(i).getTour_name(),tour_info_list.get(i).getTour_info_img(),tour_info_list.get(i).getTour_info());
                        p1.course_info.add(tour_info_list.get(i).getTour_name());
                        allposition.add(p1);
                    }

                    adapter = new Course_info_list_adapter(Course_detail.this, allposition);
                    elv.setAdapter(adapter);
                    setExpandableListViewHeight(elv, -1);
                    elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                        @Override
                        public boolean onGroupClick(ExpandableListView parent, View v, int position, long id) {
                            setExpandableListViewHeight(parent, position);
                            return false;
                        }
                    });

                    if(tour_info_list.get(0).getTour_info_img() != ""){
                        Glide.with(getApplicationContext())
                                .load(tour_info_list.get(0).getTour_info_img())
                                .into(course_info_item_img);
                    }

                    course_item_addr.setText(tour_info_list.get(0).getTour_addr());

                    course_path_rate_star.setRating((float) courseDetailData.getCourse_star());
                    course_path_rate_txt.setText("(" + courseDetailData.getCourse_star_count() + ")");
                    String[] lines;
                    String[] split_twice_lines;
                    int pos = 0;

                    for (int i = 0; i < tour_info_list.size(); i++) {
                        places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, tour_info_list.get(i).getTour_name(), tour_info_list.get(i).getTour_idx(),i, R.drawable.img_course_50));
                        lines = tour_info_list.get(i).getTour_info_detail().split("\\r?\\n\\r?\\n");
                        for (int j = 0; j < lines.length; j++){
                            Log.v("split2", lines[j]);
                            if (lines[j].contains("이용가능시설")) {
                                Log.v("parameter", lines[j]);
                                pos = j;
                            }
                        }
                        split_twice_lines = lines[pos].split("\\r?\\n");
                        for (int k = 0; k < split_twice_lines.length; k++)
                            places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD,k,tour_info_list.size(),i,split_twice_lines[k]));
                    }


                    Log.v("course taken time",courseDetailData.getCourse_taketime() +"" );
                    recyclerview.setAdapter(new ExpandableListAdapter(places,courseDetailData.getCourse_taketime()));



//                    places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "2층 : 역사의 장 - 민족저항실, 형무소 역사실, 옥중생활실", 14));
//                    places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "3층 : 체험의 장 - 임시구금실과 고문실", 14));
//                    places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "백범김구기념관", R.drawable.img_course_46, 1));
//                    places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "이봉창 의사 동상"));
//                    places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "백범광장"));
//                    places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "전시관 1층, 2층"));
//                    places.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "백범 김구 선생 묘소"));


                }

            }

            @Override
            public void onFailure(Call<CourseDetailResponse> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }

    public void RegistBookmark() {
        //eye -> 1, wheel -> 2, ear ->3, elder->4
//        Call<BaseModel> requestDetail = networkService.registCourseBookmark(SharedPreference.Companion.getInstance().getPrefStringData("data"), courseDetailData.getCourse_idx());
        CourseIndex courseIndex = new CourseIndex(select_type - 2);
        Call<TourBookmarkResponse> requestDetail = networkService.registCourseBookmark(TOKEN_DATA, courseIndex);

        requestDetail.enqueue(new Callback<TourBookmarkResponse>() {
            @Override
            public void onResponse(Call<TourBookmarkResponse> call, Response<TourBookmarkResponse> response) {
                if (response.isSuccessful()) {
                    Log.v("course bookmark code", response.body().getCode().toString());
                    Log.v("course bookmark status", response.body().getStatus().toString());
                    Log.v("course bookmark message", response.body().getMessage().toString());
                    Log.v("complete", "complete bookmark");
                }
            }

            @Override
            public void onFailure(Call<TourBookmarkResponse> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }

    public void getCourseBookmarkList() {

//        Call<CourseBookmarkResponse> requestDetail = networkService.getCourseBookmarks(SharedPreference.Companion.getInstance().getPrefStringData("data"));
        Call<MypageBookmarkCourseResponse> requestDetail = networkService.getMypageBookmarkCourse(TOKEN_DATA);

        requestDetail.enqueue(new Callback<MypageBookmarkCourseResponse>() {
            @Override
            public void onResponse(Call<MypageBookmarkCourseResponse> call, Response<MypageBookmarkCourseResponse> response) {
                if (response.isSuccessful()) {
                    Log.v("course bookmark2 code", response.body().getCode().toString());
                    Log.v("course bookmark2 status", response.body().getStatus().toString());
                    Log.v("course bookmark2 ", response.body().getMessage().toString());

                    ArrayList<MypageBookmarkCourseData> courseBookmarks = response.body().getData();
                    bookmarkList = new ArrayList<>();

                    Log.v("coursebookmarks", courseBookmarks.toString());

                    for (int i = 0; i < courseBookmarks.size(); i++) {
                        bookmarkList.add(courseBookmarks.get(i).getCourse_idx());
                    }
                    for (int i = 0; i < bookmarkList.size(); i++) {
                        if (bookmarkList.get(i) == select_type - 2) {
                            booked = i;
                            btn_course_bookmark.setImageResource(R.drawable.button_oval_bookmark_active);
                        }
                    }

                    Log.v("bookmarklist", bookmarkList.toString());
                    course_path_rate_star.setRating((float) courseBookmarks.get(booked).getCourse_star());
                    Log.v("coursebookmarks star", courseBookmarks.get(booked).getCourse_star() + "");
                }

            }

            @Override
            public void onFailure(Call<MypageBookmarkCourseResponse> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }
}

