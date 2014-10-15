package com.example.pos_report;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import com.example.flatuilibrary.FlatTextView;
import com.example.pos_peport.database.model.GlobalProperty;
import com.example.pos_peport.database.model.SumPromotionShop;
import com.example.pos_report.database.GetSumPromotionShopDao;
import com.example.pos_report.database.GlobalPropertyDao;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class SaleByDate_Detail_PromotionType extends Activity{
	
	final GlobalPropertyDao gpd = new GlobalPropertyDao(this);
	GlobalProperty format = gpd.getGlobalProperty();
	String formatnumber = format.getCurrencyFormat();
	String formatqty = format.getQtyFormat();
	NumberFormat formatter = new DecimalFormat(formatnumber);
	NumberFormat qtyformatter = new DecimalFormat(formatqty);
	
	String shopName = SaleByDate.getShopName();
	String promotionName = SaleByDate.getPromotionName();
	ListView listPromotionType;
	FlatTextView  promotionTypeValue,ShopNameValue,text_sum_promotiontype_amount,text_sum_promotiontype_percent;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.salebydate_detail_promotiontype);
        
        
        ShopNameValue = (FlatTextView) findViewById(R.id.shopNameValue);
        promotionTypeValue = (FlatTextView) findViewById(R.id.promotionTypeValue);
        listPromotionType = (ListView)findViewById(R.id.listPromotionType);
        text_sum_promotiontype_amount = (FlatTextView) findViewById(R.id.text_sum_promotiontype_amount);
        text_sum_promotiontype_percent = (FlatTextView) findViewById(R.id.text_sum_promotiontype_percent);
        
        listPromotionType.setOnTouchListener(new ListView.OnTouchListener() {
			   
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				   v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});
        
        ShopNameValue.setText(""+shopName);
        promotionTypeValue.setText("Promotion ("+promotionName+")");
        
        GetSumPromotionShopDao gp = new GetSumPromotionShopDao(this);
        List<SumPromotionShop> Promotionlist = gp.getSaleDatePromotionDetail();
        listPromotionType.setAdapter(new PromotiontypelistDetailAdapter (Promotionlist));
        
        }
	public class PromotiontypelistDetailAdapter extends BaseAdapter{
		
		List<SumPromotionShop> Promotiontlist;
		
		public PromotiontypelistDetailAdapter(List<SumPromotionShop> prl) {
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
			FlatTextView saleDatePromotiontypeValue;
			FlatTextView amountPromotionValue;
			FlatTextView percentPromotionValue;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder  holder;
			LayoutInflater inflater = getLayoutInflater();
			
				if(convertView == null){
					convertView = inflater.inflate(R.layout.salebydate_promotiontype_column, parent,false);
				holder = new ViewHolder();
				
				holder.saleDatePromotiontypeValue=(FlatTextView)convertView.findViewById(R.id.saleDateValue);
				holder.amountPromotionValue=(FlatTextView)convertView.findViewById(R.id.amountPromotionTypeValue);
				holder.percentPromotionValue=(FlatTextView)convertView.findViewById(R.id.percentPromotionTypeValue);
				convertView.setTag(holder);
				}else{
					holder=(ViewHolder)convertView.getTag();
				}
				//Number Format
				final GlobalPropertyDao gpd = new GlobalPropertyDao(getApplicationContext());
				GlobalProperty format = gpd.getGlobalProperty();
				String currencyformat = format.getCurrencyFormat();
				NumberFormat currencyformatter = new DecimalFormat(currencyformat);
				
				final GetSumPromotionShopDao gp = new GetSumPromotionShopDao(SaleByDate_Detail_PromotionType.this);
				final SumPromotionShop gpr = gp.getSumPromotionType();
				
				SumPromotionShop spr = Promotiontlist.get(position);
				holder.saleDatePromotiontypeValue.setText(spr.getSaleDate());
				holder.amountPromotionValue.setText(currencyformatter.format(spr.getDiscount()));
				
				double totaldis = gpr.getDiscount();
				double percent = (spr.getDiscount()* 100) / totaldis;
				holder.percentPromotionValue.setText(currencyformatter.format(percent));
				text_sum_promotiontype_amount.setText(currencyformatter.format(gpr.getDiscount()));
				text_sum_promotiontype_percent.setText("100%");
			return convertView;
				}
 
		}
}
