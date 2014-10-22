package com.example.pos_report.database;

import java.util.List;

import com.example.pos_report.database.model.SaleMode;
import com.example.pos_report.database.table.SaleModeTable;

import android.content.ContentValues;
import android.content.Context;

public class SaleModeDao extends ReportDatabase{

	public SaleModeDao(Context context) {
		super(context);
	}
	public void insertSaleModeData(List<SaleMode> sm){
		// begin transaction
		getWritableDatabase().beginTransaction();
		try{
			// delete all old data
			getWritableDatabase().delete(SaleModeTable.TABLE_SALEMODE, null, null);
			for(SaleMode s : sm){
				ContentValues cv = new ContentValues();
				cv.put(SaleModeTable.COLUMN_SALE_MODE_ID, s.getSaleModeID());
				cv.put(SaleModeTable.COLUMN_SALE_MODE_NAME, s.getSaleModeName());
				cv.put(SaleModeTable.COLUMN_POSITION_PREFIX, s.getPositionPrefix());
				cv.put(SaleModeTable.COLUMN_PREFIX_TEXT, s.getPrefixText());
				cv.put(SaleModeTable.COLUMN_PREFIX_QUEUE, s.getPrefixQueue());
				// insert
				getWritableDatabase().insert(SaleModeTable.TABLE_SALEMODE, null, cv);
			}
			// if not have error do commit transaction
			getWritableDatabase().setTransactionSuccessful();
		}finally{
			getWritableDatabase().endTransaction();
		}
	}
	
}



