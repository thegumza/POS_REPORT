package com.example.pos_report.database.table;

import android.database.sqlite.SQLiteDatabase;

public class SumData_TopProductReportTable {
	
public static final String TABLE_SUMDATA_TOP_PRODUCT_REPORT = "SumData_TopProductReportTable";
public static final String COLUMN_PRODUCT_GROUP_NAME = "ProductGroupName";
public static final String COLUMN_RODUCT_DEPT_NAME = "ProductDeptName";
public static final String COLUMN_PRODUCT_NAME = "ProductName";
public static final String COLUMN_SUM_AMOUNT = "SumAmount";
public static final String COLUMN_SUM_SALE_PRICE = "SumSalePrice";

private static String sqlCreate = 
		"CREATE TABLE " + TABLE_SUMDATA_TOP_PRODUCT_REPORT + "( "
		+ COLUMN_PRODUCT_GROUP_NAME + " TEXT, "
		+ COLUMN_RODUCT_DEPT_NAME + " TEXT, "
		+ COLUMN_PRODUCT_NAME + " TEXT, "
		+ COLUMN_SUM_AMOUNT + " INTEGER, "
		+ COLUMN_SUM_SALE_PRICE + " REAL )";

public static void onCreate(SQLiteDatabase db){
	db.execSQL(sqlCreate);
}

public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
	db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUMDATA_TOP_PRODUCT_REPORT);
	onCreate(db);
}
}



