package com.example.pos_report;

import com.example.flatuilibrary.FlatButton;
import com.example.pos_report.graph.SalebyDate_Graph;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class POS_Login extends Activity{
	
	FlatButton btnLogin;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.login_form);
        
        btnLogin = (FlatButton)findViewById(R.id.btnLogin);
		
        btnLogin.setOnClickListener(new View.OnClickListener() {
		    @Override
			public void onClick(View v) {
		    	Intent intentMain = new Intent(POS_Login.this , 
						 MainActivity.class);
		    	
		    	startActivity(intentMain);
		    }
		});
}
	
}
