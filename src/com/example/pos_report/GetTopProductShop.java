package com.example.pos_report;

import org.ksoap2.serialization.PropertyInfo;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.JsonSyntaxException;

public class GetTopProductShop extends Ksoap2WebService{
	
	
	public static final String GET_TOP_PRODUCT_OF_SHOP_FROM_MONTH_YEAR_METHOD = "WsDashBoard_GetTopMenuOfShopFromMonthYear";
	
	public static final int TIME_OUT = 10 * 1000;
	GetTopProduct mlistener;
	
	public GetTopProductShop(Context c,final int iShopID,final int iMonth,final int iYear,final String ProductGroupCode,final int iTopType,final int iTopNo,String deviceCode,GetTopProduct listener) {
		super(c, GET_TOP_PRODUCT_OF_SHOP_FROM_MONTH_YEAR_METHOD, TIME_OUT);
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
		mProperty.setName("szProductGroupCode");
		mProperty.setValue(ProductGroupCode);
		mProperty.setType(String.class);
		mSoapRequest.addProperty(mProperty);
		
		mProperty = new PropertyInfo();
		mProperty.setName("iTopType");
		mProperty.setValue(iTopType);
		mProperty.setType(int.class);
		mSoapRequest.addProperty(mProperty);
		
		mProperty = new PropertyInfo();
		mProperty.setName("iTopNo");
		mProperty.setValue(iTopNo);
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
	public static interface GetTopProduct{
		void onSuccess(String result);
		void onLoad();
	}
	@Override
    protected void onPreExecute() {
		mlistener.onLoad();
    }}
	/*@Override
	protected void onPostExecute(String result) {
		Gson gson = new Gson();
		try {
			Type collectionType = new TypeToken<Collection<TopProductShop>>(){}.getType();
			@SuppressWarnings("unchecked")
			List<TopProductShop> st = (List<TopProductShop>) gson.fromJson(result, collectionType);
			
			// insert GetTopProductShopDao data into database
			GetTopProductShopDao gt = new GetTopProductShopDao(mContext);
			gt.insertTopProductShopData(st);
			
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
        
    }
	

}*/


