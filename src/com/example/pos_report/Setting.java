package com.example.pos_report;

import com.example.flatuilibrary.FlatButton;
import com.example.flatuilibrary.FlatEditText;
import com.example.flatuilibrary.FlatTextView;
import com.example.pos_report.database.ReportDatabase;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.view.View;

public class Setting extends Activity  {
	
	String udid,shopID,path_vdo_json,path_setting,path_ip,path_visual,status_con;
	FlatEditText setting_visual,setting_ip;
	FlatTextView setting_udid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_main);
		
		final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

	    path_ip = sharedPreferences.getString("path_ip", "");
	    path_visual = sharedPreferences.getString("path_visual", "");
	    
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
					//SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
					Editor editor = sharedPreferences.edit();
			        editor.putString("path_ip", setting_ip.getText().toString());
			        editor.putString("path_visual", setting_visual.getText().toString());
			        editor.commit();
			        ReportDatabase Database = new ReportDatabase(Setting.this);
			        Database.dropAllTable();
			   	 
			        SharedPreferences prefs = getSharedPreferences("com.example.pos_report", MODE_PRIVATE);
			        prefs.edit().putBoolean("LoadShopData", true).commit();
				    prefs.edit().putBoolean("LoadAllProductData", true).commit();
				        
				    
				     
			        PendingIntent intent = PendingIntent.getActivity(Setting.this.getBaseContext(), 0, new Intent(getIntent()), getIntent().getFlags());
			        AlarmManager manager = (AlarmManager) Setting.this.getSystemService(Context.ALARM_SERVICE);
			        
			        System.exit(2);
			        
			   }
			});
		
	}
}

