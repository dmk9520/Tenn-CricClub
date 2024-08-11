package com.example.jeet.tenncricclub;


import android.support.v4.app.Fragment;

public class FragmentModel {

    Fragment tabFragment;
    private String tabTitle = "";

    /* Used to set Fragment and Title for Tabs of TabLayout*/
    public void setTabFragment(Fragment _tabFragment, String _tabTitle) {
        this.tabFragment = _tabFragment;
        this.tabTitle = _tabTitle;
    }

    /* Used to get Fragment for TabLayout */
    public Fragment getTabFragment() {
        return this.tabFragment;
    }

    /* Used to set and get Tab title for TabLayout */
    public String getTabTitle() {
        return this.tabTitle;
    }

}