package com.hello.seoulnuri.view.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hello.seoulnuri.CourseFragment;
import com.hello.seoulnuri.info.InfoFragment;
import com.hello.seoulnuri.view.info.InformationFragment;
import com.hello.seoulnuri.view.main.MainFragment;
import com.hello.seoulnuri.view.mypage.MypageFragment;
import com.hello.seoulnuri.view.planner.PlannerFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return MainFragment.newInstance();
            case 1:
                //return InfoFragment.newInstance();
                return new InformationFragment();
            case 2:
                return PlannerFragment.Companion.newInstance();
            case 3:
                return CourseFragment.newInstance();
            case 4:
                return MypageFragment.Companion.newInstance();
            default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}

