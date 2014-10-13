package com.example.pos_report.database;

import java.util.ArrayList;
import java.util.List;

import com.example.pos_peport.database.model.SumPaymentShop;
import com.example.pos_report.SaleByDate;
import com.example.pos_report.database.table.PayTypeTable;
import com.example.pos_report.database.table.SumData_PaymentReportTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


public class GetSumPaymentShopDao extends ReportDatabase{
	String Saledate = SaleByDate.getDate();
	int payTypeID = SaleByDate.getPayTypeID();
	public GetSumPaymentShopDao(Context context) {
		super(context);
	}
	
	public void insertPaymentShopData(List<SumPaymentShop> sps){
		// begin transaction
		getWritableDatabase().beginTransaction();
		try{
			// delete all old data
			getWritableDatabase().delete(SumData_PaymentReportTable.TABLE_SUMDATA_PAYMENT_REPORT, null, null);
			for(SumPaymentShop s : sps){
				ContentValues cv = new ContentValues();
				cv.put(SumData_PaymentReportTable.COLUMN_SHOP_ID, s.getShopID());
				cv.put(SumData_PaymentReportTable.COLUMN_SALE_DATE, s.getSaleDate());
				cv.put(SumData_PaymentReportTable.COLUMN_PAYTYPE_ID, s.getPayTypeID());
				cv.put(SumData_PaymentReportTable.COLUMN_TOTAL_PAY, s.getTotalPay());
				
				// insert
				getWritableDatabase().insert(SumData_PaymentReportTable.TABLE_SUMDATA_PAYMENT_REPORT, null, cv);
			}
			// if not have error do commit transaction
			getWritableDatabase().setTransactionSuccessful();
		}finally{
			getWritableDatabase().endTransaction();
		}
	}
	//Insert Data to List From SQLite Database
	public List<SumPaymentShop> getPayment(){
		List<SumPaymentShop> getpaymentshop = new ArrayList <SumPaymentShop>();
		String SPSql = "Select * From "+ SumData_PaymentReportTable.TABLE_SUMDATA_PAYMENT_REPORT;
		Cursor cursor =  getReadableDatabase().rawQuery(SPSql, null);
		if (cursor.moveToFirst()){
			do{
				SumPaymentShop gs = new SumPaymentShop();
				gs.setShopID(cursor.getInt(cursor.getColumnIndex(SumData_PaymentReportTable.COLUMN_SHOP_ID)));
				gs.setSaleDate(cursor.getString(cursor.getColumnIndex(SumData_PaymentReportTable.COLUMN_SALE_DATE)));
				gs.setPayTypeID(cursor.getInt(cursor.getColumnIndex(SumData_PaymentReportTable.COLUMN_PAYTYPE_ID)));
				gs.setTotalPay(cursor.getInt(cursor.getColumnIndex(SumData_PaymentReportTable.COLUMN_TOTAL_PAY)));
				getpaymentshop.add(gs);
			}while(cursor.moveToNext());
		}
		return getpaymentshop;
	}
	
	public List<SumPaymentShop> getPaymentlist(){
		String SPSql = 
				"Select " + PayTypeTable.TABLE_PAYTYPE + "."+PayTypeTable.COLUMN_PAYTYPE_NAME+ " ,sum( "
				+SumData_PaymentReportTable.TABLE_SUMDATA_PAYMENT_REPORT+ " . "
						+SumData_PaymentReportTable.COLUMN_TOTAL_PAY + " ) as TotalPay From "
				+ SumData_PaymentReportTable.TABLE_SUMDATA_PAYMENT_REPORT +" JOIN " +PayTypeTable.TABLE_PAYTYPE
				+ " USING " + " (" + PayTypeTable.COLUMN_PAYTYPE_ID + ") " + " GROUP BY " + PayTypeTable.COLUMN_PAYTYPE_NAME ;
		
		List<SumPaymentShop> getpayment = new ArrayList <SumPaymentShop>();
		Cursor cursor =  getReadableDatabase().rawQuery(SPSql, null);
		if (cursor.moveToFirst()){
			do{
				SumPaymentShop ps = new SumPaymentShop();
				ps.setPayTypeName(cursor.getString(cursor.getColumnIndex(PayTypeTable.COLUMN_PAYTYPE_NAME)));
				ps.setTotalPay(cursor.getDouble(cursor.getColumnIndex(SumData_PaymentReportTable.COLUMN_TOTAL_PAY)));
				getpayment.add(ps);
			}while(cursor.moveToNext());
		}
		return getpayment;
	}
	
	public SumPaymentShop getSumPayment(){
		String SPSql = "SELECT sum( "+SumData_PaymentReportTable.COLUMN_TOTAL_PAY+" ) as TotalPay from "+SumData_PaymentReportTable.TABLE_SUMDATA_PAYMENT_REPORT;
		Cursor cursor =  getReadableDatabase().rawQuery(SPSql, null);
		SumPaymentShop ps = new SumPaymentShop();
		if (cursor.moveToFirst()){
				ps.setTotalPay(cursor.getDouble(cursor.getColumnIndex(SumData_PaymentReportTable.COLUMN_TOTAL_PAY)));
		}
		return ps;
	}
	
	public List<SumPaymentShop> getPaymentDetail(){
		
		String SPSql = 
				"Select " + PayTypeTable.TABLE_PAYTYPE + "."+PayTypeTable.COLUMN_PAYTYPE_NAME+ " ,sum( "
				+SumData_PaymentReportTable.TABLE_SUMDATA_PAYMENT_REPORT+ " . "
						+SumData_PaymentReportTable.COLUMN_TOTAL_PAY + " ) as TotalPay From "
				+ SumData_PaymentReportTable.TABLE_SUMDATA_PAYMENT_REPORT +" JOIN " +PayTypeTable.TABLE_PAYTYPE
				+ " USING " + " (" + PayTypeTable.COLUMN_PAYTYPE_ID + ") WHERE "+SumData_PaymentReportTable.COLUMN_SALE_DATE+" ='"+Saledate +"' GROUP BY " + PayTypeTable.COLUMN_PAYTYPE_NAME ;
		List<SumPaymentShop> getpayment = new ArrayList <SumPaymentShop>();
		Cursor cursor =  getReadableDatabase().rawQuery(SPSql, null);
		if (cursor.moveToFirst()){
			do{
				SumPaymentShop ps = new SumPaymentShop();
				ps.setPayTypeName(cursor.getString(cursor.getColumnIndex(PayTypeTable.COLUMN_PAYTYPE_NAME)));
				ps.setTotalPay(cursor.getDouble(cursor.getColumnIndex(SumData_PaymentReportTable.COLUMN_TOTAL_PAY)));
				getpayment.add(ps);
			}while(cursor.moveToNext());
		}
		return getpayment;
	}
	public SumPaymentShop getSumPaymentDetail(){
		String SPSql = "SELECT sum( "+SumData_PaymentReportTable.COLUMN_TOTAL_PAY+" ) as TotalPay from "+SumData_PaymentReportTable.TABLE_SUMDATA_PAYMENT_REPORT+" WHERE SaleDate ='"+Saledate+"'";
		Cursor cursor =  getReadableDatabase().rawQuery(SPSql, null);
		SumPaymentShop ps = new SumPaymentShop();
		if (cursor.moveToFirst()){
				ps.setTotalPay(cursor.getDouble(cursor.getColumnIndex(SumData_PaymentReportTable.COLUMN_TOTAL_PAY)));
		}
		return ps;
	}
	public List<SumPaymentShop> getPaymentGraph(){
		String SPSql = 
				"Select " + PayTypeTable.TABLE_PAYTYPE + "."+PayTypeTable.COLUMN_PAYTYPE_NAME+ " ,sum( "
				+SumData_PaymentReportTable.TABLE_SUMDATA_PAYMENT_REPORT+ " . "
						+SumData_PaymentReportTable.COLUMN_TOTAL_PAY + " ) as TotalPay From "
				+ SumData_PaymentReportTable.TABLE_SUMDATA_PAYMENT_REPORT +" JOIN " +PayTypeTable.TABLE_PAYTYPE
				+ " USING " + " (" + PayTypeTable.COLUMN_PAYTYPE_ID + ") WHERE "+SumData_PaymentReportTable.COLUMN_SALE_DATE+" ='"+Saledate +"' GROUP BY " + PayTypeTable.COLUMN_PAYTYPE_NAME ;
		List<SumPaymentShop> getpayment = new ArrayList <SumPaymentShop>();
		Cursor cursor =  getReadableDatabase().rawQuery(SPSql, null);
		if (cursor.moveToFirst()){
			do{
				SumPaymentShop ps = new SumPaymentShop();
				ps.setPayTypeName(cursor.getString(cursor.getColumnIndex(PayTypeTable.COLUMN_PAYTYPE_NAME)));
				ps.setTotalPay(cursor.getDouble(cursor.getColumnIndex(SumData_PaymentReportTable.COLUMN_TOTAL_PAY)));
				getpayment.add(ps);
			}while(cursor.moveToNext());
		}
		return getpayment;
	}
public List<SumPaymentShop> getSaleDatePaymentDetail(){
		
		String SPSql = 
				"Select * FROM "+SumData_PaymentReportTable.TABLE_SUMDATA_PAYMENT_REPORT+" WHERE "+SumData_PaymentReportTable.COLUMN_PAYTYPE_ID+" ='"+payTypeID+"'";
		List<SumPaymentShop> getpayment = new ArrayList <SumPaymentShop>();
		Cursor cursor =  getReadableDatabase().rawQuery(SPSql, null);
		if (cursor.moveToFirst()){
			do{
				SumPaymentShop ps = new SumPaymentShop();
				ps.setSaleDate(cursor.getString(cursor.getColumnIndex(SumData_PaymentReportTable.COLUMN_SALE_DATE)));
				ps.setTotalPay(cursor.getDouble(cursor.getColumnIndex(SumData_PaymentReportTable.COLUMN_TOTAL_PAY)));
				getpayment.add(ps);
			}while(cursor.moveToNext());
		}
		return getpayment;
	}
}
