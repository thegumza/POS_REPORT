package com.example.pos_report;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flatuilibrary.FlatTextView;
import com.example.pos_peport.database.model.GlobalProperty;
import com.example.pos_peport.database.model.SumPaymentShop;
import com.example.pos_peport.database.model.SumPromotionShop;
import com.example.pos_report.SaleByDate.PromotionlistAdapter;
import com.example.pos_report.database.GetSumPaymentShopDao;
import com.example.pos_report.database.GetSumPromotionShopDao;
import com.example.pos_report.database.GlobalPropertyDao;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendForm;
import com.github.mikephil.charting.utils.Legend.LegendPosition;

public class SaleByDate_Detail_Promotion extends Fragment {
	private static final String ARG_SECTION_NUMBER = "section_number";
	private PieChart mChart;
	final GlobalPropertyDao gpd = new GlobalPropertyDao(getActivity());
	GlobalProperty format = gpd.getGlobalProperty();
	String formatnumber = format.getCurrencyFormat();
	String formatqty = format.getQtyFormat();
	NumberFormat formatter = new DecimalFormat(formatnumber);
	NumberFormat qtyformatter = new DecimalFormat(formatqty);
	
	
	public static SaleByDate_Detail_Promotion newInstance(int sectionNumber) {
		SaleByDate_Detail_Promotion fragment = new SaleByDate_Detail_Promotion();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			
			return fragment;
	 }
	TextView text_sum_promo_amount,text_sum_promo_percent;
	TextView ShopNameValue,SaledateValue,totalbillvalue,totalcustvalue,totalvatvalue,totalretailvalue,totaldisvalue,totalsalevalue;
	ListView listPromotion;
	String shopName = SaleByDate.getShopName();
	String saledate = SaleByDate.getDate();
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.salebydate_detail_promotion_fragment, container, false);
                rootView.findViewById(R.id.promotion_detail_layout);
                
                ShopNameValue = (FlatTextView) rootView.findViewById(R.id.shopNameValue);
                text_sum_promo_amount = (TextView)rootView.findViewById(R.id.text_sum_promo_amount);
        		text_sum_promo_percent = (TextView)rootView.findViewById(R.id.text_sum_promo_percent);
        		listPromotion = (ListView)rootView.findViewById(R.id.listPromotion);
        		ShopNameValue.setText(shopName+" (" + saledate + ")");
                listPromotion.setOnTouchListener(new ListView.OnTouchListener() {
     			   
        			@Override
        			public boolean onTouch(View v, MotionEvent event) {
        				   v.getParent().requestDisallowInterceptTouchEvent(true);
        				return false;
        			}
        		});
                
        		
        		final GetSumPromotionShopDao gpr = new GetSumPromotionShopDao(getActivity());
        		
        		//Set ListViewAdapter Promotion 
        		List<SumPromotionShop> Promotionlist = gpr.getPromoDetail();
        		listPromotion.setAdapter(new PromotionlistAdapter(Promotionlist));
        		
        		
        		

                /*Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

                mChart.setValueTypeface(tf);
                mChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
        */		
                

        		
                final GetSumPromotionShopDao gp = new GetSumPromotionShopDao(getActivity());
        		final List<SumPromotionShop> spl = gp.getPromoDetailGraph();
        		
        		ArrayList<String> promotionname = new ArrayList<String>() ;
        		ArrayList<String> totaldiscount = new ArrayList<String>() ;
        		for (SumPromotionShop ss : spl) totaldiscount.add(Double.toString(ss.getDiscount()));
        		for (SumPromotionShop ss : spl) promotionname.add(ss.getPromotionName()+" ("+ss.getDiscount()+")");
        		String[] promotArr = new String[promotionname.size()];
        		promotArr = promotionname.toArray(promotArr);
        		
                //String[] mParties = new String[] {
                       // "Party A", "Party B", "Party C", "Party D", "Party E"
               // };


                    ArrayList<Entry> yVals1 = new ArrayList<Entry>();
                    // ArrayList<Entry> yVals2 = new ArrayList<Entry>();

                    // IMPORTANT: In a PieChart, no values (Entry) should have the same
                    // xIndex (even if from different DataSets), since no values can be
                    // drawn above each other.
                    for (int i = 0; i < promotionname.size(); i++) {
                    	float val = Float.parseFloat(totaldiscount.get(i));
                        yVals1.add(new Entry(val, i));
                    }

                    // for (int i = mSeekBarX.getProgress() / 2; i <
                    // mSeekBarX.getProgress(); i++) {
                    // yVals2.add(new Entry((float) (Math.random() * mult) + mult / 5, i));
                    // }

                    ArrayList<String> xVals = new ArrayList<String>();

                    for (int i = 0; i < promotionname.size(); i++)
                        xVals.add(promotArr[i]);
                    PieDataSet set1 = new PieDataSet(yVals1, "");
                    set1.setSliceSpace(3f);
                    set1.setColors(ColorTemplate.VORDIPLOM_COLORS);

                    PieData data = new PieData(xVals, set1);
                    if(data.getYValCount() == 0){}
                    else{
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
                    mChart.setCenterText("Total Price " + (int) mChart.getYValueSum());
                    //mChart.invalidate();
                    Legend l = mChart.getLegend();
                    l.setPosition(LegendPosition.RIGHT_OF_CHART);
                    l.setForm(LegendForm.CIRCLE);
                    l.setTextSize(10f);
                    l.setXEntrySpace(7f);
                    l.setYEntrySpace(5f);}
        return rootView;
    }
	

	public class PromotionlistAdapter extends BaseAdapter{
		
		List<SumPromotionShop> Promotiontlist;
		
		public PromotionlistAdapter(List<SumPromotionShop> prl) {
			Promotiontlist = prl;
		}
		@Override
		public int getCount() {
			return Promotiontlist.size();
		}

		@Override
		public Object getItem(int position) {
			return Promotiontlist.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		private class ViewHolder {
			TextView typePromotionValue;
			TextView amountPromotionValue;
			TextView percentPromotionValue;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder  holder;
			LayoutInflater inflater = getActivity().getLayoutInflater();
			
				if(convertView == null){
					convertView = inflater.inflate(R.layout.salebydate_promotion_column, parent,false);
				holder = new ViewHolder();
				holder.typePromotionValue=(TextView)convertView.findViewById(R.id.typePromotionValue);
				holder.amountPromotionValue=(TextView)convertView.findViewById(R.id.amountPromotionValue);
				holder.percentPromotionValue=(TextView)convertView.findViewById(R.id.percentPromotionValue);
				convertView.setTag(holder);
				}else{
					holder=(ViewHolder)convertView.getTag();
				}
				final GetSumPromotionShopDao gp = new GetSumPromotionShopDao(getActivity());
				final SumPromotionShop gpr = gp.getSumPromoDetail();
				
				
				SumPromotionShop spr = Promotiontlist.get(position);
				holder.typePromotionValue.setText(spr.getPromotionName());
				holder.amountPromotionValue.setText(formatter.format(spr.getDiscount()));
				double totaldis = gpr.getDiscount();
				double percent = (spr.getDiscount()* 100) / totaldis;
				holder.percentPromotionValue.setText(formatter.format(percent));
				text_sum_promo_amount.setText(formatter.format(gpr.getDiscount()));
				text_sum_promo_percent.setText("100%");
			return convertView;
				}
 
		}
}

