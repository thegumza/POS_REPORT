package com.example.pos_report.database;

import java.util.ArrayList;
import java.util.List;

import com.example.pos_peport.database.model.SumPaymentShop;
import com.example.pos_peport.database.model.SumPromotionShop;
import com.example.pos_report.SaleByDate;
import com.example.pos_report.database.table.PayTypeTable;
import com.example.pos_report.database.table.PromotionTable;
import com.example.pos_report.database.table.SumData_PaymentReportTable;
import com.example.pos_report.database.table.SumData_PromotionReportTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


public class GetSumPromotionShopDao extends ReportDatabase{
	String Saledate = SaleByDate.getDate();
	int promotionID = SaleByDate.getPromotionID();
	
	public GetSumPromotionShopDao(Context context) {
		super(context);
	}
	
	public void insertPromoShopData(List<SumPromotionShop> spr){
		// begin transaction
		getWritableDatabase().beginTransaction();
		try{
			// delete all old data
			getWritableDatabase().delete(SumData_PromotionReportTable.TABLE_SUMDATA_PROMOTIONREPORT, null, null);
			for(SumPromotionShop s : spr){
				ContentValues cv = new ContentValues();
				cv.put(SumData_PromotionReportTable.COLUMN_SHOP_ID, s.getShopID());
				cv.put(SumData_PromotionReportTable.COLUMN_SALE_DATE, s.getSaleDate());
				cv.put(SumData_PromotionReportTable.COLUMN_PROMOTION_ID, s.getPromotionID());
				cv.put(SumData_PromotionReportTable.COLUMN_TOTAL_DISCOUNT, s.getDiscount());
				cv.put(SumData_PromotionReportTable.COLUMN_TOTAL_LAST_PRICE, s.getLastPrice());
				
				// insert
				getWritableDatabase().insert(SumData_PromotionReportTable.TABLE_SUMDATA_PROMOTIONREPORT, null, cv);
			}
			// if not have error do commit transaction
			getWritableDatabase().setTransactionSuccessful();
		}finally{
			getWritableDatabase().endTransaction();
		}
	}
	//Insert Data to List From SQLite Database
	public List<SumPromotionShop> getPromoList(){
		List<SumPromotionShop> getsumpromoshop = new ArrayList <SumPromotionShop>();
		String STSql = "Select * From "+ SumData_PromotionReportTable.TABLE_SUMDATA_PROMOTIONREPORT;
		Cursor cursor =  getReadableDatabase().rawQuery(STSql, null);
		if (cursor.moveToFirst()){
			do{
				SumPromotionShop spr = new SumPromotionShop();
				spr.setShopID(cursor.getInt(cursor.getColumnIndex(SumData_PromotionReportTable.COLUMN_SHOP_ID)));
				spr.setSaleDate(cursor.getString(cursor.getColumnIndex(SumData_PromotionReportTable.COLUMN_SALE_DATE)));
				spr.setPromotionID(cursor.getInt(cursor.getColumnIndex(SumData_PromotionReportTable.COLUMN_PROMOTION_ID)));
				spr.setDiscount(cursor.getInt(cursor.getColumnIndex(SumData_PromotionReportTable.COLUMN_TOTAL_DISCOUNT)));
				spr.setLastPrice(cursor.getDouble(cursor.getColumnIndex(SumData_PromotionReportTable.COLUMN_TOTAL_LAST_PRICE)));
				getsumpromoshop.add(spr);
			}while(cursor.moveToNext());
		}
		return getsumpromoshop;
	}
	//Insert Data to List From SQLite Database
	public List<SumPromotionShop> getSumPromoList(){
		List<SumPromotionShop> getsumpromolist = new ArrayList <SumPromotionShop>();
		String GPSql = "Select "+SumData_PromotionReportTable.COLUMN_PROMOTION_ID+","+SumData_PromotionReportTable.COLUMN_SALE_DATE+","+PromotionTable.TABLE_PROMOTION+"." +PromotionTable.COLUMN_PROMOTION_NAME+" , SUM( "
				+SumData_PromotionReportTable.COLUMN_TOTAL_DISCOUNT+" ) as TotalDiscount FROM "+SumData_PromotionReportTable.TABLE_SUMDATA_PROMOTIONREPORT+" LEFT JOIN "
				+PromotionTable.TABLE_PROMOTION+ " USING( " + SumData_PromotionReportTable.COLUMN_PROMOTION_ID+ " ) GROUP BY "
				+PromotionTable.COLUMN_PROMOTION_NAME;
		Cursor cursor =  getReadableDatabase().rawQuery(GPSql, null);
		if (cursor.moveToFirst()){
			do{
				SumPromotionShop sps = new SumPromotionShop();
				sps.setPromotionID(cursor.getInt(cursor.getColumnIndex(SumData_PromotionReportTable.COLUMN_PROMOTION_ID)));
				sps.setSaleDate(cursor.getString(cursor.getColumnIndex(SumData_PromotionReportTable.COLUMN_SALE_DATE)));
				sps.setPromotionName(cursor.getString(cursor.getColumnIndex(PromotionTable.COLUMN_PROMOTION_NAME)));
				sps.setDiscount(cursor.getDouble(cursor.getColumnIndex(SumData_PromotionReportTable.COLUMN_TOTAL_DISCOUNT)));
				getsumpromolist.add(sps);
			}while(cursor.moveToNext());
			}
		return getsumpromolist;
	}
	
	public SumPromotionShop getPromotiongraph(){
		
		String STSql = "Select * From "+ SumData_PromotionReportTable.TABLE_SUMDATA_PROMOTIONREPORT;
		Cursor cursor =  getReadableDatabase().rawQuery(STSql, null);
		SumPromotionShop spr = new SumPromotionShop();
		if (cursor.moveToFirst()){
			do{
				spr.setShopID(cursor.getInt(cursor.getColumnIndex(SumData_PromotionReportTable.COLUMN_SHOP_ID)));
				spr.setSaleDate(cursor.getString(cursor.getColumnIndex(SumData_PromotionReportTable.COLUMN_SALE_DATE)));
				spr.setPromotionID(cursor.getInt(cursor.getColumnIndex(SumData_PromotionReportTable.COLUMN_PROMOTION_ID)));
				spr.setDiscount(cursor.getInt(cursor.getColumnIndex(SumData_PromotionReportTable.COLUMN_TOTAL_DISCOUNT)));
				spr.setLastPrice(cursor.getDouble(cursor.getColumnIndex(SumData_PromotionReportTable.COLUMN_TOTAL_LAST_PRICE)));
				
			}while(cursor.moveToNext());
		}
		return spr;
	}
	//Insert Data to Object From SQLite Database
	public SumPromotionShop getSumPromotion(){
		String SPSql = "SELECT sum( "+SumData_PromotionReportTable.COLUMN_TOTAL_DISCOUNT+" ) as TotalDiscount from "
	+SumData_PromotionReportTable.TABLE_SUMDATA_PROMOTIONREPORT;
		Cursor cursor =  getReadableDatabase().rawQuery(SPSql, null);
		SumPromotionShop spr = new SumPromotionShop();
		if (cursor.moveToFirst()){
			
			spr.setDiscount(cursor.getDouble(cursor.getColumnIndex(SumData_PromotionReportTable.COLUMN_TOTAL_DISCOUNT)));
			
		}
		return spr;
		
	}
	public List<SumPromotionShop> getPromoDetail(){
		List<SumPromotionShop> getsumpromolist = new ArrayList <SumPromotionShop>();
		String GPSql = ";Select "+PromotionTable.TABLE_PROMOTION+"." +PromotionTable.COLUMN_PROMOTION_NAME+" , SUM( "
				+SumData_PromotionReportTable.COLUMN_TOTAL_DISCOUNT+" ) as TotalDiscount FROM "+SumData_PromotionReportTable.TABLE_SUMDATA_PROMOTIONREPORT+" LEFT JOIN "
				+PromotionTable.TABLE_PROMOTION+ " USING( " + SumData_PromotionReportTable.COLUMN_PROMOTION_ID+ " ) WHERE Saledate ='"+Saledate+"' GROUP BY "
				+PromotionTable.COLUMN_PROMOTION_NAME;
		Cursor cursor =  getReadableDatabase().rawQuery(GPSql, null);
		if (cursor.moveToFirst()){
			do{
				SumPromotionShop sps = new SumPromotionShop();
				sps.setPromotionName(cursor.getString(cursor.getColumnIndex(PromotionTable.COLUMN_PROMOTION_NAME)));
				sps.setDiscount(cursor.getInt(cursor.getColumnIndex(SumData_PromotionReportTable.COLUMN_TOTAL_DISCOUNT)));
				getsumpromolist.add(sps);
			}while(cursor.moveToNext());
			}
		return getsumpromolist;
	}
	public SumPromotionShop getSumPromoDetail(){
		String SPSql = "SELECT sum( "+SumData_PromotionReportTable.COLUMN_TOTAL_DISCOUNT+" ) as TotalDiscount from "
	+SumData_PromotionReportTable.TABLE_SUMDATA_PROMOTIONREPORT+" WHERE Saledate ='"+Saledate+"'";
		Cursor cursor =  getReadableDatabase().rawQuery(SPSql, null);
		SumPromotionShop spr = new SumPromotionShop();
		if (cursor.moveToFirst()){
			
			spr.setDiscount(cursor.getDouble(cursor.getColumnIndex(SumData_PromotionReportTable.COLUMN_TOTAL_DISCOUNT)));
			
		}
		return spr;
		
	}
	public List<SumPromotionShop> getPromoDetailGraph(){
		String SPSql = 
				"Select "+PromotionTable.TABLE_PROMOTION+"." + PromotionTable.COLUMN_PROMOTION_NAME + " ,sum( "
				+SumData_PromotionReportTable.COLUMN_TOTAL_DISCOUNT+ " ) as TotalDiscount From "
				+ SumData_PromotionReportTable.TABLE_SUMDATA_PROMOTIONREPORT +" LEFT JOIN " +PromotionTable.TABLE_PROMOTION
				+ " USING " + " (" + PromotionTable.COLUMN_PROMOTION_ID + ") WHERE "+SumData_PromotionReportTable.COLUMN_SALE_DATE+" ='"+Saledate +"' GROUP BY " + PromotionTable.COLUMN_PROMOTION_NAME ;
		List<SumPromotionShop> getpromo = new ArrayList <SumPromotionShop>();
		Cursor cursor =  getReadableDatabase().rawQuery(SPSql, null);
		if (cursor.moveToFirst()){
			do{
				SumPromotionShop ps = new SumPromotionShop();
				ps.setPromotionName(cursor.getString(cursor.getColumnIndex(PromotionTable.COLUMN_PROMOTION_NAME)));
				ps.setDiscount(cursor.getDouble(cursor.getColumnIndex(SumData_PromotionReportTable.COLUMN_TOTAL_DISCOUNT)));
				getpromo.add(ps);
			}while(cursor.moveToNext());
		}
		return getpromo;
	}
public List<SumPromotionShop> getSaleDatePromotionDetail(){
		
		String SRSql = 
				"Select * FROM "+SumData_PromotionReportTable.TABLE_SUMDATA_PROMOTIONREPORT+" WHERE "+SumData_PromotionReportTable.COLUMN_PROMOTION_ID+" ='"+promotionID+"'";
		List<SumPromotionShop> getpromotion = new ArrayList <SumPromotionShop>();
		Cursor cursor =  getReadableDatabase().rawQuery(SRSql, null);
		if (cursor.moveToFirst()){
			do{
				SumPromotionShop ps = new SumPromotionShop();
				ps.setSaleDate(cursor.getString(cursor.getColumnIndex(SumData_PromotionReportTable.COLUMN_SALE_DATE)));
				ps.setDiscount(cursor.getDouble(cursor.getColumnIndex(SumData_PromotionReportTable.COLUMN_TOTAL_DISCOUNT)));
				getpromotion.add(ps);
			}while(cursor.moveToNext());
		}
		return getpromotion;
	}
public SumPromotionShop getSumPromotionType(){
	String SPSql = "SELECT sum( "+SumData_PromotionReportTable.COLUMN_TOTAL_DISCOUNT+" ) as TotalDiscount from "
+SumData_PromotionReportTable.TABLE_SUMDATA_PROMOTIONREPORT+" WHERE "+SumData_PromotionReportTable.COLUMN_PROMOTION_ID+" ='"+promotionID+"'";
	Cursor cursor =  getReadableDatabase().rawQuery(SPSql, null);
	SumPromotionShop spr = new SumPromotionShop();
	if (cursor.moveToFirst()){
		
		spr.setDiscount(cursor.getDouble(cursor.getColumnIndex(SumData_PromotionReportTable.COLUMN_TOTAL_DISCOUNT)));
		
	}
	return spr;
	
}
	
}



