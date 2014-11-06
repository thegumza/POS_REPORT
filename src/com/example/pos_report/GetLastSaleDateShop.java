package com.example.pos_report;

import org.ksoap2.serialization.PropertyInfo;

import com.google.gson.JsonSyntaxException;

import android.content.Context;
import android.text.TextUtils;

public class GetLastSaleDateShop extends Ksoap2WebService{
	
	public static final String GET_LAST_SALE_DATE_OF_SHOP_METHOD = "WsDashBoard_GetLastSaleDateOfShop";
	String lastSaleDate;
	public static final int TIME_OUT = 10 * 1000;
	GetLastSaleDate mlistener;
	
	public GetLastSaleDateShop(Context c,final int iShopID,final String deviceCode,GetLastSaleDate listener) {
		
		super(c, GET_LAST_SALE_DATE_OF_SHOP_METHOD, TIME_OUT);
		mProperty = new PropertyInfo();
		mProperty.setName("iShopID");
		mProperty.setValue(iShopID);
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
				mlistener.onSuccess(result.replace("\"",""));
			}
			
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public static interface GetLastSaleDate{
		void onSuccess(String result);
		void onLoad();
	}
	
	
	@Override
    protected void onPreExecute() {
		mlistener.onLoad();
        
    }

	

}


