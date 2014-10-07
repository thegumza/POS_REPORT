package com.example.pos_report.database;

import java.util.ArrayList;
import java.util.List;

import com.example.pos_peport.database.model.SumProductShop;
import com.example.pos_report.database.table.ProductDeptTable;
import com.example.pos_report.database.table.ProductGroupTable;
import com.example.pos_report.database.table.ProductItemTable;
import com.example.pos_report.database.table.SumData_ProductReportTable;
import com.example.pos_report.database.table.TempReportByProductTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


public class GetSumProductShopDao extends ReportDatabase{

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
				sp.setProductName(cursor.getString(cursor.getColumnIndex(SumData_ProductReportTable.COLUMN_PRODUCT_NAME)));
				getsumproducttshop.add(sp);
			}while(cursor.moveToNext());
		}
		return getsumproducttshop;
	}
	public List<SumProductShop> getTempProduct(){
		List<SumProductShop> gettempproducttshop = new ArrayList <SumProductShop>();
		String TempSql =
				"insert into "+TempReportByProductTable.TABLE_TEMP_REPORT_PRODUCT+" " +
				"select d."+ProductGroupTable.COLUMN_PRODUCT_GROUP_ID+", d."+ProductGroupTable.COLUMN_PRODUCT_GROUP_NAME+", " +
				"c."+ProductDeptTable.COLUMN_PRODUCT_DEPT_ID+", c."+ProductDeptTable.COLUMN_PRODUCT_DEPT_NAME+", " +
				"b."+ProductItemTable.COLUMN_PRODUCT_ID+", b."+ProductItemTable.COLUMN_PRODUCT_TYPE_ID+"," +
				"b."+ProductItemTable.COLUMN_PRODUCT_NAME+", sum(a."+SumData_ProductReportTable.COLUMN_QTY+")," +
				"sum(a."+SumData_ProductReportTable.COLUMN_SALE_PRICE+") " +
				" from "+SumData_ProductReportTable.TABLE_SUMDATA_PRODUCTREPORT+" a " +
				"left join "+ProductItemTable.TABLE_PRODUCT_ITEM+" b " +
				"on a."+SumData_ProductReportTable.COLUMN_PRODUCT_ID+"=b."+ProductItemTable.COLUMN_PRODUCT_ID+" " +
				"left join "+ProductDeptTable.TABLE_PRODUCT_DEPT+" c " +
				"on b."+ProductItemTable.COLUMN_PRODUCT_DEPT_ID+"=c."+ProductDeptTable.COLUMN_PRODUCT_DEPT_ID+" " +
				"left join "+ProductGroupTable.TABLE_PRODUCT_GROUP+" d " +
				"on c."+ProductDeptTable.COLUMN_PRODUCT_GROUP_ID+"=d."+ProductGroupTable.COLUMN_PRODUCT_GROUP_ID+" " +
				"group by a."+SumData_ProductReportTable.COLUMN_PRODUCT_ID+";";
		
		Cursor cursor =  getReadableDatabase().rawQuery(TempSql, null);
		SumProductShop sp = new SumProductShop();
		if (cursor.moveToFirst()){
			do{
			sp.setProductGroupID(cursor.getInt(cursor.getColumnIndex(TempReportByProductTable.COLUMN_PRODUCT_GROUP_ID)));
			sp.setProductGroupName(cursor.getString(cursor.getColumnIndex(TempReportByProductTable.COLUMN_PRODUCT_GROUP_NAME)));
			sp.setProductDeptID(cursor.getInt(cursor.getColumnIndex(TempReportByProductTable.COLUMN_PRODUCT_DEPT_ID)));
			sp.setProductDeptName(cursor.getString(cursor.getColumnIndex(TempReportByProductTable.COLUMN_PRODUCT_DEPT_NAME)));
			sp.setProductID(cursor.getInt(cursor.getColumnIndex(TempReportByProductTable.COLUMN_PRODUCT_ID)));
			sp.setProductTypeID(cursor.getInt(cursor.getColumnIndex(TempReportByProductTable.COLUMN_PRODUCT_TYPE_ID)));
			sp.setProductName(cursor.getString(cursor.getColumnIndex(TempReportByProductTable.COLUMN_PRODUCT_NAME)));
			sp.setQty(cursor.getInt(cursor.getColumnIndex(TempReportByProductTable.COLUMN_TOTAL_QTY)));
			sp.setSalePrice(cursor.getDouble(cursor.getColumnIndex(TempReportByProductTable.COLUMN_TOTAL_SALE_PRICE)));
			gettempproducttshop.add(sp);
			}while(cursor.moveToNext());
		}
		return gettempproducttshop;
	}
	
}
