package com.example.pos_report.database.table;

import android.database.sqlite.SQLiteDatabase;

public class ProductDeptTable {
	public static final String TABLE_PRODUCT_DEPT = "ProductDept";
	public static final String COLUMN_PRODUCT_DEPT_ID = "ProductDeptID";
	public static final String COLUMN_PRODUCT_GROUP_ID = "ProductGroupID";
	public static final String COLUMN_PRODUCT_DEPT_CODE = "ProductDeptCode";
	public static final String COLUMN_PRODUCT_DEPT_NAME = "ProductDeptName";
	public static final String COLUMN_ORDERING = "Ordering";
	
	private static final String sqlCreate = 
			"CREATE TABLE " + TABLE_PRODUCT_DEPT + " ("
			+ COLUMN_PRODUCT_DEPT_ID + " INTEGER, "
			+ COLUMN_PRODUCT_GROUP_ID + " INTEGER, "
			+ COLUMN_PRODUCT_DEPT_CODE + " TEXT, "
			+ COLUMN_PRODUCT_DEPT_NAME + " TEXT, "
			+ COLUMN_ORDERING + " INTEGER, " 
			+ " PRIMARY KEY (" + COLUMN_PRODUCT_DEPT_ID + "));";
	public static void onCreate(SQLiteDatabase db){
		db.execSQL(sqlCreate);
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_DEPT);
		onCreate(db);
	}
}
