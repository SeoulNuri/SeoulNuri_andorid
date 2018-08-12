package com.hello.seoulnuri;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity
        implements MainFragment.OnFragmentInteractionListener,
        InfoFragment.OnFragmentInteractionListener,
        PlannerFragment.OnFragmentInteractionListener,
        CourseFragment.OnFragmentInteractionListener,
        MypageFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PagerAdapter adapter = new PagerAdapter(
                getSupportFragmentManager()
        );
        ViewPager viewPager = (ViewPager)findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);


        final TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        //tabLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 56));

        ImageView mainIcon = new ImageView(this);
        mainIcon.setImageResource(R.drawable.bar_main);
        mainIcon.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ImageView infoIcon = new ImageView(this);
        infoIcon.setImageResource(R.drawable.bar_info);
        infoIcon.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ImageView plannerIcon = new ImageView(this);
        plannerIcon.setImageResource(R.drawable.bar_planner);
        plannerIcon.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ImageView courseIcon = new ImageView(this);
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
        courseIconAct.setImageResource(R.drawable.bar_course);
        courseIconAct.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        final ImageView mypageIconAct = new ImageView(this);
        mypageIconAct.setImageResource(R.drawable.bar_mypage_active);
        mypageIconAct.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        tabLayout.getTabAt(0).setCustomView(mainIcon);
        tabLayout.getTabAt(1).setCustomView(infoIcon);
        tabLayout.getTabAt(2).setCustomView(plannerIcon);
        tabLayout.getTabAt(3).setCustomView(courseIcon);
        tabLayout.getTabAt(4).setCustomView(mypageIcon);

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        tabLayout.getTabAt(0).setCustomView(mainIconAct);
                        break;
                    case 1:
                        tabLayout.getTabAt(1).setCustomView(infoIconAct);
                        break;
                    case 2:
                        tabLayout.getTabAt(2).setCustomView(plannerIconAct);
                        break;
                    case 3:
                        tabLayout.getTabAt(3).setCustomView(courseIconAct);
                        break;
                    case 4:
                        tabLayout.getTabAt(4).setCustomView(mypageIconAct);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
