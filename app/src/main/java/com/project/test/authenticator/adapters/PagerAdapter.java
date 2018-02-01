package com.project.test.authenticator.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.project.test.authenticator.tabs.tabHome;
import com.project.test.authenticator.tabs.tabSettings;
import com.project.test.authenticator.tabs.tabSummary;

/**
 * Created by Ivica on 1.2.2018..
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    
    public PagerAdapter(FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {


        switch (position){
            case 0:
                tabHome tab1 = new tabHome();
                return tab1;
            case 1:
                tabSummary tab2 = new tabSummary();
                return tab2;
            case 2:
                tabSettings tab3 = new tabSettings();
                return tab3;
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
