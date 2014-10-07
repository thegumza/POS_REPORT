package com.example.pos_report.database.table;

import android.database.sqlite.SQLiteDatabase;

public class StaffsTable {
	
	public static final String TABLE_STAFFS = "Staffs";

	public static final String COLUMN_STAFF_ID = "StaffID";
	public static final String COLUMN_STAFF_CODE = "StaffCode";
	public static final String COLUMN_STAFF_NAME = "StaffName";
	public static final String COLUMN_STAFF_PASSWORD = "StaffPassword";
	public static final String COLUMN_STAFF_ROLE_ID = "StaffRoleID";
	public static final String COLUMN_STAFF_ROLE_NAME = "StaffRoleName";
	
	private static String sqlCreate = 
			"CREATE TABLE " + TABLE_STAFFS + " ( "
			+ COLUMN_STAFF_ID + " INTEGER, "
			+ COLUMN_STAFF_CODE + " TEXT, "
			+ COLUMN_STAFF_NAME + " TEXT, "
			+ COLUMN_STAFF_PASSWORD + " TEXT, "
			+ COLUMN_STAFF_ROLE_ID + " INTEGER, "
			+ COLUMN_STAFF_ROLE_NAME + " TEXT, "
			+ " PRIMARY KEY (" + COLUMN_STAFF_ID + "));";
	
	public static void onCreate(SQLiteDatabase db){
		db.execSQL(sqlCreate);
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_STAFFS);
		onCreate(db);
	}
}
