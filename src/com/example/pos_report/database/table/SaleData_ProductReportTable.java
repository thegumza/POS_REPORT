package com.example.pos_report.database.table;

import android.database.sqlite.SQLiteDatabase;

public class SaleData_ProductReportTable {
	
	public static final String TABLE_SALE_DATA_PRODUCTREPORT = "SaleData_ProductReportTable";
	public static final String COLUMN_PRODUCT_GROUP_CODE = "ProductGroupCode";
	public static final String COLUMN_PRODUCT_GROUP_NAME = "ProductGroupName";
	public static final String COLUMN_PRODUCT_DEPT_NAME = "ProductDeptName";
	public static final String COLUMN_PRODUCT_NAME = "ProductName";
	public static final String COLUMN_SUM_AMOUNT = "SumAmount";
	public static final String COLUMN_SUM_SALE_PRICE = "SumSalePrice";
	
	private static String sqlCreate = 
			"CREATE TABLE " + TABLE_SALE_DATA_PRODUCTREPORT + "( "
			+ COLUMN_PRODUCT_GROUP_CODE + " TEXT, "
			+ COLUMN_PRODUCT_GROUP_NAME + " TEXT, "
			+ COLUMN_PRODUCT_DEPT_NAME + " TEXT, "
			+ COLUMN_PRODUCT_NAME + " TEXT, "
			+ COLUMN_SUM_AMOUNT + " INTEGER, "
			+ COLUMN_SUM_SALE_PRICE + " REAL )";
	
	public static void onCreate(SQLiteDatabase db){
		db.execSQL(sqlCreate);
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SALE_DATA_PRODUCTREPORT);
		onCreate(db);
	}
}


