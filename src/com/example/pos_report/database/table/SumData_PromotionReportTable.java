package com.example.pos_report.database.table;

import android.database.sqlite.SQLiteDatabase;

public class SumData_PromotionReportTable {
	
	public static final String TABLE_SUMDATA_PROMOTIONREPORT = "SumData_PromotionReport";
	public static final String COLUMN_SHOP_ID = "ShopID";
	public static final String COLUMN_SALE_DATE = "SaleDate";
	public static final String COLUMN_PROMOTION_ID = "PromotionID";
	public static final String COLUMN_TOTAL_DISCOUNT = "TotalDiscount";
	public static final String COLUMN_TOTAL_LAST_PRICE = "TotalLastPrice";
	
	private static String sqlCreate = 
			"CREATE TABLE " + TABLE_SUMDATA_PROMOTIONREPORT + " ("
			+ COLUMN_SHOP_ID + " INTEGER, "
			+ COLUMN_SALE_DATE + " TEXT, "
			+ COLUMN_PROMOTION_ID + " INTEGER, "
			+ COLUMN_TOTAL_DISCOUNT + " REAL, "
			+ COLUMN_TOTAL_LAST_PRICE + " REAL ) ";
	
	public static void onCreate(SQLiteDatabase db){
		db.execSQL(sqlCreate);
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUMDATA_PROMOTIONREPORT);
		onCreate(db);
	}
}
