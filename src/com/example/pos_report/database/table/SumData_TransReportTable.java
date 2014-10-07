package com.example.pos_report.database.table;

import android.database.sqlite.SQLiteDatabase;

public class SumData_TransReportTable {
	public static final String TABLE_SUMDATA_TRANSPORTREPORT = "SumData_TransReportTable";
	public static final String COLUMN_SHOP_ID = "ShopID";
	public static final String COLUMN_SALE_DATE = "SaleDate";
	public static final String COLUMN_TOTAL_BILL = "TotalBill";
	public static final String COLUMN_TOTAL_CUSTOMER = "TotalCustomer";
	public static final String COLUMN_TRANSACTION_VAT = "TransactionVAT";
	public static final String COLUMN_TOTAL_RETAIL_PRICE = "TotalRetailPrice";
	public static final String COLUMN_TOTAL_DISCOUNT = "TotalDiscount";
	public static final String COLUMN_TOTAL_SALE_PRICE = "TotalSalePrice";
	
	private static String sqlCreate = 
			"CREATE TABLE " + TABLE_SUMDATA_TRANSPORTREPORT + "( "
			+ COLUMN_SHOP_ID + " INTEGER, "
			+ COLUMN_SALE_DATE + " TEXT, "
			+ COLUMN_TOTAL_BILL + " INTEGER, "
			+ COLUMN_TOTAL_CUSTOMER + " INTEGER, "
			+ COLUMN_TRANSACTION_VAT + " REAL, "
			+ COLUMN_TOTAL_RETAIL_PRICE + " REAL, "
			+ COLUMN_TOTAL_DISCOUNT + " REAL, "
			+ COLUMN_TOTAL_SALE_PRICE + " REAL )";
	public static void onCreate(SQLiteDatabase db){
		db.execSQL(sqlCreate);
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUMDATA_TRANSPORTREPORT);
		onCreate(db);
	}
}
