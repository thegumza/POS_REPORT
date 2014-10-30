package com.example.pos_report.database;

import java.util.ArrayList;
import java.util.List;

import com.example.pos_report.SaleByDate;
import com.example.pos_report.database.model.SumProductShop;
import com.example.pos_report.database.model.TopProductShop;
import com.example.pos_report.database.table.ProductItemTable;
import com.example.pos_report.database.table.SumData_ProductReportTable;
import com.example.pos_report.database.table.SumData_TopProductReportTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


public class GetSumProductShopDao extends ReportDatabase{
	String Saledate = SaleByDate.getDate();
	public GetSumProductShopDao(Context context) {
		super(context);
	}
	
	public void insertSumProductShopData(List<SumProductShop> sps){
		// begin transaction
		getWritableDatabase().beginTransaction();
		try{
			// delete all old data
			getWritableDatabase().delete(SumData_ProductReportTable.TABLE_SUMDATA_PRODUCTREPORT, null, null);
			for(SumProductShop s : sps){
				ContentValues cv = new ContentValues();
				cv.put(SumData_ProductReportTable.COLUMN_SHOP_ID, s.getShopID());
				cv.put(SumData_ProductReportTable.COLUMN_SALE_DATE, s.getSaleDate());
				cv.put(SumData_ProductReportTable.COLUMN_PRODUCT_ID, s.getProductID());
				cv.put(SumData_ProductReportTable.COLUMN_SALE_MODE, s.getSaleMode());
				cv.put(SumData_ProductReportTable.COLUMN_QTY, s.getQty());
				cv.put(SumData_ProductReportTable.COLUMN_SALE_PRICE, s.getSalePrice());
				
				// insert
				getWritableDatabase().insert(SumData_ProductReportTable.TABLE_SUMDATA_PRODUCTREPORT, null, cv);
			}
			// if not have error do commit transaction
			getWritableDatabase().setTransactionSuccessful();
		}finally{
			getWritableDatabase().endTransaction();
		}
	}
	//Insert Data to List From SQLite Database
	public List<SumProductShop> getSumProduct(){
		List<SumProductShop> getsumproducttshop = new ArrayList <SumProductShop>();
		String SPSql = "Select * From "+ SumData_ProductReportTable.TABLE_SUMDATA_PRODUCTREPORT+" JOIN "+ProductItemTable.TABLE_PRODUCT_ITEM;
		Cursor cursor =  getReadableDatabase().rawQuery(SPSql, null);
		if (cursor.moveToFirst()){
			do{
				SumProductShop sp = new SumProductShop();
				sp.setShopID(cursor.getInt(cursor.getColumnIndex(SumData_ProductReportTable.COLUMN_SHOP_ID)));
				sp.setSaleDate(cursor.getString(cursor.getColumnIndex(SumData_ProductReportTable.COLUMN_SALE_DATE)));
				sp.setProductID(cursor.getInt(cursor.getColumnIndex(SumData_ProductReportTable.COLUMN_PRODUCT_ID)));
				sp.setSaleMode(cursor.getInt(cursor.getColumnIndex(SumData_ProductReportTable.COLUMN_SALE_MODE)));
				sp.setQty(cursor.getInt(cursor.getColumnIndex(SumData_ProductReportTable.COLUMN_QTY)));
				sp.setSalePrice(cursor.getInt(cursor.getColumnIndex(SumData_ProductReportTable.COLUMN_SALE_PRICE)));
				sp.setProductName(cursor.getString(cursor.getColumnIndex(ProductItemTable.COLUMN_PRODUCT_NAME)));
				getsumproducttshop.add(sp);
			}while(cursor.moveToNext());
		}
		return getsumproducttshop;
	}
	
	//Insert Data to List From SQLite Database
		public List<SumProductShop> getTopQtyProduct(){
			List<SumProductShop> getsumproducttshop = new ArrayList <SumProductShop>();
			String SPSql = "Select ProductItem."+ProductItemTable.COLUMN_PRODUCT_NAME +",Qty,SalePrice From "+ SumData_ProductReportTable.TABLE_SUMDATA_PRODUCTREPORT+" JOIN "+ProductItemTable.TABLE_PRODUCT_ITEM+" USING (ProductID) WHERE SaleDate='"+Saledate+"' ORDER BY Qty DESC";
			Cursor cursor =  getReadableDatabase().rawQuery(SPSql, null);
			if (cursor.moveToFirst()){
				do{
					SumProductShop sp = new SumProductShop();
					sp.setQty(cursor.getInt(cursor.getColumnIndex(SumData_ProductReportTable.COLUMN_QTY)));
					sp.setSalePrice(cursor.getInt(cursor.getColumnIndex(SumData_ProductReportTable.COLUMN_SALE_PRICE)));
					sp.setProductName(cursor.getString(cursor.getColumnIndex(ProductItemTable.COLUMN_PRODUCT_NAME)));
					getsumproducttshop.add(sp);
				}while(cursor.moveToNext());
			}
			return getsumproducttshop;
		}
		
		//Insert Data to List From SQLite Database
				public List<SumProductShop> getTopSaleProduct(){
					List<SumProductShop> getsumproducttshop = new ArrayList <SumProductShop>();
					String SPSql = "Select ProductItem."+ProductItemTable.COLUMN_PRODUCT_NAME +",Qty,SalePrice From "+ SumData_ProductReportTable.TABLE_SUMDATA_PRODUCTREPORT+" JOIN "+ProductItemTable.TABLE_PRODUCT_ITEM+" USING (ProductID) WHERE SaleDate='"+Saledate+"' ORDER BY SalePrice DESC";
					Cursor cursor =  getReadableDatabase().rawQuery(SPSql, null);
					if (cursor.moveToFirst()){
						do{
							SumProductShop sp = new SumProductShop();
							sp.setQty(cursor.getInt(cursor.getColumnIndex(SumData_ProductReportTable.COLUMN_QTY)));
							sp.setSalePrice(cursor.getInt(cursor.getColumnIndex(SumData_ProductReportTable.COLUMN_SALE_PRICE)));
							sp.setProductName(cursor.getString(cursor.getColumnIndex(ProductItemTable.COLUMN_PRODUCT_NAME)));
							getsumproducttshop.add(sp);
						}while(cursor.moveToNext());
					}
					return getsumproducttshop;
				}
	public SumProductShop getSumTopDetailProduct(){
		String SPSql = "Select sum(SalePrice) as SalePrice From "+ SumData_ProductReportTable.TABLE_SUMDATA_PRODUCTREPORT+" JOIN "+ProductItemTable.TABLE_PRODUCT_ITEM+" USING (ProductID) WHERE SaleDate='"+Saledate+"' ORDER BY SalePrice DESC";
	Cursor cursor =  getReadableDatabase().rawQuery(SPSql, null);
	SumProductShop ps = new SumProductShop();
	if (cursor.moveToFirst()){
			ps.setSalePrice(cursor.getDouble(cursor.getColumnIndex(SumData_ProductReportTable.COLUMN_SALE_PRICE)));
	}
	return ps;
	}
	
}
