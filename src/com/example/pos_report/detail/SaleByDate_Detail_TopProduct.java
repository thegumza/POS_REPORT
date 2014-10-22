package com.example.pos_report.detail;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.List;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.flatuilibrary.FlatTextView;
import com.example.pos_report.R;
import com.example.pos_report.SaleByDate;
import com.example.pos_report.TopProductPagerAdapter;
import com.example.pos_report.R.id;
import com.example.pos_report.R.layout;
import com.example.pos_report.database.GetSumProductShopDao;
import com.example.pos_report.database.GetTopProductShopDao;
import com.example.pos_report.database.GlobalPropertyDao;
import com.example.pos_report.database.model.GlobalProperty;
import com.example.pos_report.database.model.SumProductShop;
import com.example.pos_report.database.model.TopProductShop;
import com.github.mikephil.charting.charts.PieChart;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class SaleByDate_Detail_TopProduct extends Fragment {
	
	private static final String ARG_SECTION_NUMBER = "section_number";
	public static String URL;
	private ViewPager mViewPager;
	private String shopName;
	private String saledate;
	private ProgressDialog pdia;
	private String ProductGroupCode="";
	private int ShopID,currentmonth,currentyear;
	final GlobalPropertyDao gpd = new GlobalPropertyDao(getActivity());
	GlobalProperty format = gpd.getGlobalProperty();
	String formatnumber = format.getCurrencyFormat();
	String formatqty = format.getQtyFormat();
	NumberFormat formatter = new DecimalFormat(formatnumber);
	NumberFormat qtyformatter = new DecimalFormat(formatqty);
	
	public static SaleByDate_Detail_TopProduct newInstance(int sectionNumber) {
		SaleByDate_Detail_TopProduct fragment = new SaleByDate_Detail_TopProduct();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			
			return fragment;
	 }
	//private ListView list_TopProduct;
	FlatTextView ShopNameValue;
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.salebydate_detail_top_detail, container, false);
                rootView.findViewById(R.id.pager);
               pdia = new ProgressDialog(getActivity());
         	   pdia.setCancelable(true);
         	   pdia.setIndeterminate(true);
         	  final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
         	   String path_ip = sharedPreferences.getString("path_ip", "");
         	  	String path_visual = sharedPreferences.getString("path_visual", "");
       	   		URL = "http://"+path_ip+"/"+path_visual+"/ws_dashboard.asmx?WSDL";
                
                shopName = SaleByDate.getShopName();
            	saledate = SaleByDate.getDate();
            	ShopID = SaleByDate.getCurrentShopID();
            	ShopNameValue = (FlatTextView)rootView.findViewById(R.id.shopNameValue);
            	ShopNameValue.setText(shopName+" ("+saledate+")");
            	mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
                PagerAdapter adapter = new TopProductPagerAdapter(getChildFragmentManager());
                mViewPager.setAdapter(adapter);
                mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                
                
                
        return rootView;
    }
	
}
