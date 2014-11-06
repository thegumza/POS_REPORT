package com.example.pos_report;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import com.cengalabs.flatui.views.FlatButton;
import com.cengalabs.flatui.views.FlatEditText;
import com.cengalabs.flatui.views.FlatTextView;
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
import com.example.pos_report.database.model.Staffs;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.view.View;
import android.widget.Toast;


public class POS_Login extends Activity {
	private static ProgressDialog  pdia;
	FlatButton btnLogin;
	public static String URL;
	private static ReportDatabase Database;
	private AlertDialog ald;
	private FlatEditText edtuserName,edtpassWord;
	private static String userName;
	private String passWord; 
	String enPass,udid;
	FlatButton btnUpdate,btnSetting;
	FlatTextView setting_udid;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		getActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_form);
       //final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
      
       Database = new ReportDatabase(this);
  	   pdia = new ProgressDialog(this);
	   pdia.setCancelable(true);
	   pdia.setIndeterminate(true);
	    
	   	udid = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
        btnLogin = (FlatButton)findViewById(R.id.btnLogin);
        btnUpdate = (FlatButton)findViewById(R.id.btnUpdate);
        btnSetting = (FlatButton)findViewById(R.id.btnSetting);
        edtuserName = (FlatEditText)findViewById(R.id.edtUserName);
        edtpassWord = (FlatEditText)findViewById(R.id.edtPassword);
        setting_udid = (FlatTextView) findViewById(R.id.value_udid);
        setting_udid.setText(udid);
        
        btnLogin.setOnClickListener(new View.OnClickListener() {
		    @Override
			public void onClick(View v) {
		    	userName = edtuserName.getText().toString();
		    	passWord = edtpassWord.getText().toString();
		    	try {
					enPass = SHA1(passWord).toUpperCase();
				} catch (NoSuchAlgorithmException
						| UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
		    	final StaffsDao stu = new StaffsDao(POS_Login.this);
				List<Staffs> st = stu.getStaffs();
				ArrayList<String> username = new ArrayList<String>();
				ArrayList<String> password = new ArrayList<String>();
				for (Staffs tps : st) username.add((tps.getStaffCode()));
				for (Staffs tps : st) password.add((tps.getStaffPassword()));
				boolean checkUsername = username.contains(userName);
				boolean checkPassword = password.contains(enPass);
				
				if(checkUsername == true && checkPassword == true){
					Intent intentMain = new Intent(POS_Login.this , 
							 MainActivity.class);
			    		startActivity(intentMain);
				}
				else{
					Toast.makeText(POS_Login.this, "Username or Password not correct please try again", Toast.LENGTH_LONG).show();
				}
				
		    }
		});
        btnUpdate.setOnClickListener(new View.OnClickListener() {
		    @Override
			public void onClick(View v) {
		    	onUpdate();
				
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

	public void onUpdate() {
		final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
	  	   String path_ip = sharedPreferences.getString("path_ip", "");
	  	   String path_visual = sharedPreferences.getString("path_visual", "");
	  	   URL = "http://"+path_ip+"/"+path_visual+"/ws_dashboard.asmx?WSDL";
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

	private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

	public static String getUserName() {
		return userName;
	}
 
}
