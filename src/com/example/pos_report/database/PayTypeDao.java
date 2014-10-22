package com.example.pos_report.database;

import java.util.ArrayList;
import java.util.List;

import com.example.pos_report.database.model.PayType;
import com.example.pos_report.database.table.PayTypeTable;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class PayTypeDao extends ReportDatabase{

	public PayTypeDao(Context context) {
		super(context);
	}
	public void insertPayTypeData(List<PayType> pt){
		// begin transaction
		getWritableDatabase().beginTransaction();
		try{
			// delete all old data
			getWritableDatabase().delete(PayTypeTable.TABLE_PAYTYPE, null, null);
			for(PayType s : pt){
				ContentValues cv = new ContentValues();
				cv.put(PayTypeTable.COLUMN_PAYTYPE_ID, s.getPayTypeID());
				cv.put(PayTypeTable.COLUMN_PAYTYPE_CODE, s.getPayTypeCode());
				cv.put(PayTypeTable.COLUMN_PAYTYPE_NAME, s.getPayTypeName());
				cv.put(PayTypeTable.COLUMN_ORDERING, s.getOrdering());
				// insert
				getWritableDatabase().insert(PayTypeTable.TABLE_PAYTYPE, null, cv);
			}
			// if not have error do commit transaction
			getWritableDatabase().setTransactionSuccessful();
		}finally{
			getWritableDatabase().endTransaction();
		}
	}
	//Insert Data to List From SQLite Database
	public List<PayType> getPayTypeList(){
		List<PayType> paytype = new ArrayList <PayType>();
		String PTSql = "Select * From "+ PayTypeTable.TABLE_PAYTYPE;
		Cursor cursor =  getReadableDatabase().rawQuery(PTSql, null);
		if (cursor.moveToFirst()){
			do{
				PayType pt = new PayType();
				pt.setPayTypeID(cursor.getInt(cursor.getColumnIndex(PayTypeTable.COLUMN_PAYTYPE_ID)));
				pt.setPayTypeCode(cursor.getString(cursor.getColumnIndex(PayTypeTable.COLUMN_PAYTYPE_CODE)));
				pt.setPayTypeName(cursor.getString(cursor.getColumnIndex(PayTypeTable.COLUMN_PAYTYPE_NAME)));
				pt.setOrdering(cursor.getInt(cursor.getColumnIndex(PayTypeTable.COLUMN_ORDERING)));
				paytype.add(pt);
			}while(cursor.moveToNext());
		}
		return paytype;
	}
}
