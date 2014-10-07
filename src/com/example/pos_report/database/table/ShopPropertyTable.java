package com.example.pos_report.database.table;

import android.database.sqlite.SQLiteDatabase;

public class ShopPropertyTable {
	public static final String TABLE_SHOP = "ShopProperty";
	public static final String COLUMN_SHOP_ID = "ShopID";
	public static final String COLUMN_SHOP_CODE = "ShopCode";
	public static final String COLUMN_SHOP_NAME = "ShopNAME";
	public static final String COLUMN_SHOP_TYPE = "ShopType";
	public static final String COLUMN_SHOP_GROUP_ID = "ShopGroupID";
	
	private static String sqlCreate = 
			"CREATE TABLE " + TABLE_SHOP + " ("
			+ COLUMN_SHOP_ID + " INTEGER, "
			+ COLUMN_SHOP_CODE + " TEXT, "
			+ COLUMN_SHOP_NAME + " TEXT, "
			+ COLUMN_SHOP_TYPE + " INTEGER, "
			+ COLUMN_SHOP_GROUP_ID + " INTEGER, "
			+ " PRIMARY KEY (" + COLUMN_SHOP_ID + "));";
	
	public static void onCreate(SQLiteDatabase db){
		db.execSQL(sqlCreate);
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOP);
		onCreate(db);
	}
}
