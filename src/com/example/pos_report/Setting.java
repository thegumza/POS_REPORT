package com.example.pos_report;

import com.example.flatuilibrary.FlatButton;
import com.example.flatuilibrary.FlatEditText;
import com.example.flatuilibrary.FlatTextView;
import com.example.pos_report.database.GlobalPropertyDao;
import com.example.pos_report.database.PayTypeDao;
import com.example.pos_report.database.ProductDeptDao;
import com.example.pos_report.database.ProductGroupDao;
import com.example.pos_report.database.ProductItemDao;
import com.example.pos_report.database.PromotionDao;
import com.example.pos_report.database.ReportDatabase;
import com.example.pos_report.database.ShopPropertyDao;
import com.example.pos_report.database.StaffsDao;
import com.example.pos_report.database.model.AllProductData;
import com.example.pos_report.database.model.ShopData;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.view.View;

public class Setting extends Activity  {
	private static ProgressDialog  pdia;
	String udid,shopID,path_vdo_json,path_setting,path_ip,path_visual,status_con;
	boolean datachange;
	FlatEditText setting_visual,setting_ip;
	FlatTextView setting_udid;
	public static String URL;
	private static ReportDatabase Database;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_main);
		pdia = new ProgressDialog(this);
		   pdia.setCancelable(true);
		   pdia.setIndeterminate(true);

			Database = new ReportDatabase(this);
		final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		
	    path_ip = sharedPreferences.getString("path_ip", "");
	    path_visual = sharedPreferences.getString("path_visual", "");
	    URL = "http://"+path_ip+"/"+path_visual+"/ws_dashboard.asmx?WSDL";
	    
	    udid = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
	    setting_udid = (FlatTextView) findViewById(R.id.value_udid);	
		 setting_ip = (FlatEditText) findViewById(R.id.value_ip);
		 setting_visual = (FlatEditText) findViewById(R.id.value_visual);
		 setting_udid.setText(udid);
		 setting_ip.setText(path_ip);
		 setting_visual.setText(path_visual);
		 
		 
		 FlatButton reset =(FlatButton) findViewById(R.id.reset);
		 reset.setOnClickListener(new View.OnClickListener() {
			   @Override
			   public void onClick(View view) {
				   	
					Editor editor = sharedPreferences.edit();
			        editor.putString("path_ip", setting_ip.getText().toString());
			        editor.putString("path_visual", setting_visual.getText().toString());
			        editor.commit();
			   	 
			        //PendingIntent intent = PendingIntent.getActivity(Setting.this.getBaseContext(), 0, new Intent(getIntent()), getIntent().getFlags());
			        //AlarmManager manager = (AlarmManager) Setting.this.getSystemService(Context.ALARM_SERVICE);
			        
			        
			        onUpdate();
			        finish();
			        
			   }
			});
		
	}
	public void onUpdate() {
    	new ShopDataLoader(Setting.this, "123", new ShopDataLoader.GetShopDataLoader() {
			 
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				Gson gson = new Gson();
				try {
					ShopData sd = gson.fromJson(result, ShopData.class);
					
					// insert ShopProperty Data into Database
					ShopPropertyDao sp = new ShopPropertyDao(Setting.this);
					sp.insertShopData(sd.getShopProperty());
					
					// insert GlobalProperty Data into Database
					GlobalPropertyDao gp = new GlobalPropertyDao(Setting.this);
					gp.insertGlobalPropertyData(sd.getGlobalProperty());
					
					//insert PayType Data into Database
					PayTypeDao pt = new PayTypeDao(Setting.this);
					pt.insertPayTypeData(sd.getPayType());
					
					//insert Staffs Data into Database
					StaffsDao st = new StaffsDao(Setting.this);
					st.insertStaffsData(sd.getStaffs());
					
					pdia.dismiss();
					
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				}
				
			}
			@Override
			public void onLoad() {
				// TODO Auto-generated method stub
		        pdia.setMessage("Loading...");
		        pdia.show();
				
			}
		}).execute(URL);
		new AllProductDataLoader(Setting.this, "123",new AllProductDataLoader.GetAllProductDataLoader() {
			
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				Gson gson = new Gson();
				try {
					AllProductData ap = gson.fromJson(result, AllProductData.class);
					
					// insert promotion Data into Database
					PromotionDao pr = new PromotionDao(Setting.this);
					pr.insertPromotionData(ap.getPromotion());
					
					//insert ProductGroup Data into Database
					ProductGroupDao pg = new ProductGroupDao(Setting.this);
					pg.insertProductGroupData(ap.getProductGroup());
					
					//insert ProductItem Data into Database
					ProductItemDao pi = new ProductItemDao(Setting.this);
					pi.insertProductItemData(ap.getProductItem());
					
					//insert ProductDept Data into Database
					ProductDeptDao pd = new ProductDeptDao(Setting.this);
					pd.insertProductDeptData(ap.getProductDept());
					
					
					pdia.dismiss();
					 
					
					
					
							
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void onLoad() {
				// TODO Auto-generated method stub
		        pdia.setMessage("Loading...");
		        pdia.show();
				
			}
		}).execute(URL);
		
	}
	
}

