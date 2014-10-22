package com.example.pos_report.database;

import java.util.ArrayList;
import java.util.List;

import com.example.pos_report.SaleByDate;
import com.example.pos_report.database.model.TopProductShop;
import com.example.pos_report.database.table.SumData_ProductReportTable;
import com.example.pos_report.database.table.SumData_TopProductReportTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class GetTopProductShopDao extends ReportDatabase{
	String Saledate = SaleByDate.getDate();
	public GetTopProductShopDao(Context context) {
		super(context);
	}
	
	public void insertTopProductShopData(List<TopProductShop> st){
		// begin transaction
		getWritableDatabase().beginTransaction();
		try{
			// delete all old data
			getWritableDatabase().delete(SumData_TopProductReportTable.TABLE_SUMDATA_TOP_PRODUCT_REPORT, null, null);
			for(TopProductShop t : st){
				ContentValues cv = new ContentValues();
				cv.put(SumData_TopProductReportTable.COLUMN_PRODUCT_GROUP_NAME, t.getProductGroupName());
				cv.put(SumData_TopProductReportTable.COLUMN_RODUCT_DEPT_NAME, t.getProductDeptName());
				cv.put(SumData_TopProductReportTable.COLUMN_PRODUCT_NAME, t.getProductName());
				cv.put(SumData_TopProductReportTable.COLUMN_SUM_AMOUNT, t.getSumAmount());
				cv.put(SumData_TopProductReportTable.COLUMN_SUM_SALE_PRICE, t.getSumSalePrice());
				
				// insert
				getWritableDatabase().insert(SumData_TopProductReportTable.TABLE_SUMDATA_TOP_PRODUCT_REPORT, null, cv);
			}
			// if not have error do commit transaction
			getWritableDatabase().setTransactionSuccessful();
		}finally{
			getWritableDatabase().endTransaction();
		}
	}
	public List<TopProductShop> getTopQtyProduct(){
		List<TopProductShop> gettopproducttshop = new ArrayList <TopProductShop>();
		String TPSql = "Select * From "+ SumData_TopProductReportTable.TABLE_SUMDATA_TOP_PRODUCT_REPORT+" JOIN "
		+SumData_ProductReportTable.TABLE_SUMDATA_PRODUCTREPORT+" GROUP BY "+SumData_TopProductReportTable.COLUMN_PRODUCT_NAME+" ORDER BY "+SumData_ProductReportTable.COLUMN_QTY+" DESC";
		Cursor cursor =  getReadableDatabase().rawQuery(TPSql, null);
		if (cursor.moveToFirst()){
			do{
				TopProductShop ts = new TopProductShop();
				ts.setProductGroupName(cursor.getString(cursor.getColumnIndex(SumData_TopProductReportTable.COLUMN_PRODUCT_GROUP_NAME)));
				ts.setProductDeptName(cursor.getString(cursor.getColumnIndex(SumData_TopProductReportTable.COLUMN_RODUCT_DEPT_NAME)));
				ts.setProductName(cursor.getString(cursor.getColumnIndex(SumData_TopProductReportTable.COLUMN_PRODUCT_NAME)));
				ts.setSumAmount(cursor.getInt(cursor.getColumnIndex(SumData_TopProductReportTable.COLUMN_SUM_AMOUNT)));
				ts.setSumSalePrice(cursor.getInt(cursor.getColumnIndex(SumData_TopProductReportTable.COLUMN_SUM_SALE_PRICE)));
				ts.setQty(cursor.getInt(cursor.getColumnIndex(SumData_ProductReportTable.COLUMN_QTY)));
				gettopproducttshop.add(ts);
			}while(cursor.moveToNext());
		}
		return gettopproducttshop;
	}
	public List<TopProductShop> getTopSaleProduct(){
		List<TopProductShop> gettopproducttshop = new ArrayList <TopProductShop>();
		String TPSql = "Select * From "+ SumData_TopProductReportTable.TABLE_SUMDATA_TOP_PRODUCT_REPORT+" JOIN "
		+SumData_ProductReportTable.TABLE_SUMDATA_PRODUCTREPORT+" GROUP BY "+SumData_TopProductReportTable.COLUMN_PRODUCT_NAME+" ORDER BY "+SumData_TopProductReportTable.COLUMN_SUM_SALE_PRICE+" DESC";
		Cursor cursor =  getReadableDatabase().rawQuery(TPSql, null);
		if (cursor.moveToFirst()){
			do{
				TopProductShop ts = new TopProductShop();
				ts.setProductGroupName(cursor.getString(cursor.getColumnIndex(SumData_TopProductReportTable.COLUMN_PRODUCT_GROUP_NAME)));
				ts.setProductDeptName(cursor.getString(cursor.getColumnIndex(SumData_TopProductReportTable.COLUMN_RODUCT_DEPT_NAME)));
				ts.setProductName(cursor.getString(cursor.getColumnIndex(SumData_TopProductReportTable.COLUMN_PRODUCT_NAME)));
				ts.setSumAmount(cursor.getInt(cursor.getColumnIndex(SumData_TopProductReportTable.COLUMN_SUM_AMOUNT)));
				ts.setSumSalePrice(cursor.getInt(cursor.getColumnIndex(SumData_TopProductReportTable.COLUMN_SUM_SALE_PRICE)));
				ts.setQty(cursor.getInt(cursor.getColumnIndex(SumData_ProductReportTable.COLUMN_QTY)));
				gettopproducttshop.add(ts);
			}while(cursor.moveToNext());
		}
		return gettopproducttshop;
	}
	// GetSumData for Saledate Product
	public List<TopProductShop> getTopSaleDateProduct(){
		List<TopProductShop> gettopproducttshop = new ArrayList <TopProductShop>();
		String TPSql = "Select * From "+ SumData_TopProductReportTable.TABLE_SUMDATA_TOP_PRODUCT_REPORT+" JOIN "
		+SumData_ProductReportTable.TABLE_SUMDATA_PRODUCTREPORT+" GROUP BY "+SumData_TopProductReportTable.COLUMN_PRODUCT_NAME;
		Cursor cursor =  getReadableDatabase().rawQuery(TPSql, null);
		if (cursor.moveToFirst()){
			do{
				TopProductShop ts = new TopProductShop();
				ts.setProductGroupName(cursor.getString(cursor.getColumnIndex(SumData_TopProductReportTable.COLUMN_PRODUCT_GROUP_NAME)));
				ts.setProductDeptName(cursor.getString(cursor.getColumnIndex(SumData_TopProductReportTable.COLUMN_RODUCT_DEPT_NAME)));
				ts.setProductName(cursor.getString(cursor.getColumnIndex(SumData_TopProductReportTable.COLUMN_PRODUCT_NAME)));
				ts.setSumAmount(cursor.getInt(cursor.getColumnIndex(SumData_TopProductReportTable.COLUMN_SUM_AMOUNT)));
				ts.setSumSalePrice(cursor.getInt(cursor.getColumnIndex(SumData_TopProductReportTable.COLUMN_SUM_SALE_PRICE)));
				ts.setQty(cursor.getInt(cursor.getColumnIndex(SumData_ProductReportTable.COLUMN_QTY)));
				gettopproducttshop.add(ts);
			}while(cursor.moveToNext());
		}
		return gettopproducttshop;
	}
	
}
