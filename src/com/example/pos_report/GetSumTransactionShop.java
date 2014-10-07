package com.example.pos_report;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import org.ksoap2.serialization.PropertyInfo;

import android.content.Context;
import com.example.pos_peport.database.model.SumTransactionShop;
import com.example.pos_report.database.GetSumTransactionShopDao;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class GetSumTransactionShop extends Ksoap2WebService {
	
	
	public static final String GET_SUM_TRANSACTION_OF_SHOP_FROM_MONTH_YEAR_METHOD = "WsDashBoard_GetSumTransactionOfShopFromMonthYear";
	
	public static final int TIME_OUT = 10 * 1000;
	
	public GetSumTransactionShop(Context c,final int iShopID,final int iMonth,final int iYear,String deviceCode) {
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
		
	}
	
	@Override
	protected void onPostExecute(String result) {
		
		Gson gson = new Gson();
		try {
			Type collectionType = new TypeToken<Collection<SumTransactionShop>>(){}.getType();
			
			List<SumTransactionShop> gs = (List<SumTransactionShop>) gson.fromJson(result, collectionType);
			
			// insert GetSumTransactionShop data into database
			GetSumTransactionShopDao gt = new GetSumTransactionShopDao(mContext);
			gt.insertSumTransactionShopData(gs);
			
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onPreExecute() {
	}

	

}

