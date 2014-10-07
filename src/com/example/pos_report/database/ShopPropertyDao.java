package com.example.pos_report.database;

import java.util.ArrayList;
import java.util.List;

import com.example.pos_peport.database.model.ShopProperty;
import com.example.pos_report.database.table.ShopPropertyTable;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * DAO stand for Data Access Object
 * @author j1tth4
 *
 */
public class ShopPropertyDao extends ReportDatabase{

	public ShopPropertyDao(Context context) {
		super(context);
	}
	
	public void insertShopData(List<ShopProperty> sl){
		// begin transaction
		getWritableDatabase().beginTransaction();
		try{
			// delete all old data
			getWritableDatabase().delete(ShopPropertyTable.TABLE_SHOP, null, null);
			for(ShopProperty s : sl){
				ContentValues cv = new ContentValues();
				cv.put(ShopPropertyTable.COLUMN_SHOP_ID, s.getShopID());
				cv.put(ShopPropertyTable.COLUMN_SHOP_CODE, s.getShopCode());
				cv.put(ShopPropertyTable.COLUMN_SHOP_NAME, s.getShopName());
				cv.put(ShopPropertyTable.COLUMN_SHOP_TYPE, s.getShopType());
				cv.put(ShopPropertyTable.COLUMN_SHOP_GROUP_ID, s.getShopGroupID());
				// insert
				getWritableDatabase().insert(ShopPropertyTable.TABLE_SHOP, null, cv);
			}
			// if not have error do commit transaction
			getWritableDatabase().setTransactionSuccessful();
		}finally{
			getWritableDatabase().endTransaction();
		}
	}
	//Insert Data to List From SQLite Database
	public List<ShopProperty> getShopList(){
		List<ShopProperty> shopproperty = new ArrayList <ShopProperty>();
		String SPSql = "Select * From "+ ShopPropertyTable.TABLE_SHOP;
		Cursor cursor =  getReadableDatabase().rawQuery(SPSql, null);
		if (cursor.moveToFirst()){
			do{
				ShopProperty sp = new ShopProperty();
				sp.setShopID(cursor.getInt(cursor.getColumnIndex(ShopPropertyTable.COLUMN_SHOP_ID)));
				sp.setShopCode(cursor.getString(cursor.getColumnIndex(ShopPropertyTable.COLUMN_SHOP_CODE)));
				sp.setShopName(cursor.getString(cursor.getColumnIndex(ShopPropertyTable.COLUMN_SHOP_NAME)));
				shopproperty.add(sp);
			}while(cursor.moveToNext());
		}
		return shopproperty;
	}
	
}
