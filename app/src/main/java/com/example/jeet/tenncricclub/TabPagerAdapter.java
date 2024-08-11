package com.example.jeet.tenncricclub;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class TabPagerAdapter extends FragmentPagerAdapter {

    /* Initialize ArrayList */
    private ArrayList<FragmentModel> fList = new ArrayList<FragmentModel>();

    /* Constructor to class */
    public TabPagerAdapter(FragmentManager fm, ArrayList<FragmentModel> _fList) {
        super(fm);
        this.fList = _fList;
    }

    /* Override method getItem() which return current tab using position */
    @Override
    public Fragment getItem(int position) {
        return fList.get(position).getTabFragment();
    }

    /* Override method getCount() to return numbers of Tabs */
    @Override
    public int getCount() {
        return this.fList.size();
    }

    /* Override method getPageTitle() to return title of Tabs by position */
    @Override
    public CharSequence getPageTitle(int position) {
        return fList.get(position).getTabTitle();
    }

}
