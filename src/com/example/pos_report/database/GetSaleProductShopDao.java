package com.example.pos_report.database;

import java.util.ArrayList;
import java.util.List;

import com.example.pos_peport.database.model.ProductModel;
import com.example.pos_peport.database.model.ProductModel.ProductNameModel;
import com.example.pos_peport.database.model.SaleProductShop;
import com.example.pos_report.database.table.SaleData_ProductReportTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class GetSaleProductShopDao extends ReportDatabase{

	public GetSaleProductShopDao(Context context) {
		super(context);
	}
	
	public void insertSaleProductShopData(List<SaleProductShop> sps){
		// begin transaction
		getWritableDatabase().beginTransaction();
		try{
			// delete all old data
			getWritableDatabase().delete(SaleData_ProductReportTable.TABLE_SALE_DATA_PRODUCTREPORT, null, null);
			for(SaleProductShop s : sps){
				ContentValues cv = new ContentValues();
				cv.put(SaleData_ProductReportTable.COLUMN_PRODUCT_GROUP_CODE, s.getProductGroupCode());
				cv.put(SaleData_ProductReportTable.COLUMN_PRODUCT_GROUP_NAME, s.getProductGroupName());
				cv.put(SaleData_ProductReportTable.COLUMN_PRODUCT_DEPT_NAME, s.getProductDeptName());
				cv.put(SaleData_ProductReportTable.COLUMN_PRODUCT_NAME, s.getProductName());
				cv.put(SaleData_ProductReportTable.COLUMN_SUM_AMOUNT, s.getSumAmount());
				cv.put(SaleData_ProductReportTable.COLUMN_SUM_SALE_PRICE, s.getSumSalePrice());
				// insert
				getWritableDatabase().insert(SaleData_ProductReportTable.TABLE_SALE_DATA_PRODUCTREPORT, null, cv);
			}
			// if not have error do commit transaction
			getWritableDatabase().setTransactionSuccessful();
		}finally{
			getWritableDatabase().endTransaction();
		}
	}
	
	public List<ProductModel> getSaleProduct(){
		List<ProductModel> getproductgroup = null;
		String SPSql = "Select * From "+ SaleData_ProductReportTable.TABLE_SALE_DATA_PRODUCTREPORT+" GROUP BY "+SaleData_ProductReportTable.COLUMN_PRODUCT_DEPT_NAME+" ORDER BY "+SaleData_ProductReportTable.COLUMN_PRODUCT_GROUP_NAME;
		Cursor groupCursor =  getReadableDatabase().rawQuery(SPSql, null);
		if (groupCursor.moveToFirst()){
			getproductgroup = new ArrayList<ProductModel>();
			do{
				ProductModel sp = new ProductModel();
				String productgroupName = sp.productGroupName = groupCursor.getString(groupCursor.getColumnIndex(SaleData_ProductReportTable.COLUMN_PRODUCT_GROUP_NAME));
				String productdeptName = sp.productDeptName = groupCursor.getString(groupCursor.getColumnIndex(SaleData_ProductReportTable.COLUMN_PRODUCT_DEPT_NAME));
				
				String SPql = "Select * From "+ SaleData_ProductReportTable.TABLE_SALE_DATA_PRODUCTREPORT+" WHERE "
						+SaleData_ProductReportTable.COLUMN_PRODUCT_GROUP_NAME+" ='"+productgroupName+"' AND "
								+SaleData_ProductReportTable.COLUMN_PRODUCT_DEPT_NAME+"='"+productdeptName+"';";
				
				String SSql = "Select sum(SumAmount)as SumAmount, sum(SumSalePrice)as SumSalePrice From "+ SaleData_ProductReportTable.TABLE_SALE_DATA_PRODUCTREPORT+" WHERE "
						+SaleData_ProductReportTable.COLUMN_PRODUCT_GROUP_NAME+" ='"+productgroupName+"' AND "
								+SaleData_ProductReportTable.COLUMN_PRODUCT_DEPT_NAME+"='"+productdeptName+"';";
				
				Cursor itemCursor =  getReadableDatabase().rawQuery(SPql, null);
				Cursor sumCursor =  getReadableDatabase().rawQuery(SSql, null);
				
				if (itemCursor.moveToFirst()){
					sp.productNameModel  = new ArrayList <ProductNameModel>();
					do{
						ProductNameModel pn = new ProductNameModel();
						pn.ProductName = itemCursor.getString(itemCursor.getColumnIndex(SaleData_ProductReportTable.COLUMN_PRODUCT_NAME));
						pn.SumAmount = itemCursor.getInt(itemCursor.getColumnIndex(SaleData_ProductReportTable.COLUMN_SUM_AMOUNT));
						pn.SumSalePrice = itemCursor.getDouble(itemCursor.getColumnIndex(SaleData_ProductReportTable.COLUMN_SUM_SALE_PRICE));
						sp.productNameModel.add(pn);
						
					}while(itemCursor.moveToNext());
				}
				
				if(sumCursor.moveToFirst()){
					do{
						ProductNameModel pn = new ProductNameModel();
						pn.ProductName = "Summary";
						pn.SumAmount = sumCursor.getInt(sumCursor.getColumnIndex(SaleData_ProductReportTable.COLUMN_SUM_AMOUNT));
						pn.SumSalePrice = sumCursor.getDouble(sumCursor.getColumnIndex(SaleData_ProductReportTable.COLUMN_SUM_SALE_PRICE));
						sp.productNameModel.add(pn);
						
					}while(sumCursor.moveToNext());
				}
				itemCursor.close();
				sumCursor.close();
				getproductgroup.add(sp);
				
				
			}while(groupCursor.moveToNext());
		}
		groupCursor.close();
		return getproductgroup;
	}
	
		public SaleProductShop getSumProduct(){
			String SPSql = "SELECT sum( "+SaleData_ProductReportTable.COLUMN_SUM_AMOUNT+" ) as SumAmount,sum( "+SaleData_ProductReportTable.COLUMN_SUM_SALE_PRICE+" )as SumSalePrice from "+SaleData_ProductReportTable.TABLE_SALE_DATA_PRODUCTREPORT;
			Cursor cursor =  getReadableDatabase().rawQuery(SPSql, null);
			SaleProductShop ps = new SaleProductShop();
			if (cursor.moveToFirst()){
					ps.setSumAmount(cursor.getInt(cursor.getColumnIndex(SaleData_ProductReportTable.COLUMN_SUM_AMOUNT)));
					ps.setSumSalePrice(cursor.getDouble(cursor.getColumnIndex(SaleData_ProductReportTable.COLUMN_SUM_SALE_PRICE)));
			}
			return ps;
		}
		
		public List<SaleProductShop>  getSumProductGraph(){
			String GSql = "SELECT "+SaleData_ProductReportTable.COLUMN_PRODUCT_DEPT_NAME+",sum( "+SaleData_ProductReportTable.COLUMN_SUM_SALE_PRICE+" )as SumSalePrice FROM "+SaleData_ProductReportTable.TABLE_SALE_DATA_PRODUCTREPORT+" GROUP BY "+SaleData_ProductReportTable.COLUMN_PRODUCT_DEPT_NAME;
			List<SaleProductShop> getproductgraph = new ArrayList <SaleProductShop>();
			Cursor cursor =  getReadableDatabase().rawQuery(GSql, null);
			
			if (cursor.moveToFirst()){
			do{
					SaleProductShop ps = new SaleProductShop();
					ps.setProductDeptName(cursor.getString(cursor.getColumnIndex(SaleData_ProductReportTable.COLUMN_PRODUCT_DEPT_NAME)));
					ps.setSumSalePrice(cursor.getDouble(cursor.getColumnIndex(SaleData_ProductReportTable.COLUMN_SUM_SALE_PRICE)));
					getproductgraph.add(ps);
					
			}while(cursor.moveToNext());
			}
			
			return getproductgraph;
		}
			
	}
