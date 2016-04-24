package com.hackerkernel.ondew.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mfragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();


    public FragmentViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mfragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mfragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mfragmentList.add(fragment);
        mFragmentTitleList.add(title);

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
