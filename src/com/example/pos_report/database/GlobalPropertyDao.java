package com.example.pos_report.database;

import java.util.List;

import com.example.pos_report.database.model.GlobalProperty;
import com.example.pos_report.database.table.GlobalPropertyTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * DAO stand for Data Access Object
 * @author j1tth4
 *
 */
public class GlobalPropertyDao extends ReportDatabase{

	public GlobalPropertyDao(Context context) {
		super(context);
	}
	
	public void insertGlobalPropertyData(List<GlobalProperty> gl){
		// begin transaction
		getWritableDatabase().beginTransaction();
		try{
			// delete all old data
			getWritableDatabase().delete(GlobalPropertyTable.TABLE_GLOBAL_PROPERTY, null, null);
			for(GlobalProperty gp : gl){
				ContentValues cv = new ContentValues();
				cv.put(GlobalPropertyTable.COLUMN_CURRENCY_SYMBOL, gp.getCurrencySymbol());
				cv.put(GlobalPropertyTable.COLUMN_CURRENCY_CODE, gp.getCurrencyCode());
				cv.put(GlobalPropertyTable.COLUMN_CURRENCY_NAME, gp.getCurrencyName());
				cv.put(GlobalPropertyTable.COLUMN_CURRENCY_FORMAT, gp.getCurrencyFormat());
				cv.put(GlobalPropertyTable.COLUMN_DATE_FORMAT, gp.getDateFormat());
				cv.put(GlobalPropertyTable.COLUMN_TIME_FORMAT, gp.getTimeFormat());
				cv.put(GlobalPropertyTable.COLUMN_QTY_FORMAT, gp.getQtyFormat());
				cv.put(GlobalPropertyTable.COLUMN_PREFIX_TEXT_TW, gp.getPrefixTextTW());
				cv.put(GlobalPropertyTable.COLUMN_POSITION_PREFIX, gp.getPositionPrefix());
				// insert
				getWritableDatabase().insert(GlobalPropertyTable.TABLE_GLOBAL_PROPERTY, null, cv);
			}
			// if not have error do commit transaction
			getWritableDatabase().setTransactionSuccessful();
		}finally{
			getWritableDatabase().endTransaction();
		}
		
	}
	public GlobalProperty getGlobalProperty() {
		
		String SPSql = "SELECT * FROM "+GlobalPropertyTable.TABLE_GLOBAL_PROPERTY;
		
					Cursor cursor =  getReadableDatabase().rawQuery(SPSql, null);
					GlobalProperty gp = new GlobalProperty();
					if (cursor.moveToFirst()){
						
						gp.setCurrencyFormat(cursor.getString(cursor.getColumnIndex(GlobalPropertyTable.COLUMN_CURRENCY_FORMAT)));
						gp.setQtyFormat(cursor.getString(cursor.getColumnIndex(GlobalPropertyTable.COLUMN_QTY_FORMAT)));
					}
					return gp;
		
	}

}
