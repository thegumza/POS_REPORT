package com.example.pos_report.database;

import java.util.ArrayList;
import java.util.List;

import com.example.pos_peport.database.model.ProductGroup;
import com.example.pos_report.database.table.ProductGroupTable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


public class ProductGroupDao extends ReportDatabase{

	public ProductGroupDao(Context context) {
		super(context);
	}
	public void insertProductGroupData(List<ProductGroup> pi){
		// begin transaction
		getWritableDatabase().beginTransaction();
		try{
			// delete all old data
			getWritableDatabase().delete(ProductGroupTable.TABLE_PRODUCT_GROUP, null, null);
			for(ProductGroup g : pi){
				ContentValues cv = new ContentValues();
				cv.put(ProductGroupTable.COLUMN_PRODUCT_GROUP_ID, g.getProductGroupID());
				cv.put(ProductGroupTable.COLUMN_PRODUCT_GROUP_CODE, g.getProductGroupCode());
				cv.put(ProductGroupTable.COLUMN_PRODUCT_GROUP_NAME, g.getProductGroupName());
				cv.put(ProductGroupTable.COLUMN_PRODUCT_GROUP_TYPE, g.getProductGroupType());
				cv.put(ProductGroupTable.COLUMN_ORDERING, g.getOrdering());
				cv.put(ProductGroupTable.COLUMN_ISCOMMENT, g.getIsComment());
				cv.put(ProductGroupTable.COLUMN_SHOP_ID, g.getShopID());
				// insert
				getWritableDatabase().insert(ProductGroupTable.TABLE_PRODUCT_GROUP, null, cv);
			}
			// if not have error do commit transaction
			getWritableDatabase().setTransactionSuccessful();
		}finally{
			getWritableDatabase().endTransaction();
		}
	}
	//insert data to list
	public List<ProductGroup> getProductGroup(){
		List<ProductGroup> getproductgroup = new ArrayList <ProductGroup>();
		String PGSql = "Select * From "+ ProductGroupTable.TABLE_PRODUCT_GROUP+" WHERE "+ProductGroupTable.COLUMN_ISCOMMENT+" ='0'";
		Cursor cursor =  getReadableDatabase().rawQuery(PGSql, null);
		if (cursor.moveToFirst()){
			do{
				ProductGroup pg = new ProductGroup();
				pg.setProductGroupCode(cursor.getString(cursor.getColumnIndex(ProductGroupTable.COLUMN_PRODUCT_GROUP_CODE)));
				pg.setProductGroupName(cursor.getString(cursor.getColumnIndex(ProductGroupTable.COLUMN_PRODUCT_GROUP_NAME)));
				getproductgroup.add(pg);
			}while(cursor.moveToNext());
		}
		return getproductgroup;
		
	}
	
}



