package com.example.pos_report;


import org.ksoap2.serialization.PropertyInfo;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.JsonSyntaxException;

public class GetSumTransactionShop extends Ksoap2WebService {
	
	
	public static final String GET_SUM_TRANSACTION_OF_SHOP_FROM_MONTH_YEAR_METHOD = "WsDashBoard_GetSumTransactionOfShopFromMonthYear";
	
	public static final int TIME_OUT = 10 * 1000;
	GetSumTransacTion mlistener;
	
	public GetSumTransactionShop(Context c,final int iShopID,final int iMonth,final int iYear,String deviceCode,GetSumTransacTion listener) {
		super(c, GET_SUM_TRANSACTION_OF_SHOP_FROM_MONTH_YEAR_METHOD, TIME_OUT);
		mProperty = new PropertyInfo();
		mProperty.setName("iShopID");
		mProperty.setValue(iShopID);
		mProperty.setType(int.class);
		mSoapRequest.addProperty(mProperty);
		
		mProperty = new PropertyInfo();
		mProperty.setName("iMonth");
		mProperty.setValue(iMonth);
		mProperty.setType(int.class);
		mSoapRequest.addProperty(mProperty);
		
		mProperty = new PropertyInfo();
		mProperty.setName("iYear");
		mProperty.setValue(iYear);
		mProperty.setType(int.class);
		mSoapRequest.addProperty(mProperty);
		
		mProperty = new PropertyInfo();
		mProperty.setName("szDeviceCode");
		mProperty.setValue(deviceCode);
		mProperty.setType(String.class);
		mSoapRequest.addProperty(mProperty);
		
		mlistener = listener;
	}
	@Override
	protected void onPostExecute(String result) {
		try {
			if(!TextUtils.isEmpty(result))
			{
				mlistener.onSuccess(result);
			}
			
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		
	}
	public static interface GetSumTransacTion{
		void onSuccess(String result);
		void onLoad();
	}
	@Override
    protected void onPreExecute() {
		mlistener.onLoad();
    }
	

}

