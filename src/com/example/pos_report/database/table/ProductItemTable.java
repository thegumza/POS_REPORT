package com.example.pos_report.database.table;

import android.database.sqlite.SQLiteDatabase;

public class ProductItemTable {
	
	public static final String TABLE_PRODUCT_ITEM = "ProductItem";
	public static final String COLUMN_PRODUCT_ID = "ProductID";
	public static final String COLUMN_PRODUCT_DEPT_ID = "ProductDeptID";
	public static final String COLUMN_PRODUCT_CODE = "ProductCode";
	public static final String COLUMN_PRODUCT_BARCODE = "ProductBarCode";
	public static final String COLUMN_PRODUCT_NAME = "ProductName";
	public static final String COLUMN_PRODUCT_TYPE_ID = "ProductTypeID";
	public static final String COLUMN_PRODUCT_UNIT_NAME = "ProductUnitName";

	private static String sqlCreate =
			"CREATE TABLE " + TABLE_PRODUCT_ITEM +" ( "
			+ COLUMN_PRODUCT_ID + " INTEGER, "
			+ COLUMN_PRODUCT_DEPT_ID + " INTEGER, "
			+ COLUMN_PRODUCT_CODE + " TEXT, "
			+ COLUMN_PRODUCT_BARCODE + " TEXT, "
			+ COLUMN_PRODUCT_NAME + " TEXT, "
			+ COLUMN_PRODUCT_TYPE_ID + " INTEGER, "
			+ COLUMN_PRODUCT_UNIT_NAME + " TEXT );";
	
	public static void onCreate(SQLiteDatabase db){
		db.execSQL(sqlCreate);
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_ITEM);
		onCreate(db);
	}
}
