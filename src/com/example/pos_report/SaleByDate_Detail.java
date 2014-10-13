package com.example.pos_report;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.flatuilibrary.FlatTextView;
import com.example.pos_peport.database.model.GlobalProperty;
import com.example.pos_peport.database.model.SumPaymentShop;
import com.example.pos_report.database.GetSumPaymentShopDao;
import com.example.pos_report.database.GlobalPropertyDao;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendForm;
import com.github.mikephil.charting.utils.Legend.LegendPosition;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SaleByDate_Detail extends Fragment {
	private static final String ARG_SECTION_NUMBER = "section_number";
	private PieChart mChart;
	final GlobalPropertyDao gpd = new GlobalPropertyDao(getActivity());
	GlobalProperty format = gpd.getGlobalProperty();
	String formatnumber = format.getCurrencyFormat();
	String formatqty = format.getQtyFormat();
	NumberFormat formatter = new DecimalFormat(formatnumber);
	NumberFormat qtyformatter = new DecimalFormat(formatqty);
	String shopName = SaleByDate.getShopName();
	String saledate = SaleByDate.getDate();
	
	public static SaleByDate_Detail newInstance(int sectionNumber) {
		SaleByDate_Detail fragment = new SaleByDate_Detail();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			
			return fragment;
	 }
	
	FlatTextView ShopNameValue,SaledateValue,totalbillvalue,totalcustvalue,totalvatvalue,totalretailvalue,totaldisvalue,totalsalevalue;
	ListView listPaymentDetail;
	TextView text_sum_payment_amount,text_sum_payment_percent;
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		
		int totalbill = SaleByDate.getTotalBill();
		int totalcust = SaleByDate.getTotalCust();
		double totalvat = SaleByDate.getTotalVat();
		double totalretail = SaleByDate.getTotalRetail();
		double totaldis = SaleByDate.getTotalDis();
		double totalsale = SaleByDate.getTotalSale();

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.salebydate_detail_saledate_fragment, container, false);
                rootView.findViewById(R.id.saledate_linear_layout);
             // Uncorrect Display Sale Date Detail
        		text_sum_payment_amount = (TextView) rootView
        				.findViewById(R.id.text_sum_payment_amount);
        		text_sum_payment_percent = (TextView) rootView
        				.findViewById(R.id.text_sum_payment_percent);
        		listPaymentDetail = (ListView) rootView.findViewById(R.id.listPaymentDetail);

        		final GetSumPaymentShopDao gp = new GetSumPaymentShopDao(getActivity());
        		// Set ListViewAdapter Payment
        		List<SumPaymentShop> Paymentlist = gp.getPaymentDetail();
        		listPaymentDetail.setAdapter(new PaymentlistDetailAdapter(Paymentlist));
        		
                ShopNameValue = (FlatTextView) rootView.findViewById(R.id.shopNameValue);
		totalbillvalue = (FlatTextView) rootView
				.findViewById(R.id.TotalBillValue);
		totalcustvalue = (FlatTextView) rootView
				.findViewById(R.id.ToTalCustValue);
		totalvatvalue = (FlatTextView) rootView
				.findViewById(R.id.TotalVatValue);
		totalretailvalue = (FlatTextView) rootView
				.findViewById(R.id.TotalRetailValue);
		totaldisvalue = (FlatTextView) rootView
				.findViewById(R.id.TotalDisValue);
		totalsalevalue = (FlatTextView) rootView
				.findViewById(R.id.TotalSaleValue);
		
		ShopNameValue.setText(shopName+" (" + saledate + ")");
		totalbillvalue.setText("" + totalbill);
		totalcustvalue.setText("" + totalcust);
		totalvatvalue.setText("" + formatter.format((totalvat)));
		totalretailvalue.setText("" + formatter.format((totalretail)));
		totaldisvalue.setText("" + formatter.format((totaldis)));
		totalsalevalue.setText("" + formatter.format((totalsale)));
        		
        		 
        	                
		
        	        		
        	        		

        	                /*Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        	                mChart.setValueTypeface(tf);
        	                mChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
        	        */		
        	                

        	        		
        	        		final List<SumPaymentShop> spl = gp.getPaymentGraph();
        	        		
        	        		ArrayList<String> paytype = new ArrayList<String>() ;
        	        		ArrayList<String> totalpay = new ArrayList<String>() ;
        	        		for (SumPaymentShop ss : spl) totalpay.add(Double.toString(ss.getTotalPay()));
        	        		for (SumPaymentShop ss : spl) paytype.add(ss.getPayTypeName()+" ("+ss.getTotalPay()+" Baht)");
        	        		String[] paytypeArr = new String[paytype.size()];
        	        		paytypeArr = paytype.toArray(paytypeArr);
        	        		
        	                //String[] mParties = new String[] {
        	                       // "Party A", "Party B", "Party C", "Party D", "Party E"
        	               // };


        	                    ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        	                    // ArrayList<Entry> yVals2 = new ArrayList<Entry>();

        	                    // IMPORTANT: In a PieChart, no values (Entry) should have the same
        	                    // xIndex (even if from different DataSets), since no values can be
        	                    // drawn above each other.
        	                    for (int i = 0; i < paytype.size(); i++) {
        	                    	float val = Float.parseFloat(totalpay.get(i));
        	                        yVals1.add(new Entry(val, i));
        	                    }

        	                    // for (int i = mSeekBarX.getProgress() / 2; i <
        	                    // mSeekBarX.getProgress(); i++) {
        	                    // yVals2.add(new Entry((float) (Math.random() * mult) + mult / 5, i));
        	                    // }

        	                    ArrayList<String> xVals = new ArrayList<String>();

        	                    for (int i = 0; i < paytype.size(); i++)
        	                        xVals.add(paytypeArr[i]);
        	                    PieDataSet set1 = new PieDataSet(yVals1, "");
        	                    set1.setSliceSpace(3f);
        	                    set1.setColors(ColorTemplate.createColors(getActivity(),
        	                            ColorTemplate.PASTEL_COLORS));

        	                    PieData data = new PieData(xVals, set1);
        	                    if(data.getYValCount() == 0){}
        	                    else{
        	                    	//Graph
        	                		mChart = (PieChart) rootView.findViewById(R.id.chart1);
        	                    	mChart.setHoleRadius(50f);
        	                        mChart.setDescription("");
        	                        mChart.setDrawYValues(true);
        	                        mChart.setDrawCenterText(true);
        	                        mChart.setDrawHoleEnabled(true);

        	                        // draws the corresponding description value into the slice
        	                        mChart.setDrawXValues(false);
        	                        mChart.setTouchEnabled(true);

        	                        // display percentage values
        	                        mChart.setUsePercentValues(true);
        	                        // mChart.setUnit(" €");
        	                        // mChart.setDrawUnitsInChart(true);

        	                        // add a selection listener

        	                        mChart.animateXY(1500, 1500);
        	                    mChart.setData(data);
        	                    // undo all highlights
        	                    mChart.highlightValues(null);
        	                    // set a text for the chart center
        	                    mChart.setCenterText("Total Price " + (int) mChart.getYValueSum() + " Baht");
        	                    //mChart.invalidate();
        	                    Legend l = mChart.getLegend();
        	                    l.setPosition(LegendPosition.RIGHT_OF_CHART);
        	                    l.setForm(LegendForm.CIRCLE);
        	                    l.setTextSize(10f);
        	                    l.setXEntrySpace(7f);
        	                    l.setYEntrySpace(5f);}
        return rootView;
    }
public class PaymentlistDetailAdapter extends BaseAdapter{
		
		List<SumPaymentShop> Paymentlist;
		
		public PaymentlistDetailAdapter(List<SumPaymentShop> pl) {
			Paymentlist = pl;
		}
		@Override
		public int getCount() {
			return Paymentlist.size();
		}

		@Override
		public Object getItem(int position) {
			return Paymentlist.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		private class ViewHolder {
			TextView typePaymentValue;
			TextView amountPaymentValue;
			TextView percentPaymentValue;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder  holder;
			LayoutInflater inflater = getActivity().getLayoutInflater();
			
				if(convertView == null){
					convertView = inflater.inflate(R.layout.salebydate_payment_column, parent,false);
				holder = new ViewHolder();
				holder.typePaymentValue=(TextView)convertView.findViewById(R.id.typePaymentValue);
				holder.amountPaymentValue=(TextView)convertView.findViewById(R.id.amountPaymentValue);
				holder.percentPaymentValue=(TextView)convertView.findViewById(R.id.percentPaymentValue);
				
				convertView.setTag(holder);
				}else{
					holder=(ViewHolder)convertView.getTag();
				}
				final GetSumPaymentShopDao gp = new GetSumPaymentShopDao(getActivity());
				final SumPaymentShop gsp = gp.getSumPaymentDetail();
				
				SumPaymentShop sp = Paymentlist.get(position);
				holder.typePaymentValue.setText(sp.getPayTypeName());
				holder.amountPaymentValue.setText(formatter.format(sp.getTotalPay()));
				double totalpay = gsp.getTotalPay();
				double percent = (sp.getTotalPay()* 100) / totalpay;
				holder.percentPaymentValue.setText(formatter.format(percent));
				text_sum_payment_amount.setText(formatter.format(gsp.getTotalPay()));
				text_sum_payment_percent.setText("100%");
				
			return convertView;
				}}
	
}