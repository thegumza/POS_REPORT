package com.example.pos_report.database.table;

import android.database.sqlite.SQLiteDatabase;

public class TempReportByProductTable {
	
	public static final String TABLE_TEMP_REPORT_PRODUCT = "TempReportByProduct";
	public static final String COLUMN_PRODUCT_GROUP_ID = "ProductGroupID";
	public static final String COLUMN_PRODUCT_GROUP_NAME = "ProductGroupName";
	public static final String COLUMN_PRODUCT_DEPT_ID= "ProductDeptID";
	public static final String COLUMN_PRODUCT_DEPT_NAME = "ProductDeptName";
	public static final String COLUMN_PRODUCT_ID = "ProductID";
	public static final String COLUMN_PRODUCT_TYPE_ID = "ProductTypeID";
	public static final String COLUMN_PRODUCT_NAME = "ProductName";
	public static final String COLUMN_TOTAL_QTY = "TotalQty";
	public static final String COLUMN_TOTAL_SALE_PRICE = "TotalSalePrice";
	
	private static String sqlCreate = 
			"CREATE TABLE " + TABLE_TEMP_REPORT_PRODUCT + " ( "
			+ COLUMN_PRODUCT_GROUP_ID + " INTEGER, "
			+ COLUMN_PRODUCT_GROUP_NAME + " TEXT, "
			+ COLUMN_PRODUCT_DEPT_ID + " INTEGER, "
			+ COLUMN_PRODUCT_DEPT_NAME + " TEXT, "
			+ COLUMN_PRODUCT_ID + " INTEGER, "
			+ COLUMN_PRODUCT_TYPE_ID + " INTEGER, "
			+ COLUMN_PRODUCT_NAME + " TEXT, "
			+ COLUMN_TOTAL_QTY + " INTEGER, "
			+ COLUMN_TOTAL_SALE_PRICE + " REAL );";
	
	public static void onCreate(SQLiteDatabase db){
		db.execSQL(sqlCreate);
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEMP_REPORT_PRODUCT);
		onCreate(db);
	}
}
