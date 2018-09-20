package com.hello.seoulnuri.view.main;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hello.seoulnuri.view.course.CourseFragment;
import com.hello.seoulnuri.CustomViewPager;
import com.hello.seoulnuri.PagerAdapter;
import com.hello.seoulnuri.R;
import com.hello.seoulnuri.info.InfoFragment;
import com.hello.seoulnuri.model.main.MainTourResponse;
import com.hello.seoulnuri.network.ApplicationController;
import com.hello.seoulnuri.network.NetworkService;
import com.hello.seoulnuri.utils.SharedPreference;
import com.hello.seoulnuri.view.mypage.MypageFragment;
import com.hello.seoulnuri.view.planner.PlannerFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements MainFragment.OnFragmentInteractionListener,
        InfoFragment.OnFragmentInteractionListener,
        PlannerFragment.OnFragmentInteractionListener,
        CourseFragment.OnFragmentInteractionListener,
        MypageFragment.OnFragmentInteractionListener {



    private NetworkService networkService;

    // 메인에서 정보 받아오는 함수!
    public void requestMainTourInfo(){
        Call<MainTourResponse> mainTourResponse = networkService.getMainInfo(SharedPreference.Companion.getInstance().getPrefStringData("data"));
        mainTourResponse.enqueue(new Callback<MainTourResponse>() {
            @Override
            public void onResponse(Call<MainTourResponse> call, Response<MainTourResponse> response) {
                if(response.isSuccessful()){
                    Log.v("1994 main",String.valueOf(response.code()));
                    Log.v("1994 main message",String.valueOf(response.message()));
                    Log.v("1994 main status",String.valueOf(response.body().getStatus()));
                    Log.v("1994 main body",String.valueOf(response.body()));

                    //System.out.println("1994 main2"+response.body().getData().toString());

                }
            }

            @Override
            public void onFailure(Call<MainTourResponse> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        networkService = ApplicationController.Companion.getInstance().getNetworkService();
//        SharedPreference.Companion.getInstance();
//        requestMainTourInfo();
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            Window w = getWindow(); // in Activity's onCreate() for instance
            //w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }*/


        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        CustomViewPager viewPager = (CustomViewPager) findViewById(R.id.view_pager);
        viewPager.setPagingEnabled(false);
        viewPager.setAdapter(adapter);
        




        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        //tabLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 56));

        final ImageView mainIcon = new ImageView(this);
        mainIcon.setImageResource(R.drawable.bar_main);
        mainIcon.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        final ImageView infoIcon = new ImageView(this);
        infoIcon.setImageResource(R.drawable.bar_info);
        infoIcon.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        final ImageView plannerIcon = new ImageView(this);
        plannerIcon.setImageResource(R.drawable.bar_planner);
        plannerIcon.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        final ImageView courseIcon = new ImageView(this);
        courseIcon.setImageResource(R.drawable.bar_course);
        courseIcon.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        final ImageView mypageIcon = new ImageView(this);
        mypageIcon.setImageResource(R.drawable.bar_mypage);
        mypageIcon.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        final ImageView mainIconAct = new ImageView(this);
        mainIconAct.setImageResource(R.drawable.bar_main_active);
        mainIconAct.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        final ImageView infoIconAct = new ImageView(this);
        infoIconAct.setImageResource(R.drawable.bar_info_active);
        infoIconAct.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        final ImageView plannerIconAct = new ImageView(this);
        plannerIconAct.setImageResource(R.drawable.bar_planner_active);
        plannerIconAct.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        final ImageView courseIconAct = new ImageView(this);
        courseIconAct.setImageResource(R.drawable.bar_course_active);
        courseIconAct.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        final ImageView mypageIconAct = new ImageView(this);
        mypageIconAct.setImageResource(R.drawable.bar_mypage_active);
        mypageIconAct.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        tabLayout.getTabAt(0).setCustomView(mainIconAct);
        tabLayout.getTabAt(1).setCustomView(infoIcon);
        tabLayout.getTabAt(2).setCustomView(plannerIcon);
        tabLayout.getTabAt(3).setCustomView(courseIcon);
        tabLayout.getTabAt(4).setCustomView(mypageIcon);

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ImageView iv = (ImageView) tab.getCustomView();
                switch (tab.getPosition()) {
                    case 0:
                        iv.setImageResource(R.drawable.bar_main_active);
                        break;
                    case 1:
                        iv.setImageResource(R.drawable.bar_info_active);
                        break;
                    case 2:
                        iv.setImageResource(R.drawable.bar_planner_active);
                        break;
                    case 3:
                        iv.setImageResource(R.drawable.bar_course_active);
                        break;
                    case 4:
                        iv.setImageResource(R.drawable.bar_mypage_active);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab1) {
                ImageView iv = (ImageView) tab1.getCustomView();
                switch (tab1.getPosition()) {
                    case 0:
                        iv.setImageResource(R.drawable.bar_main);
                        break;
                    case 1:
                        iv.setImageResource(R.drawable.bar_info);
                        break;
                    case 2:
                        iv.setImageResource(R.drawable.bar_planner);
                        break;
                    case 3:
                        iv.setImageResource(R.drawable.bar_course);
                        break;
                    case 4:
                        iv.setImageResource(R.drawable.bar_mypage);
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab2) {
                Log.d("reselected tap", String.valueOf(tab2.getPosition()));
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
