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

import com.example.flatuilibrary.FlatTextView;
import com.example.pos_report.R;
import com.example.pos_report.SaleByDate;
import com.example.pos_report.R.id;
import com.example.pos_report.R.layout;
import com.example.pos_report.database.GetSumProductShopDao;
import com.example.pos_report.database.GetTopProductShopDao;
import com.example.pos_report.database.GlobalPropertyDao;
import com.example.pos_report.database.model.GlobalProperty;
import com.example.pos_report.database.model.SumProductShop;
import com.example.pos_report.database.model.TopProductShop;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendForm;
import com.github.mikephil.charting.utils.Legend.LegendPosition;

public class SaleByDate_Detail_TopQty extends Fragment{
	private PieChart mChart;
	private static final String ARG_SECTION_NUMBER = "section_number";
	final GlobalPropertyDao gpd = new GlobalPropertyDao(getActivity());
	GlobalProperty format = gpd.getGlobalProperty();
	String formatnumber = format.getCurrencyFormat();
	String formatqty = format.getQtyFormat();
	NumberFormat formatter = new DecimalFormat(formatnumber);
	NumberFormat qtyformatter = new DecimalFormat(formatqty);
	
	String shopName = SaleByDate.getShopName();
	
	private ListView list_TopProduct;
	
	public static SaleByDate_Detail_TopQty newInstance(int sectionNumber) {
		SaleByDate_Detail_TopQty fragment = new SaleByDate_Detail_TopQty();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			
			return fragment;
	 }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.salebydate_detail_topqty_fragment, container, false);
                rootView.findViewById(R.id.salebyproduct_topqty_layout);
                mChart = (PieChart) rootView.findViewById(R.id.chart1);
                list_TopProduct = (ListView)rootView.findViewById(R.id.list_TopProduct);
                GetSumProductShopDao gt = new GetSumProductShopDao(getActivity());
                
                List<SumProductShop> Topqtyproduct = gt.getTopQtyProduct();
				list_TopProduct.setAdapter(new TopQtyProductListAdapter(Topqtyproduct));

				final List<SumProductShop> tq = gt.getTopQtyProduct();
				
				
				ArrayList<String> productname = new ArrayList<String>() ;
				ArrayList<String> saleprice = new ArrayList<String>() ;
				
				for (SumProductShop tps : tq) saleprice.add(Double.toString(tps.getSalePrice()));
				for (SumProductShop tps : tq) productname.add(tps.getProductName()+" ("+tps.getSalePrice()+")");
				String[] productnameArr = new String[productname.size()];
				productnameArr = productname.toArray(productnameArr);
				
		            ArrayList<Entry> yVals1 = new ArrayList<Entry>();
		            
		            for (int i = 0; i < productname.size(); i++) {
		            	float val = Float.parseFloat(saleprice.get(i));
		                yVals1.add(new Entry(val, i));
		            }


		            ArrayList<String> xVals = new ArrayList<String>();

		            for (int i = 0; i < productname.size(); i++)
		                xVals.add(productnameArr[i]);
		            PieDataSet set1 = new PieDataSet(yVals1, "");
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
                        mChart.setTouchEnabled(false);

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
                    l.setTextSize(14f);
                    l.setXEntrySpace(7f);
                    l.setYEntrySpace(5f);}
                return rootView;
	}
	//ListViewAdapter
	public class TopQtyProductListAdapter extends BaseAdapter{
		
		List<SumProductShop> topqtyproduct;
		public TopQtyProductListAdapter(List<SumProductShop> tq) {
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
				
				SumProductShop ts = topqtyproduct.get(position);
				holder.number_QtyValue.setText(""+(position+1));
				holder.item_QtyValue.setText(ts.getProductName());
				holder.qty_QtyValue.setText(qtyformatter.format(ts.getQty()));
				holder.salePrice_QtyValue.setText(currencyformatter.format(ts.getSalePrice()));
			
			return convertView;
				}
	}
}
