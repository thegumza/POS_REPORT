package com.example.pos_report;


import org.ksoap2.serialization.PropertyInfo;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.JsonSyntaxException;

public class GetSumProductShop extends Ksoap2WebService{
	
	
	public static final String GET_SUM_PRODUCT_OF_SHOP_FROM_MONTH_YEAR_METHOD = "WsDashBoard_GetSumProductOfShopFromMonthYear";
	
	public static final int TIME_OUT = 10 * 1000;
	GetSumProduct mlistener;
	
	public GetSumProductShop(Context c,final int iShopID,final int iMonth,final int iYear,String deviceCode,GetSumProduct listener) {
		super(c, GET_SUM_PRODUCT_OF_SHOP_FROM_MONTH_YEAR_METHOD, TIME_OUT);
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
	public static interface GetSumProduct{
		void onSuccess(String result);
		void onLoad();
	}
	@Override
    protected void onPreExecute() {
		mlistener.onLoad();
    }
	/*@Override
	protected void onPostExecute(String result) {
		Gson gson = new Gson();
		try {
			Type collectionType = new TypeToken<Collection<SumProductShop>>(){}.getType();
			List<SumProductShop> sp = (List<SumProductShop>) gson.fromJson(result, collectionType);
			
			// insert GetSumProductShop data into database
			GetSumProductShopDao gp = new GetSumProductShopDao(mContext);
			gp.insertSumProductShopData(sp);
			
			
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		pdia.dismiss();
	}
	@Override
    protected void onPreExecute() {
        pdia = new ProgressDialog(mContext);
        pdia.setMessage("Loading...");
        pdia.show();
		super.onPreExecute();
        
    }*/

	

}


