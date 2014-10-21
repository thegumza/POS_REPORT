package com.example.pos_report;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.flatuilibrary.FlatTextView;
import com.example.pos_peport.database.model.GlobalProperty;
import com.example.pos_peport.database.model.SumProductShop;
import com.example.pos_report.database.GetSumProductShopDao;
import com.example.pos_report.database.GlobalPropertyDao;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendForm;
import com.github.mikephil.charting.utils.Legend.LegendPosition;

public class SaleByDate_Detail_TopSale extends Fragment{
	private PieChart mChart;
	private static final String ARG_SECTION_NUMBER = "section_number";
	final GlobalPropertyDao gpd = new GlobalPropertyDao(getActivity());
	GlobalProperty format = gpd.getGlobalProperty();
	String formatnumber = format.getCurrencyFormat();
	String formatqty = format.getQtyFormat();
	NumberFormat formatter = new DecimalFormat(formatnumber);
	NumberFormat qtyformatter = new DecimalFormat(formatqty);
	
	String shopName = SaleByDate.getShopName();
	String payTypeName = SaleByDate.getPayTypeName();
	
	private ListView list_TopProduct;
	
	public static SaleByDate_Detail_TopSale newInstance(int sectionNumber) {
		SaleByDate_Detail_TopSale fragment = new SaleByDate_Detail_TopSale();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			
			return fragment;
	 }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.salebydate_detail_topsale_fragment, container, false);
                rootView.findViewById(R.id.salebyproduct_topsale_layout);
                mChart = (PieChart) rootView.findViewById(R.id.chart1);
                list_TopProduct = (ListView)rootView.findViewById(R.id.list_TopProduct);
                
                GetSumProductShopDao gt = new GetSumProductShopDao(getActivity());
                List<SumProductShop> Topqtyproduct = gt.getTopSaleProduct();
				list_TopProduct.setAdapter(new TopQtyProductListAdapter(Topqtyproduct));
				
				final List<SumProductShop> ts = gt.getTopSaleProduct();
				
				
				ArrayList<String> productname = new ArrayList<String>() ;
				ArrayList<String> saleprice = new ArrayList<String>() ;
				
				for (SumProductShop tps : ts) saleprice.add(Double.toString(tps.getSalePrice()));
				for (SumProductShop tps : ts) productname.add(tps.getProductName()+" ("+tps.getSalePrice()+")");
				String[] productnameArr = new String[productname.size()];
				productnameArr = productname.toArray(productnameArr);
				
		            ArrayList<Entry> yVals1 = new ArrayList<Entry>();
		            // ArrayList<Entry> yVals2 = new ArrayList<Entry>();

		            // IMPORTANT: In a PieChart, no values (Entry) should have the same
		            // xIndex (even if from different DataSets), since no values can be
		            // drawn above each other.
		            for (int i = 0; i < productname.size(); i++) {
		            	float val = Float.parseFloat(saleprice.get(i));
		                yVals1.add(new Entry(val, i));
		            }

		            // for (int i = mSeekBarX.getProgress() / 2; i <
		            // mSeekBarX.getProgress(); i++) {
		            // yVals2.add(new Entry((float) (Math.random() * mult) + mult / 5, i));
		            // }

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
				//holder.percentValue.setText(formatter.format((st.getSalePrice())));
				//text_sum_totalBill.setText(formatter.format(Sumsale.getTotalBill()));
				//text_sum_discount.setText(formatter.format(Sumsale.getDiscount()));
				//text_sum_salePrice.setText(formatter.format(Sumsale.getSalePrice()));
			
			return convertView;
				}
	}
}
