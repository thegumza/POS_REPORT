package com.example.pos_report.database.table;

import android.database.sqlite.SQLiteDatabase;

public class SumData_PaymentReportTable {
	
	public static final String TABLE_SUMDATA_PAYMENT_REPORT = "SumData_PaymentReport";
	public static final String COLUMN_SHOP_ID = "ShopID";
	public static final String COLUMN_SALE_DATE = "SaleDate";
	public static final String COLUMN_PAYTYPE_ID = "PayTypeID";
	public static final String COLUMN_TOTAL_PAY = "TotalPay";
	
	private static String sqlCreate = 
			"CREATE TABLE " + TABLE_SUMDATA_PAYMENT_REPORT + " ("
			+ COLUMN_SHOP_ID + " INTEGER, "
			+ COLUMN_SALE_DATE + " TEXT, "
			+ COLUMN_PAYTYPE_ID + " TEXT, "
			+ COLUMN_TOTAL_PAY + " REAL ) ";
	
	public static void onCreate(SQLiteDatabase db){
		db.execSQL(sqlCreate);
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUMDATA_PAYMENT_REPORT);
		onCreate(db);
	}
}
