package com.example.pos_report.database;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;

import com.example.pos_report.database.model.ProductItem;
import com.example.pos_report.database.table.ProductItemTable;

public class ProductItemDao extends ReportDatabase{

	public ProductItemDao(Context context) {
		super(context);
	}
	public void insertProductItemData(List<ProductItem> pi){
		// begin transaction
		getWritableDatabase().beginTransaction();
		try{
			// delete all old data
			getWritableDatabase().delete(ProductItemTable.TABLE_PRODUCT_ITEM, null, null);
			for(ProductItem i : pi){
				ContentValues cv = new ContentValues();
				cv.put(ProductItemTable.COLUMN_PRODUCT_ID, i.getProductID());
				cv.put(ProductItemTable.COLUMN_PRODUCT_DEPT_ID, i.getProductDeptID());
				cv.put(ProductItemTable.COLUMN_PRODUCT_CODE, i.getProductCode());
				cv.put(ProductItemTable.COLUMN_PRODUCT_BARCODE, i.getProductBarCode());
				cv.put(ProductItemTable.COLUMN_PRODUCT_NAME, i.getProductName());
				cv.put(ProductItemTable.COLUMN_PRODUCT_TYPE_ID, i.getProductTypeID());
				cv.put(ProductItemTable.COLUMN_PRODUCT_UNIT_NAME, i.getProductUnitName());
				// insert
				getWritableDatabase().insert(ProductItemTable.TABLE_PRODUCT_ITEM, null, cv);
			}
			// if not have error do commit transaction
			getWritableDatabase().setTransactionSuccessful();
		}finally{
			getWritableDatabase().endTransaction();
		}
	}
	
}



