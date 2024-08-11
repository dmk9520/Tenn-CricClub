package com.example.jeet.tenncricclub;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class match_details extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);
        /* get a list of Fragments and Titles for TabLayout */
        ArrayList<FragmentModel> fList = fragmentList();

        /* Initialize ViewPager and Add Adapert to ViewPager */
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager(), fList));

        /* Initialize TabLayout and Setup ViewPager with TabLayout */
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

    }

    /* This function will return ArrayList of Fragments and Titles for Tabs */
    public ArrayList<FragmentModel> fragmentList() {

        ArrayList<FragmentModel> fList = new ArrayList<FragmentModel>();
        FragmentModel fModel;

        fModel = new FragmentModel();
        fModel.setTabFragment(new matchinfo(), "Match Info");
        fList.add(fModel);

        fModel = new FragmentModel();
        fModel.setTabFragment(new score(), "Score");
        fList.add(fModel);

        return fList;

    }

}