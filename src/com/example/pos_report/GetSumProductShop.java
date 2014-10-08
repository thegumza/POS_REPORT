package com.example.pos_report;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import org.ksoap2.serialization.PropertyInfo;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.pos_peport.database.model.SumProductShop;
import com.example.pos_report.database.GetSumProductShopDao;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class GetSumProductShop extends Ksoap2WebService{
	
	
	public static final String GET_SUM_PRODUCT_OF_SHOP_FROM_MONTH_YEAR_METHOD = "WsDashBoard_GetSumProductOfShopFromMonthYear";
	
	public static final int TIME_OUT = 10 * 1000;
	private ProgressDialog pdia;
	public GetSumProductShop(Context c,final int iShopID,final int iMonth,final int iYear,String deviceCode) {
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
	}
	
	@Override
	protected void onPostExecute(String result) {
		Gson gson = new Gson();
		try {
			Type collectionType = new TypeToken<Collection<SumProductShop>>(){}.getType();
			List<SumProductShop> sp = (List<SumProductShop>) gson.fromJson(result, collectionType);
			
			// insert GetSumProductShop data into database
			GetSumProductShopDao gp = new GetSumProductShopDao(mContext);
			gp.insertSumProductShopData(sp);
			gp.getTempProduct();
			
			
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

	

}


