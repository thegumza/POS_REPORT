package com.example.pos_report.detail;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
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
import com.example.pos_report.database.GetSumPaymentShopDao;
import com.example.pos_report.database.GlobalPropertyDao;
import com.example.pos_report.database.model.GlobalProperty;
import com.example.pos_report.database.model.SumPaymentShop;

public class SaleByDate_Detail_Paytype extends Activity{
	
	final GlobalPropertyDao gpd = new GlobalPropertyDao(this);
	GlobalProperty format = gpd.getGlobalProperty();
	String formatnumber = format.getCurrencyFormat();
	String formatqty = format.getQtyFormat();
	NumberFormat formatter = new DecimalFormat(formatnumber);
	NumberFormat qtyformatter = new DecimalFormat(formatqty);
	
	String shopName = SaleByDate.getShopName();
	String payTypeName = SaleByDate.getPayTypeName();
	ListView listPaytype;
	FlatTextView  payTypeValue,ShopNameValue,text_sum_paytype_amount,text_sum_paytype_percent;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.salebydate_detail_paytype);
        
        ShopNameValue = (FlatTextView) findViewById(R.id.shopNameValue);
        payTypeValue = (FlatTextView) findViewById(R.id.payTypeValue);
        listPaytype = (ListView)findViewById(R.id.listPaytype);
        text_sum_paytype_amount = (FlatTextView) findViewById(R.id.text_sum_paytype_amount);
        text_sum_paytype_percent = (FlatTextView) findViewById(R.id.text_sum_paytype_percent);
        
        listPaytype.setOnTouchListener(new ListView.OnTouchListener() {
			   
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				   v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});
        
        ShopNameValue.setText(""+shopName);
        payTypeValue.setText("PayType ("+payTypeName+")");
        
        GetSumPaymentShopDao gp = new GetSumPaymentShopDao(this);
        List<SumPaymentShop> Paymentlist = gp.getSaleDatePaymentDetail();
        listPaytype.setAdapter(new PaytypelistDetailAdapter(Paymentlist));
        
        }
		public class PaytypelistDetailAdapter extends BaseAdapter{
		
		List<SumPaymentShop> Paymentlist;
		
		public PaytypelistDetailAdapter(List<SumPaymentShop> pl) {
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
			FlatTextView saleDatePaytypeValue;
			FlatTextView amountPaytypeValue;
			FlatTextView percentPaytypeValue;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder  holder;
			LayoutInflater inflater = getLayoutInflater();
			
				if(convertView == null){
					convertView = inflater.inflate(R.layout.salebydate_paytype_column, parent,false);
				holder = new ViewHolder();
				holder.saleDatePaytypeValue=(FlatTextView)convertView.findViewById(R.id.saleDateValue);
				holder.amountPaytypeValue=(FlatTextView)convertView.findViewById(R.id.amountPaytypeValue);
				holder.percentPaytypeValue=(FlatTextView)convertView.findViewById(R.id.percentPaytypeValue);
				
				convertView.setTag(holder);
				}else{
					holder=(ViewHolder)convertView.getTag();
				}
				final GetSumPaymentShopDao gp = new GetSumPaymentShopDao(SaleByDate_Detail_Paytype.this);
				SumPaymentShop gsp = gp.getSumPaytype();
				
				SumPaymentShop sp = Paymentlist.get(position);
				holder.saleDatePaytypeValue.setText(sp.getSaleDate());
				holder.amountPaytypeValue.setText(formatter.format(sp.getTotalPay()));
				
				double totalpay = gsp.getTotalPay();
				double percent = (sp.getTotalPay()* 100) / totalpay;
				holder.percentPaytypeValue.setText(formatter.format(percent));
				text_sum_paytype_amount.setText(formatter.format(gsp.getTotalPay()));
				text_sum_paytype_percent.setText("100%");
				
			return convertView;
				}}
}
