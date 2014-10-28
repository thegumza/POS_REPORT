package com.example.pos_report.database.table;

import android.database.sqlite.SQLiteDatabase;

public class ProductGroupTable {
	public static final String TABLE_PRODUCT_GROUP = "ProductGroup";
	public static final String COLUMN_PRODUCT_GROUP_ID = "ProductGroupID";
	public static final String COLUMN_PRODUCT_GROUP_CODE = "ProductGroupCode";
	public static final String COLUMN_PRODUCT_GROUP_NAME = "ProductGroupName";
	public static final String COLUMN_PRODUCT_GROUP_TYPE = "ProductGroupType";
	public static final String COLUMN_ORDERING = "Ordering";
	public static final String COLUMN_ISCOMMENT = "IsComment";
	public static final String COLUMN_SHOP_ID = "ShopID";
	
	private static final String sqlCreate = 
			"CREATE TABLE " + TABLE_PRODUCT_GROUP + " ( "
			+ COLUMN_PRODUCT_GROUP_ID + " INTEGER, "
			+ COLUMN_PRODUCT_GROUP_CODE + " TEXT, "
			+ COLUMN_PRODUCT_GROUP_NAME + " TEXT, "
			+ COLUMN_PRODUCT_GROUP_TYPE + " INTEGER, "
			+ COLUMN_ORDERING + " INTEGER, "
			+ COLUMN_ISCOMMENT + " INTEGER, "
			+ COLUMN_SHOP_ID + " INTEGER  );";
	
	public static void onCreate(SQLiteDatabase db){
		db.execSQL(sqlCreate);
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_GROUP);
		onCreate(db);
	}

}
