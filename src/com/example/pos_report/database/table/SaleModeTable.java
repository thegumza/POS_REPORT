package com.example.pos_report.database.table;

import android.database.sqlite.SQLiteDatabase;

public class SaleModeTable {
	public static final String TABLE_SALEMODE = "SaleMode";
	public static final String COLUMN_SALE_MODE_ID = "SaleModeID";
	public static final String COLUMN_SALE_MODE_NAME = "SaleModeName";
	public static final String COLUMN_POSITION_PREFIX = "PositionPrefix";
	public static final String COLUMN_PREFIX_TEXT = "PrefixText";
	public static final String COLUMN_PREFIX_QUEUE = "PrefixQueue";
	
	private static String sqlCreate = 
			"CREATE TABLE " + TABLE_SALEMODE + " ("
			+ COLUMN_SALE_MODE_ID + " INTEGER, "
			+ COLUMN_SALE_MODE_NAME + " TEXT, "
			+ COLUMN_POSITION_PREFIX + " INTEGER, "
			+ COLUMN_PREFIX_TEXT + " TEXT, "
			+ COLUMN_PREFIX_QUEUE + " INTEGER );";
	
	public static void onCreate(SQLiteDatabase db){
		db.execSQL(sqlCreate);
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SALEMODE);
		onCreate(db);
	}
}
