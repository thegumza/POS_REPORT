package com.example.pos_report.detail;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cengalabs.flatui.views.FlatTextView;
import com.example.pos_report.R;
import com.example.pos_report.SaleByDate;
import com.example.pos_report.database.GetSumPromotionShopDao;
import com.example.pos_report.database.GlobalPropertyDao;
import com.example.pos_report.database.model.GlobalProperty;
import com.example.pos_report.database.model.SumPromotionShop;
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
	String currencyformat = format.getCurrencyFormat();
	NumberFormat currencyformatter = new DecimalFormat(currencyformat);
	String currencysymbol = format.getCurrencySymbol();
	
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
        		List<SumPromotionShop> Promotionlist = gpr.getPromoDetail();
        		listPromotion.setAdapter(new PromotionlistAdapter(Promotionlist));
                final GetSumPromotionShopDao gp = new GetSumPromotionShopDao(getActivity());
                final SumPromotionShop gr = gpr.getSumDetailPromotion();
                double totaldis = gr.getDiscount();
        		final List<SumPromotionShop> spl = gp.getPromoDetailGraph();
        		
        		ArrayList<String> promotionname = new ArrayList<String>() ;
        		ArrayList<String> totaldiscount = new ArrayList<String>() ;
        		for (SumPromotionShop ss : spl) totaldiscount.add(Double.toString(ss.getDiscount()));
        		for (SumPromotionShop ss : spl) promotionname.add("("+(currencyformatter.format((ss.getDiscount()* 100) / totaldis))+"%) "
        		+ss.getPromotionName()+" ("+(currencyformatter.format(ss.getDiscount()))+" "+currencysymbol+")");
        		
        		String[] promotArr = new String[promotionname.size()];
        		promotArr = promotionname.toArray(promotArr);
        		
                    ArrayList<Entry> yVals1 = new ArrayList<Entry>();
                    for (int i = 0; i < promotionname.size(); i++) {
                    	float val = Float.parseFloat(totaldiscount.get(i));
                        yVals1.add(new Entry(val, i));
                    }

                    ArrayList<String> xVals = new ArrayList<String>();

                    for (int i = 0; i < promotionname.size(); i++)
                        xVals.add(promotArr[i]);
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
	                    mChart.setCenterText("Total "+currencyformatter.format(totaldis)+" "+currencysymbol);
	                    Legend l = mChart.getLegend();
	                    l.setPosition(LegendPosition.RIGHT_OF_CHART);
	                    l.setForm(LegendForm.CIRCLE);
	                    l.setTextSize(14f);
	                    l.setTypeface(tf);
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
				holder.amountPromotionValue.setText(currencyformatter.format(spr.getDiscount()));
				double totaldis = gpr.getDiscount();
				double percent = (spr.getDiscount()* 100) / totaldis;
				holder.percentPromotionValue.setText(currencyformatter.format(percent));
				text_sum_promo_amount.setText(currencyformatter.format(gpr.getDiscount()));
				text_sum_promo_percent.setText("100%");
			return convertView;
				}
 
		}
}

