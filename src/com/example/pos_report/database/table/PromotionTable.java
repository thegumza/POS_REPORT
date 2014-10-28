package com.example.pos_report.database.table;

import android.database.sqlite.SQLiteDatabase;

public class PromotionTable {

	public static final String TABLE_PROMOTION = "Promotion";
	public static final String COLUMN_PROMOTION_ID = "PromotionID";
	public static final String COLUMN_TYPE_ID = "TypeID";
	public static final String COLUMN_PROMOTION_NAME = "PromotionName";
	
	private static String sqlCreate = 
			"CREATE TABLE " + TABLE_PROMOTION + " ("
			+ COLUMN_PROMOTION_ID + " INTEGER, "
			+ COLUMN_TYPE_ID + " INTEGER, "
			+ COLUMN_PROMOTION_NAME + " TEXT )";
	
	public static void onCreate(SQLiteDatabase db){
		db.execSQL(sqlCreate);
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROMOTION);
		onCreate(db);
	}
}
