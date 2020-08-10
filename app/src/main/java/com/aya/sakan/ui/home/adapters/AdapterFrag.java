package com.aya.sakan.ui.home.adapters;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.aya.sakan.ui.home.salePosts.SaleFragment;
import com.aya.sakan.ui.home.rentPosts.RentFragment;

public class AdapterFrag extends FragmentPagerAdapter {

    public AdapterFrag(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new SaleFragment();
            case 1:
                return new RentFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
