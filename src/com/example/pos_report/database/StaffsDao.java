package com.example.pos_report.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.pos_report.POS_Login;
import com.example.pos_report.database.model.Staffs;
import com.example.pos_report.database.model.SumPaymentShop;
import com.example.pos_report.database.table.StaffsTable;
import com.example.pos_report.database.table.SumData_PaymentReportTable;

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
public List<Staffs> getStaffs(){
		
	String SPSql = "Select * From "+ StaffsTable.TABLE_STAFFS;
	Cursor cursor =  getReadableDatabase().rawQuery(SPSql, null);
		List<Staffs> getstaff = new ArrayList <Staffs>();
		if (cursor.moveToFirst()){
			do{
				Staffs st = new Staffs();
				st.setStaffCode(cursor.getString(cursor.getColumnIndex(StaffsTable.COLUMN_STAFF_CODE)));
				st.setStaffPassword(cursor.getString(cursor.getColumnIndex(StaffsTable.COLUMN_STAFF_PASSWORD)));
				getstaff.add(st);
			}while(cursor.moveToNext());
		}
		return getstaff;
	}
}



