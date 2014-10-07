package com.example.pos_report.database;

import java.util.List;

import com.example.pos_peport.database.model.ProductDept;
import com.example.pos_report.database.table.ProductDeptTable;

import android.content.ContentValues;
import android.content.Context;


public class ProductDeptDao extends ReportDatabase{

	public ProductDeptDao(Context context) {
		super(context);
	}
	public void insertProductDeptData(List<ProductDept> pd){
		// begin transaction
		getWritableDatabase().beginTransaction();
		try{
			// delete all old data
			getWritableDatabase().delete(ProductDeptTable.TABLE_PRODUCT_DEPT, null, null);
			for(ProductDept g : pd){
				ContentValues cv = new ContentValues();
				cv.put(ProductDeptTable.COLUMN_PRODUCT_DEPT_ID, g.getProductDeptID());
				cv.put(ProductDeptTable.COLUMN_PRODUCT_GROUP_ID, g.getProductGroupID());
				cv.put(ProductDeptTable.COLUMN_PRODUCT_DEPT_CODE, g.getProductDeptCode());
				cv.put(ProductDeptTable.COLUMN_PRODUCT_DEPT_NAME, g.getProductDeptName());
				cv.put(ProductDeptTable.COLUMN_ORDERING, g.getOrdering());
				// insert
				getWritableDatabase().insert(ProductDeptTable.TABLE_PRODUCT_DEPT, null, cv);
			}
			// if not have error do commit transaction
			getWritableDatabase().setTransactionSuccessful();
		}finally{
			getWritableDatabase().endTransaction();
		}
	}
	
}



