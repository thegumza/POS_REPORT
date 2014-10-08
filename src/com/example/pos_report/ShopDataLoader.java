package com.example.pos_report;

import org.ksoap2.serialization.PropertyInfo;

import android.app.ProgressDialog;
import android.content.Context;
import com.example.pos_peport.database.model.ShopData;
import com.example.pos_report.database.GlobalPropertyDao;
import com.example.pos_report.database.PayTypeDao;
import com.example.pos_report.database.ShopPropertyDao;
import com.example.pos_report.database.StaffsDao;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class ShopDataLoader extends Ksoap2WebService{
	
	
	public static final String LOAD_SHOP_DATA_METHOD = "WsDashBoard_LoadShopData";
	private ProgressDialog pdia;
	public static final int TIME_OUT = 10 * 1000;
	public ShopDataLoader(Context c, String deviceCode) {
		super(c, LOAD_SHOP_DATA_METHOD, TIME_OUT);
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
			ShopData sd = gson.fromJson(result, ShopData.class);
			
			// insert ShopProperty Data into Database
			ShopPropertyDao sp = new ShopPropertyDao(mContext);
			sp.insertShopData(sd.getShopProperty());
			
			// insert GlobalProperty Data into Database
			GlobalPropertyDao gp = new GlobalPropertyDao(mContext);
			gp.insertGlobalPropertyData(sd.getGlobalProperty());
			
			//insert PayType Data into Database
			PayTypeDao pt = new PayTypeDao(mContext);
			pt.insertPayTypeData(sd.getPayType());
			
			//insert Staffs Data into Database
			StaffsDao st = new StaffsDao(mContext);
			st.insertStaffsData(sd.getStaffs());
			
			
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
