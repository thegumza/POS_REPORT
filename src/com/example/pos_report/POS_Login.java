package com.example.pos_report;

import java.util.List;

import com.example.flatuilibrary.FlatButton;
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
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class POS_Login extends Activity{
	private ProgressDialog  pdia;
	FlatButton btnLogin,btnUpdate,btnSetting;
	public static String URL;
	private ReportDatabase Database;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();
        setContentView(R.layout.login_form);
        
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
  	  	//Database = new ReportDatabase(POS_Login.this);
  	   String path_ip = sharedPreferences.getString("path_ip", "");
  	   String path_visual = sharedPreferences.getString("path_visual", "");
  	   URL = "http://"+path_ip+"/"+path_visual+"/ws_dashboard.asmx?WSDL";
  	   pdia = new ProgressDialog(this);
	   pdia.setCancelable(true);
	   pdia.setIndeterminate(true);
	   
        btnLogin = (FlatButton)findViewById(R.id.btnLogin);
        btnUpdate = (FlatButton)findViewById(R.id.btnUpdate);
        btnSetting = (FlatButton)findViewById(R.id.btnSetting);
        
        btnLogin.setOnClickListener(new View.OnClickListener() {
		    @Override
			public void onClick(View v) {
		    	
		    	Intent intentMain = new Intent(POS_Login.this , 
						 MainActivity.class);
		    		startActivity(intentMain);
		    }
		});
        btnUpdate.setOnClickListener(new View.OnClickListener() {
		    @Override
			public void onClick(View v) {
		    	Database = new ReportDatabase(getBaseContext());
		    	new ShopDataLoader(POS_Login.this, "123", new ShopDataLoader.GetShopDataLoader() {
					 
					@Override
					public void onSuccess(String result) {
						// TODO Auto-generated method stub
						Gson gson = new Gson();
						try {
							ShopData sd = gson.fromJson(result, ShopData.class);
							
							// insert ShopProperty Data into Database
							ShopPropertyDao sp = new ShopPropertyDao(POS_Login.this);
							sp.insertShopData(sd.getShopProperty());
							
							// insert GlobalProperty Data into Database
							GlobalPropertyDao gp = new GlobalPropertyDao(POS_Login.this);
							gp.insertGlobalPropertyData(sd.getGlobalProperty());
							
							//insert PayType Data into Database
							PayTypeDao pt = new PayTypeDao(POS_Login.this);
							pt.insertPayTypeData(sd.getPayType());
							
							//insert Staffs Data into Database
							StaffsDao st = new StaffsDao(POS_Login.this);
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
				new AllProductDataLoader(POS_Login.this, "123",new AllProductDataLoader.GetAllProductDataLoader() {
					
					@Override
					public void onSuccess(String result) {
						// TODO Auto-generated method stub
						Gson gson = new Gson();
						try {
							AllProductData ap = gson.fromJson(result, AllProductData.class);
							
							// insert promotion Data into Database
							PromotionDao pr = new PromotionDao(POS_Login.this);
							pr.insertPromotionData(ap.getPromotion());
							
							//insert ProductGroup Data into Database
							ProductGroupDao pg = new ProductGroupDao(POS_Login.this);
							pg.insertProductGroupData(ap.getProductGroup());
							
							//insert ProductItem Data into Database
							ProductItemDao pi = new ProductItemDao(POS_Login.this);
							pi.insertProductItemData(ap.getProductItem());
							
							//insert ProductDept Data into Database
							ProductDeptDao pd = new ProductDeptDao(POS_Login.this);
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
		});
        btnSetting.setOnClickListener(new View.OnClickListener() {
		    @Override
			public void onClick(View v) {
		    	
		    	Intent intentMain = new Intent(POS_Login.this ,Setting.class);
		    	startActivity(intentMain);
		    }
		});
        
}
	
}
