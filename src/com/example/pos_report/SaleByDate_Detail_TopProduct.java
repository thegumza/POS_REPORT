package com.example.pos_report;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.appcompat.R.color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.flatuilibrary.FlatTextView;
import com.example.pos_peport.database.model.GlobalProperty;
import com.example.pos_peport.database.model.SumPaymentShop;
import com.example.pos_peport.database.model.TopProductShop;
import com.example.pos_report.SaleByDate.PaymentlistAdapter;
import com.example.pos_report.SaleByProduct.TopQtyProductListAdapter;
import com.example.pos_report.database.GetSumPaymentShopDao;
import com.example.pos_report.database.GetTopProductShopDao;
import com.example.pos_report.database.GlobalPropertyDao;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendForm;
import com.github.mikephil.charting.utils.Legend.LegendPosition;

public class SaleByDate_Detail_TopProduct extends Fragment {
	private static final String ARG_SECTION_NUMBER = "section_number";
	private PieChart mChart;
	
	
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
	private ListView list_TopProduct;
	TextView text_sum_payment_amount,text_sum_payment_percent;
	TextView SaledateValue,totalbillvalue,totalcustvalue,totalvatvalue,totalretailvalue,totaldisvalue,totalsalevalue;
	
	@SuppressLint("ResourceAsColor")
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.salebydate_detail_top_product, container, false);
                rootView.findViewById(R.id.product_detail_layout);
                
                
        		
        		
        		list_TopProduct = (ListView)rootView.findViewById(R.id.list_TopProduct);
        		list_TopProduct.setOnTouchListener(new ListView.OnTouchListener() {
     			   
        			@Override
        			public boolean onTouch(View v, MotionEvent event) {
        				   v.getParent().requestDisallowInterceptTouchEvent(true);
        				return false;
        			}
        		});
        		
        		GetTopProductShopDao gt = new GetTopProductShopDao(getActivity());
        		//Set ListViewAdapter Payment
        		List<TopProductShop> Topqtyproduct = gt.getTopQtyProduct();
				list_TopProduct.setAdapter(new TopQtyProductListAdapter(Topqtyproduct));
        		
        		

                /*Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

                mChart.setValueTypeface(tf);
                mChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
        */		
                

        		
        		/*final List<SumPaymentShop> spl = gp.getPaymentGraph();
        		
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
                    l.setYEntrySpace(5f);}*/
        return rootView;
    }
	//ListViewAdapter
	public class TopQtyProductListAdapter extends BaseAdapter{
		
		List<TopProductShop> topqtyproduct;
		public TopQtyProductListAdapter(List<TopProductShop> tq) {
			topqtyproduct = tq;
		}
		@Override
		public int getCount() {
			return topqtyproduct.size();
		}

		@Override
		public Object getItem(int position) {
			return topqtyproduct.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		private class ViewHolder {
			FlatTextView number_QtyValue;
			FlatTextView item_QtyValue;
			FlatTextView qty_QtyValue;
			FlatTextView salePrice_QtyValue;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder  holder;
			LayoutInflater inflater = getActivity().getLayoutInflater();
			
				if(convertView == null){
					convertView = inflater.inflate(R.layout.salebyproduct_top_qty_column, parent,false);
				holder = new ViewHolder();
				holder.number_QtyValue=(FlatTextView)convertView.findViewById(R.id.number_QtyValue);
				holder.item_QtyValue=(FlatTextView)convertView.findViewById(R.id.item_QtyValue);
				holder.qty_QtyValue=(FlatTextView)convertView.findViewById(R.id.qty_QtyValue);
				holder.salePrice_QtyValue=(FlatTextView)convertView.findViewById(R.id.salePrice_QtyValue);
				
				convertView.setTag(holder);
				}else{
					holder=(ViewHolder)convertView.getTag();
				}
				final GlobalPropertyDao gpd = new GlobalPropertyDao(getActivity());
				GlobalProperty format = gpd.getGlobalProperty();
				String currencyformat = format.getCurrencyFormat();
				String qtyformat = format.getQtyFormat();
				NumberFormat currencyformatter = new DecimalFormat(currencyformat);
				NumberFormat qtyformatter = new DecimalFormat(qtyformat);
				
				TopProductShop ts = topqtyproduct.get(position);
				holder.number_QtyValue.setText(""+(position+1));
				holder.item_QtyValue.setText(ts.getProductName());
				holder.qty_QtyValue.setText(qtyformatter.format(ts.getQty()));
				holder.salePrice_QtyValue.setText(currencyformatter.format(ts.getSumSalePrice()));
				//holder.percentValue.setText(formatter.format((st.getSalePrice())));
				//text_sum_totalBill.setText(formatter.format(Sumsale.getTotalBill()));
				//text_sum_discount.setText(formatter.format(Sumsale.getDiscount()));
				//text_sum_salePrice.setText(formatter.format(Sumsale.getSalePrice()));
			
			return convertView;
				}
	}	
}
