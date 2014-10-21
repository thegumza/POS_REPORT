package com.example.pos_report.database.table;

import android.database.sqlite.SQLiteDatabase;

public class SumData_ProductReportTable {
	
	public static final String TABLE_SUMDATA_PRODUCTREPORT = "SumData_ProductReportTable";
	public static final String COLUMN_SHOP_ID = "ShopID";
	public static final String COLUMN_SALE_DATE = "SaleDate";
	public static final String COLUMN_PRODUCT_ID = "ProductID";
	public static final String COLUMN_SALE_MODE = "SaleMode";
	public static final String COLUMN_QTY = "Qty";
	public static final String COLUMN_SALE_PRICE = "SalePrice";
	
	private static String sqlCreate = 
			"CREATE TABLE " + TABLE_SUMDATA_PRODUCTREPORT + "( "
			+ COLUMN_SHOP_ID + " INTEGER, "
			+ COLUMN_SALE_DATE + " TEXT, "
			+ COLUMN_PRODUCT_ID + " INTEGER, "
			+ COLUMN_SALE_MODE + " INTEGER, "
			+ COLUMN_QTY + " INTEGER, "
			+ COLUMN_SALE_PRICE + " REAL )";
	
	public static void onCreate(SQLiteDatabase db){
		db.execSQL(sqlCreate);
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUMDATA_PRODUCTREPORT);
		onCreate(db);
	}
}


