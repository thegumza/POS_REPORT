package com.example.pos_report;

import org.ksoap2.serialization.PropertyInfo;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.JsonSyntaxException;

public class AllProductDataLoader extends Ksoap2WebService{
	
	
	public static final String LOAD_SHOP_DATA_METHOD = "WsDashBoard_LoadAllProductData";
	public static final int TIME_OUT = 10 * 1000;
	GetAllProductDataLoader mlistener;
	
	public AllProductDataLoader(Context c, String deviceCode,GetAllProductDataLoader listener) {
		super(c, LOAD_SHOP_DATA_METHOD, TIME_OUT);
		mProperty = new PropertyInfo();
		mProperty.setName("szDeviceCode");
		mProperty.setValue(deviceCode);
		mProperty.setType(String.class);
		mSoapRequest.addProperty(mProperty);
		
		mlistener = listener;
	}
	@Override
	protected void onPostExecute(String result) {
		try {
			if(!TextUtils.isEmpty(result))
			{
				mlistener.onSuccess(result);
			}
			
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public static interface GetAllProductDataLoader{
		void onSuccess(String result);
		void onLoad();
	}
	
	
	@Override
    protected void onPreExecute() {
		mlistener.onLoad();
        
    }}
	/*@Override
	protected void onPostExecute(String result) {
		Gson gson = new Gson();
		try {
			AllProductData ap = gson.fromJson(result, AllProductData.class);
			
			// insert promotion Data into Database
			PromotionDao pr = new PromotionDao(mContext);
			pr.insertPromotionData(ap.getPromotion());
			
			//insert ProductGroup Data into Database
			ProductGroupDao pg = new ProductGroupDao(mContext);
			pg.insertProductGroupData(ap.getProductGroup());
			
			//insert ProductItem Data into Database
			ProductItemDao pi = new ProductItemDao(mContext);
			pi.insertProductItemData(ap.getProductItem());
			
			//insert ProductDept Data into Database
			ProductDeptDao pd = new ProductDeptDao(mContext);
			pd.insertProductDeptData(ap.getProductDept());
			
			
			//insert SaleMode Data into Database
			//SaleModeDao sm = new SaleModeDao(mContext);
			//sm.insertSaleModeData(ap.getSaleMode());
			
			
					
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		
	}

	@Override
    protected void onPreExecute() {
        
    }
	}
*/