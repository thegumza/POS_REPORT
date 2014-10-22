package com.example.pos_report.database;

import java.util.ArrayList;
import java.util.List;

import com.example.pos_report.database.model.Promotion;
import com.example.pos_report.database.table.PromotionTable;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


public class PromotionDao extends ReportDatabase{

	public PromotionDao(Context context) {
		super(context);
	}
	public void insertPromotionData(List<Promotion> pr){
		// begin transaction
		getWritableDatabase().beginTransaction();
		try{
			// delete all old data
			getWritableDatabase().delete(PromotionTable.TABLE_PROMOTION, null, null);
			for(Promotion s : pr){
				ContentValues cv = new ContentValues();
				cv.put(PromotionTable.COLUMN_PROMOTION_ID, s.getPromotionID());
				cv.put(PromotionTable.COLUMN_TYPE_ID, s.getTypeID());
				cv.put(PromotionTable.COLUMN_PROMOTION_NAME, s.getPromotionName());
				// insert
				getWritableDatabase().insert(PromotionTable.TABLE_PROMOTION, null, cv);
			}
			// if not have error do commit transaction
			getWritableDatabase().setTransactionSuccessful();
		}finally{
			getWritableDatabase().endTransaction();
		}
	}
	//Insert Data to List From SQLite Database
	public List<Promotion> getPromotionList(){
		List<Promotion> promotion = new ArrayList <Promotion>();
		String PRSql = "Select * From "+ PromotionTable.TABLE_PROMOTION;
		Cursor cursor =  getReadableDatabase().rawQuery(PRSql, null);
		if (cursor.moveToFirst()){
			do{
				Promotion pr = new Promotion();
				pr.setPromotionID(cursor.getInt(cursor.getColumnIndex(PromotionTable.COLUMN_PROMOTION_ID)));
				pr.setTypeID(cursor.getInt(cursor.getColumnIndex(PromotionTable.COLUMN_TYPE_ID)));
				pr.setPromotionName(cursor.getString(cursor.getColumnIndex(PromotionTable.COLUMN_PROMOTION_NAME)));
				promotion.add(pr);
			}while(cursor.moveToNext());
		}
		return promotion;
	}
}
