package com.example.pos_report;

import com.example.pos_report.detail.SaleByDate_Detail;
import com.example.pos_report.detail.SaleByDate_Detail_Promotion;
import com.example.pos_report.detail.SaleByDate_Detail_TopProduct;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class SimplePagerAdapter extends FragmentStatePagerAdapter {

	
    public static final int NUM_PAGES = 3;
    private static final int ARG_SECTION_NUMBER = NUM_PAGES;
    public SimplePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
    	switch(position) {
    	
    case 0: return SaleByDate_Detail.newInstance(ARG_SECTION_NUMBER);	
    case 1: return SaleByDate_Detail_Promotion.newInstance(ARG_SECTION_NUMBER);
    case 2: return SaleByDate_Detail_TopProduct.newInstance(ARG_SECTION_NUMBER);
    default: return SaleByDate_Detail.newInstance(ARG_SECTION_NUMBER);	
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