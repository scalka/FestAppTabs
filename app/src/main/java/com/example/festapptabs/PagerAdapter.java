package com.example.festapptabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
//class to manage the tabs
class PagerAdapter extends FragmentStatePagerAdapter {

    private final int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }
    //method to check which tab was picked
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new GenresTabFragment();
            case 1:
                return new MapTabFragment();
            case 2:
                return new MyTicketsFragment();
            default:
                return null;
        }
    }
    // gets the number of tabs
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
