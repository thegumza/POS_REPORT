package com.example.pos_report.detail;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import com.cengalabs.flatui.views.FlatTextView;
import com.example.pos_report.R;
import com.example.pos_report.SaleByDate;
import com.example.pos_report.database.GetSumPaymentShopDao;
import com.example.pos_report.database.GlobalPropertyDao;
import com.example.pos_report.database.model.GlobalProperty;
import com.example.pos_report.database.model.SumPaymentShop;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendForm;
import com.github.mikephil.charting.utils.Legend.LegendPosition;

import android.graphics.Typeface;
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
	String currencyformat = format.getCurrencyFormat();
	NumberFormat currencyformatter = new DecimalFormat(currencyformat);
	String currencysymbol = format.getCurrencySymbol();
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
	FlatTextView text_sum_payment_amount,text_sum_payment_percent;
	
	
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
                
                
                ShopNameValue = (FlatTextView) rootView.findViewById(R.id.shopNameValue);
        		totalbillvalue = (FlatTextView) rootView.findViewById(R.id.TotalBillValue);
        		totalcustvalue = (FlatTextView) rootView.findViewById(R.id.ToTalCustValue);
        		totalvatvalue = (FlatTextView) rootView.findViewById(R.id.TotalVatValue);
        		totalretailvalue = (FlatTextView) rootView.findViewById(R.id.TotalRetailValue);
        		totaldisvalue = (FlatTextView) rootView.findViewById(R.id.TotalDisValue);
        		totalsalevalue = (FlatTextView) rootView.findViewById(R.id.TotalSaleValue);
        		
        		ShopNameValue.setText(shopName+" ("+ saledate +")");
        		totalbillvalue.setText("" + totalbill+" ");
				totalcustvalue.setText("" + totalcust+" ");
        		totalvatvalue.setText("" + currencyformatter.format((totalvat))+" "+currencysymbol);
        		totalretailvalue.setText("" + currencyformatter.format((totalretail))+" "+currencysymbol);
        		totaldisvalue.setText("" + currencyformatter.format((totaldis))+" "+currencysymbol);
        		totalsalevalue.setText("" + currencyformatter.format((totalsale))+" "+currencysymbol);
        		text_sum_payment_amount = (FlatTextView) rootView.findViewById(R.id.text_sum_payment_amount);
        		text_sum_payment_percent = (FlatTextView) rootView.findViewById(R.id.text_sum_payment_percent);
        		
        		
        		listPaymentDetail = (ListView) rootView.findViewById(R.id.listPaymentdetail);

        		final GetSumPaymentShopDao gp = new GetSumPaymentShopDao(getActivity());
        		List<SumPaymentShop> Paymentlist = gp.getPaymentDetail();
        		final SumPaymentShop gsp = gp.getSumDetailPayment();
        		
        		listPaymentDetail.setAdapter(new PaymentlistDetailAdapter(Paymentlist));
        		
        		
        	        		final List<SumPaymentShop> spl = gp.getPaymentGraph();
        	        		
        	        		ArrayList<String> paytype = new ArrayList<String>() ;
        	        		ArrayList<String> totalpay = new ArrayList<String>() ;
        	        		double sumtotalpay = gsp.getTotalPay();
        	        		
        	        		for (SumPaymentShop ss : spl) totalpay.add(Double.toString(ss.getTotalPay()));
        	        		for (SumPaymentShop ss : spl) paytype.add("("+(currencyformatter.format((ss.getTotalPay()*100) / sumtotalpay))+"%) "
        	        		+ss.getPayTypeName()+" ("+(currencyformatter.format(ss.getTotalPay()))+" "+currencysymbol+")");
        	        		String[] paytypeArr = new String[paytype.size()];
        	        		paytypeArr = paytype.toArray(paytypeArr);
        	                    ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        	                    for (int i = 0; i < paytype.size(); i++) {
        	                    	float val = Float.parseFloat(totalpay.get(i));
        	                        yVals1.add(new Entry(val, i));
        	                    }
        	                    ArrayList<String> xVals = new ArrayList<String>();

        	                    for (int i = 0; i < paytype.size(); i++)
        	                        xVals.add(paytypeArr[i]);
        	                    PieDataSet set1 = new PieDataSet(yVals1, "");
        	                    set1.setColors(ColorTemplate.PASTEL_COLORS);

        	                    PieData data = new PieData(xVals, set1);
        	                    if(data.getYValCount() == 0){}
        	                    else{
        	                    	//Graph
        	                    	Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        	                    	mChart = (PieChart) rootView.findViewById(R.id.chart1);
        	                    	mChart.setValueTypeface(tf);
        	                        mChart.setCenterTextTypeface(tf);
        	                    	mChart.setHoleRadius(50f);
        	                        mChart.setDescription("");
        	                        mChart.setDrawYValues(true);
        	                        mChart.setDrawCenterText(true);
        	                        mChart.setDrawHoleEnabled(true);
        	                        mChart.setDrawXValues(false);
        	                        mChart.setTouchEnabled(false);
        	                        mChart.setUsePercentValues(true);
        	                        mChart.animateXY(1500, 1500);
	        	                    mChart.setData(data);
	        	                    mChart.highlightValues(null);
	        	                    mChart.setCenterText("Total "+currencyformatter.format(sumtotalpay)+" "+currencysymbol);
	        	                    Legend l = mChart.getLegend();
	        	                    l.setPosition(LegendPosition.RIGHT_OF_CHART);
	        	                    l.setForm(LegendForm.CIRCLE);
	        	                    l.setTextSize(14f);
	        	                    l.setTypeface(tf);
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
				holder.amountPaymentValue.setText(currencyformatter.format(sp.getTotalPay()));
				double totalpay = gsp.getTotalPay();
				double percent = (sp.getTotalPay()* 100) / totalpay;
				holder.percentPaymentValue.setText(currencyformatter.format(percent));
				text_sum_payment_amount.setText(currencyformatter.format(gsp.getTotalPay()));
				text_sum_payment_percent.setText("100%");
			return convertView;
				}}
	
}