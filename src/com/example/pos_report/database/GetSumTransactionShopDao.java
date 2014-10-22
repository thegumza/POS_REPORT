package com.example.pos_report.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.pos_report.database.model.SumTransactionShop;
import com.example.pos_report.database.table.SumData_TransReportTable;

public class GetSumTransactionShopDao extends ReportDatabase{
	
	public GetSumTransactionShopDao(Context context) {
		super(context);
		
	}
	public void insertSumTransactionShopData(List<SumTransactionShop> gs){
		// begin transaction
		getWritableDatabase().beginTransaction();
		try{
			// delete all old data
			getWritableDatabase().delete(SumData_TransReportTable.TABLE_SUMDATA_TRANSPORTREPORT, null, null);
			for(SumTransactionShop s : gs){
				ContentValues cv = new ContentValues();
				cv.put(SumData_TransReportTable.COLUMN_SHOP_ID, s.getShopID());
				cv.put(SumData_TransReportTable.COLUMN_SALE_DATE, s.getSaleDate());
				cv.put(SumData_TransReportTable.COLUMN_TOTAL_BILL, s.getTotalBill());
				cv.put(SumData_TransReportTable.COLUMN_TOTAL_CUSTOMER, s.getTotalCust());
				cv.put(SumData_TransReportTable.COLUMN_TRANSACTION_VAT, s.getTransVAT());
				cv.put(SumData_TransReportTable.COLUMN_TOTAL_RETAIL_PRICE, s.getRetailPrice());
				cv.put(SumData_TransReportTable.COLUMN_TOTAL_DISCOUNT, s.getDiscount());
				cv.put(SumData_TransReportTable.COLUMN_TOTAL_SALE_PRICE, s.getSalePrice());
				
				// insert
				getWritableDatabase().insert(SumData_TransReportTable.TABLE_SUMDATA_TRANSPORTREPORT, null, cv);
			}
			// if not have error do commit transaction
			getWritableDatabase().setTransactionSuccessful();
		}finally{
			getWritableDatabase().endTransaction();
		}
	}
	//Insert Data to List From SQLite Database
	public List<SumTransactionShop> getSaleDate(){
		List<SumTransactionShop> getsumtransshop = new ArrayList <SumTransactionShop>();
		String STSql = "Select * From "+ SumData_TransReportTable.TABLE_SUMDATA_TRANSPORTREPORT;
		Cursor cursor =  getReadableDatabase().rawQuery(STSql, null);
		if (cursor.moveToFirst()){
			do{
				SumTransactionShop gs = new SumTransactionShop();
				gs.setShopID(cursor.getInt(cursor.getColumnIndex(SumData_TransReportTable.COLUMN_SHOP_ID)));
				gs.setSaleDate(cursor.getString(cursor.getColumnIndex(SumData_TransReportTable.COLUMN_SALE_DATE)));
				gs.setTotalBill(cursor.getInt(cursor.getColumnIndex(SumData_TransReportTable.COLUMN_TOTAL_BILL)));
				gs.setTotalCust(cursor.getInt(cursor.getColumnIndex(SumData_TransReportTable.COLUMN_TOTAL_CUSTOMER)));
				gs.setTransVAT(cursor.getDouble(cursor.getColumnIndex(SumData_TransReportTable.COLUMN_TRANSACTION_VAT)));
				gs.setRetailPrice(cursor.getDouble(cursor.getColumnIndex(SumData_TransReportTable.COLUMN_TOTAL_RETAIL_PRICE)));
				gs.setDiscount(cursor.getDouble(cursor.getColumnIndex(SumData_TransReportTable.COLUMN_TOTAL_DISCOUNT)));
				gs.setSalePrice(cursor.getDouble(cursor.getColumnIndex(SumData_TransReportTable.COLUMN_TOTAL_SALE_PRICE)));
				getsumtransshop.add(gs);
			}while(cursor.moveToNext());
		}
		return getsumtransshop;
	}
	//Insert Data to Object From SQLite Database
	
	public SumTransactionShop getSumSaleShop(){
		String SSSql = 
	"Select sum ( "+ SumData_TransReportTable.COLUMN_TOTAL_BILL + " ) 	as TotalBill," 
	+"sum ( "+ SumData_TransReportTable.COLUMN_TOTAL_DISCOUNT + " ) 	as TotalDiscount," 
	+"sum ( "+ SumData_TransReportTable.COLUMN_TOTAL_SALE_PRICE + " ) 	as TotalSalePrice From "
	+ SumData_TransReportTable.TABLE_SUMDATA_TRANSPORTREPORT;
		
		Cursor cursor =  getReadableDatabase().rawQuery(SSSql, null);
		SumTransactionShop gs = new SumTransactionShop();
		if (cursor.moveToFirst()){
			do{
				gs.setTotalBill(cursor.getInt(cursor.getColumnIndex(SumData_TransReportTable.COLUMN_TOTAL_BILL)));
				gs.setDiscount(cursor.getDouble(cursor.getColumnIndex(SumData_TransReportTable.COLUMN_TOTAL_DISCOUNT)));
				gs.setSalePrice(cursor.getDouble(cursor.getColumnIndex(SumData_TransReportTable.COLUMN_TOTAL_SALE_PRICE)));
			}while(cursor.moveToNext());
		}
		return gs;
	}
	public List<SumTransactionShop> getSalebyDateGraph(){
		List<SumTransactionShop> getsumtransshop = new ArrayList <SumTransactionShop>();
		String SSSql = 
	"Select "+ SumData_TransReportTable.COLUMN_SALE_DATE + " as SaleDate," 
	+"( "+ SumData_TransReportTable.COLUMN_TOTAL_SALE_PRICE + " ) 	as TotalSalePrice From "
	+ SumData_TransReportTable.TABLE_SUMDATA_TRANSPORTREPORT;
		
		Cursor cursor =  getReadableDatabase().rawQuery(SSSql, null);
		SumTransactionShop gd = new SumTransactionShop();
		if (cursor.moveToFirst()){
			do{
			gd.setSaleDate(cursor.getString(cursor.getColumnIndex(SumData_TransReportTable.COLUMN_SALE_DATE)));
			gd.setSalePrice(cursor.getDouble(cursor.getColumnIndex(SumData_TransReportTable.COLUMN_TOTAL_SALE_PRICE)));
			getsumtransshop.add(gd);
			}while(cursor.moveToNext())	;
		}
		return getsumtransshop;
	}
	public SumTransactionShop getLastSaleDate(){
		String STSql = "Select * From "+ SumData_TransReportTable.TABLE_SUMDATA_TRANSPORTREPORT;
		Cursor cursor =  getReadableDatabase().rawQuery(STSql, null);
		SumTransactionShop gs = new SumTransactionShop();
		if (cursor.moveToFirst()){
				
				gs.setShopID(cursor.getInt(cursor.getColumnIndex(SumData_TransReportTable.COLUMN_SHOP_ID)));
				gs.setSaleDate(cursor.getString(cursor.getColumnIndex(SumData_TransReportTable.COLUMN_SALE_DATE)));
				gs.setTotalBill(cursor.getInt(cursor.getColumnIndex(SumData_TransReportTable.COLUMN_TOTAL_BILL)));
				gs.setTotalCust(cursor.getInt(cursor.getColumnIndex(SumData_TransReportTable.COLUMN_TOTAL_CUSTOMER)));
				gs.setTransVAT(cursor.getDouble(cursor.getColumnIndex(SumData_TransReportTable.COLUMN_TRANSACTION_VAT)));
				gs.setRetailPrice(cursor.getDouble(cursor.getColumnIndex(SumData_TransReportTable.COLUMN_TOTAL_RETAIL_PRICE)));
				gs.setDiscount(cursor.getDouble(cursor.getColumnIndex(SumData_TransReportTable.COLUMN_TOTAL_DISCOUNT)));
				gs.setSalePrice(cursor.getDouble(cursor.getColumnIndex(SumData_TransReportTable.COLUMN_TOTAL_SALE_PRICE)));
		}
		return gs;
	}
}



