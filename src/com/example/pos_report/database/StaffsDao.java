package com.example.pos_report.database;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;

import com.example.pos_report.database.model.Staffs;
import com.example.pos_report.database.table.StaffsTable;

public class StaffsDao extends ReportDatabase{

	public StaffsDao(Context context) {
		super(context);
	}
	public void insertStaffsData(List<Staffs> st){
		// begin transaction
		getWritableDatabase().beginTransaction();
		try{
			// delete all old data
			getWritableDatabase().delete(StaffsTable.TABLE_STAFFS, null, null);
			for(Staffs s : st){
				ContentValues cv = new ContentValues();
				cv.put(StaffsTable.COLUMN_STAFF_ID, s.getStaffID());
				cv.put(StaffsTable.COLUMN_STAFF_CODE, s.getStaffCode());
				cv.put(StaffsTable.COLUMN_STAFF_NAME, s.getStaffName());
				cv.put(StaffsTable.COLUMN_STAFF_PASSWORD, s.getStaffPassword());
				cv.put(StaffsTable.COLUMN_STAFF_ROLE_ID, s.getStaffRoleID());
				cv.put(StaffsTable.COLUMN_STAFF_ROLE_NAME, s.getStaffRoleName());
				// insert
				getWritableDatabase().insert(StaffsTable.TABLE_STAFFS, null, cv);
			}
			// if not have error do commit transaction
			getWritableDatabase().setTransactionSuccessful();
		}finally{
			getWritableDatabase().endTransaction();
		}
	}
	
}



