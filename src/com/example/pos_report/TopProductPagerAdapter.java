package com.example.pos_report;

import com.example.pos_report.detail.SaleByDate_Detail_TopQty;
import com.example.pos_report.detail.SaleByDate_Detail_TopSale;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TopProductPagerAdapter extends FragmentStatePagerAdapter {

	
    public static final int NUM_PAGES = 2;
    private static final int ARG_SECTION_NUMBER = NUM_PAGES;
    public TopProductPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
    	switch(position) {
    	
    case 0: return SaleByDate_Detail_TopQty.newInstance(ARG_SECTION_NUMBER);	
    case 1: return SaleByDate_Detail_TopSale.newInstance(ARG_SECTION_NUMBER);
    default: return SaleByDate_Detail_TopQty.newInstance(ARG_SECTION_NUMBER);	
    }
        /*Fragment fragment = new SimpleFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_POSITION, position);
        fragment.setArguments(args);*/

        /*return fragment;*/
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

}