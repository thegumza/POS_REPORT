package com.example.pos_report;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.example.flatuilibrary.FlatTextView;
import com.example.pos_peport.database.model.GlobalProperty;
import com.example.pos_report.database.GlobalPropertyDao;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SaleByDate_Detail extends Fragment {
	private static final String ARG_SECTION_NUMBER = "section_number";
	
	public static SaleByDate_Detail newInstance(int sectionNumber) {
		SaleByDate_Detail fragment = new SaleByDate_Detail();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			
			return fragment;
	 }
	
	FlatTextView SaledateVal,SaledateValue,totalbillvalue,totalcustvalue,totalvatvalue,totalretailvalue,totaldisvalue,totalsalevalue;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		SaleByDate sp = new SaleByDate();
		String Saledate = sp.getDate();
		int totalbill = sp.getTotalBill();
		int totalcust = sp.getTotalCust();
		double totalvat = sp.getTotalVat();
		double totalretail = sp.getTotalRetail();
		double totaldis = sp.getTotalDis();
		double totalsale = sp.getTotalSale();
    	
        int position = 0;

        Bundle bundle = getArguments();

        //position = bundle.getInt(SimplePagerAdapter.ARGS_POSITION);

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.salebydate_detail_fragment, container, false);
                rootView.findViewById(R.id.linear_layout);
                
                SaledateVal = (FlatTextView)rootView.findViewById(R.id.Saledate);
        		totalbillvalue = (FlatTextView)rootView.findViewById(R.id.TotalBillValue);
        		totalcustvalue = (FlatTextView)rootView.findViewById(R.id.ToTalCustValue);
        		totalvatvalue = (FlatTextView)rootView.findViewById(R.id.TotalVatValue);
        		totalretailvalue = (FlatTextView)rootView.findViewById(R.id.TotalRetailValue);
        		totaldisvalue = (FlatTextView)rootView.findViewById(R.id.TotalDisValue);
        		totalsalevalue = (FlatTextView)rootView.findViewById(R.id.TotalSaleValue);
        		//Number Format
        		final GlobalPropertyDao gpd = new GlobalPropertyDao(getActivity());
        		GlobalProperty format = gpd.getglobalproperty();
        		String formatnumber = format.getCurrencyFormat();
        		NumberFormat formatter = new DecimalFormat(formatnumber);
        		SaledateVal.setText("SaleDate ("+Saledate+")");
        		totalbillvalue.setText(""+totalbill);
        		totalcustvalue.setText(""+totalcust);
        		totalvatvalue.setText(""+formatter.format((totalvat)));
        		totalretailvalue.setText(""+formatter.format((totalretail)));
        		totaldisvalue.setText(""+formatter.format((totaldis)));
        		totalsalevalue.setText(""+formatter.format((totalsale)));
        		
        return rootView;
    }
	
	
}