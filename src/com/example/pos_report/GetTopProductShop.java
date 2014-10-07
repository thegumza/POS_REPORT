package com.example.pos_report;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import org.ksoap2.serialization.PropertyInfo;

import android.content.Context;

import com.example.pos_peport.database.model.TopProductShop;
import com.example.pos_report.database.GetTopProductShopDao;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class GetTopProductShop extends Ksoap2WebService{
	
	
	public static final String GET_TOP_PRODUCT_OF_SHOP_FROM_MONTH_YEAR_METHOD = "WsDashBoard_GetTopMenuOfShopFromMonthYear";
	
	public static final int TIME_OUT = 10 * 1000;

	public GetTopProductShop(Context c,final int iShopID,final int iMonth,final int iYear,final String ProductGroupCode,final int iTopType,final int iTopNo,String deviceCode) {
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
	}
	
	@Override
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
	}

	@Override
	protected void onPreExecute() {
	}

	

}


