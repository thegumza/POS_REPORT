package com.example.pos_report;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import org.ksoap2.serialization.PropertyInfo;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.pos_peport.database.model.SumPaymentShop;
import com.example.pos_report.database.GetSumPaymentShopDao;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class GetSumPaymentShop extends Ksoap2WebService{
	
	
	public static final String GET_SUM_PAYMENT_OF_SHOP_FROM_MONTH_YEAR_METHOD = "WsDashBoard_GetSumPaymentOfShopFromMonthYear";
	
	public static final int TIME_OUT = 10 * 1000;
	private ProgressDialog pdia;
	public GetSumPaymentShop(Context c,final int iShopID,final int iMonth,final int iYear,String deviceCode) {
		super(c, GET_SUM_PAYMENT_OF_SHOP_FROM_MONTH_YEAR_METHOD, TIME_OUT);
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
			Type collectionType = new TypeToken<Collection<SumPaymentShop>>(){}.getType();
			@SuppressWarnings("unchecked")
			List<SumPaymentShop> gs = (List<SumPaymentShop>) gson.fromJson(result, collectionType);
			
			// insert GetSumTransactionShop data into database
			GetSumPaymentShopDao gp = new GetSumPaymentShopDao(mContext);
			gp.insertPaymentShopData(gs);
			
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
