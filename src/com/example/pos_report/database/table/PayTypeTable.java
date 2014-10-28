package com.example.pos_report.database.table;

import android.database.sqlite.SQLiteDatabase;

public class PayTypeTable {
	public static final String TABLE_PAYTYPE = "PayType";
	public static final String COLUMN_PAYTYPE_ID = "PayTypeID";
	public static final String COLUMN_PAYTYPE_CODE = "PayTypeCode";
	public static final String COLUMN_PAYTYPE_NAME = "PayTypeName";
	public static final String COLUMN_ORDERING = "Ordering";
	
	private static String sqlCreate = 
			"CREATE TABLE "+ TABLE_PAYTYPE +" ("
			+ COLUMN_PAYTYPE_ID + " INTEGER, "
			+ COLUMN_PAYTYPE_CODE + " TEXT, "
			+ COLUMN_PAYTYPE_NAME + " TEXT, "
			+ COLUMN_ORDERING + " INTEGER );";
	
	public static void oncreate (SQLiteDatabase db){
		db.execSQL(sqlCreate);
	}
	public static void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
		db.execSQL("DROP TABLE IF EXITS " + TABLE_PAYTYPE);
		oncreate(db);
	}
}
