package com.example.pos_report.database;

import com.example.pos_report.database.table.GlobalPropertyTable;
import com.example.pos_report.database.table.PayTypeTable;
import com.example.pos_report.database.table.ProductDeptTable;
import com.example.pos_report.database.table.ProductGroupTable;
import com.example.pos_report.database.table.ProductItemTable;
import com.example.pos_report.database.table.PromotionTable;
import com.example.pos_report.database.table.SaleModeTable;
import com.example.pos_report.database.table.ShopPropertyTable;
import com.example.pos_report.database.table.StaffsTable;
import com.example.pos_report.database.table.SumData_PaymentReportTable;
import com.example.pos_report.database.table.SumData_ProductReportTable;
import com.example.pos_report.database.table.SumData_PromotionReportTable;
import com.example.pos_report.database.table.SumData_TopProductReportTable;
import com.example.pos_report.database.table.SumData_TransReportTable;
import com.example.pos_report.database.table.SaleData_ProductReportTable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * @author j1tth4
 * 
 */
public class ReportDatabase{

	protected Context mContext;
	private ReportDatabaseHelper mHelper;
	
	public ReportDatabase(Context context){
		mContext = context;
		mHelper = ReportDatabaseHelper.getInstance(context); 
	}
	
	public Context getContext(){
		return mContext;
	}
	
	// open database for write
	public SQLiteDatabase getWritableDatabase(){
		return mHelper.getWritableDatabase();
	}
	
	// open database for read only
	public SQLiteDatabase getReadableDatabase(){
		return mHelper.getReadableDatabase();
	}
	
	public static class ReportDatabaseHelper extends SQLiteOpenHelper {
		
		/**
		 * Database name
		 */
		public static final String DB_NAME = "report.db";
		
		/**
		 * Database version
		 */
		private static final int DB_VERSION = 1;

		/**
		 * SQLiteOpenHelper instance
		 */
		private static ReportDatabaseHelper sHelper;

		/**
		 * @param context
		 * @return SQLiteOpenHelper instance This singleton pattern for only get
		 *         one SQLiteOpenHelper instance for thread save
		 */
		public static synchronized ReportDatabaseHelper getInstance(Context context) {
			if (sHelper == null) {
				sHelper = new ReportDatabaseHelper(context.getApplicationContext());
			}
			return sHelper;
		}

		private ReportDatabaseHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
				ShopPropertyTable.onCreate(db);
				GlobalPropertyTable.onCreate(db);
				PayTypeTable.oncreate(db);
				PromotionTable.onCreate(db);
				SumData_TransReportTable.onCreate(db);
				SumData_PaymentReportTable.onCreate(db);
				SumData_PromotionReportTable.onCreate(db);
				ProductItemTable.onCreate(db);
				ProductGroupTable.onCreate(db);
				ProductDeptTable.onCreate(db);
				SaleModeTable.onCreate(db);
				StaffsTable.onCreate(db);
				SumData_ProductReportTable.onCreate(db);
				SaleData_ProductReportTable.onCreate(db);
				SumData_TopProductReportTable.onCreate(db);
		}

		@Override
		public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
				ShopPropertyTable.onUpgrade(db, oldVersion, newVersion);
				GlobalPropertyTable.onUpgrade(db, oldVersion, newVersion);
				PayTypeTable.onUpgrade(db, oldVersion, newVersion);
				PromotionTable.onUpgrade(db, oldVersion, newVersion);
				SumData_TransReportTable.onUpgrade(db, oldVersion, newVersion);
				SumData_PaymentReportTable.onUpgrade(db, oldVersion, newVersion);
				SumData_PromotionReportTable.onUpgrade(db, oldVersion, newVersion);
				ProductItemTable.onUpgrade(db, oldVersion, newVersion);
				ProductGroupTable.onUpgrade(db, oldVersion, newVersion);
				ProductDeptTable.onUpgrade(db, oldVersion, newVersion);
				SaleModeTable.onUpgrade(db, oldVersion, newVersion);
				StaffsTable.onUpgrade(db, oldVersion, newVersion);
				SumData_ProductReportTable.onUpgrade(db, oldVersion, newVersion);
				SumData_TopProductReportTable.onUpgrade(db, oldVersion, newVersion);
				SumData_TopProductReportTable.onUpgrade(db, oldVersion, newVersion);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
				ShopPropertyTable.onUpgrade(db, oldVersion, newVersion);
				GlobalPropertyTable.onUpgrade(db, oldVersion, newVersion);
				PayTypeTable.onUpgrade(db, oldVersion, newVersion);
				PromotionTable.onUpgrade(db, oldVersion, newVersion);
				SumData_TransReportTable.onUpgrade(db, oldVersion, newVersion);
				SumData_PaymentReportTable.onUpgrade(db, oldVersion, newVersion);
				SumData_PromotionReportTable.onUpgrade(db, oldVersion, newVersion);
				ProductItemTable.onUpgrade(db, oldVersion, newVersion);
				ProductGroupTable.onUpgrade(db, oldVersion, newVersion);
				ProductDeptTable.onUpgrade(db, oldVersion, newVersion);
				SaleModeTable.onUpgrade(db, oldVersion, newVersion);
				StaffsTable.onUpgrade(db, oldVersion, newVersion);
				SumData_ProductReportTable.onUpgrade(db, oldVersion, newVersion);
				SumData_TopProductReportTable.onUpgrade(db, oldVersion, newVersion);
				SumData_TopProductReportTable.onUpgrade(db, oldVersion, newVersion);
		}
		/*public void dropAllTable() {
			SQLiteDatabase db = this.getWritableDatabase();

			db.execSQL("DROP TABLE IF EXISTS " + ShopPropertyTable.TABLE_SHOP);
			db.execSQL("DROP TABLE IF EXISTS " + GlobalPropertyTable.TABLE_GLOBAL_PROPERTY);
			db.execSQL("DROP TABLE IF EXISTS " + PayTypeTable.TABLE_PAYTYPE);
			db.execSQL("DROP TABLE IF EXISTS " + PromotionTable.TABLE_PROMOTION);
			db.execSQL("DROP TABLE IF EXISTS " + SumData_TransReportTable.TABLE_SUMDATA_TRANSPORTREPORT);
			db.execSQL("DROP TABLE IF EXISTS " + SumData_PaymentReportTable.TABLE_SUMDATA_PAYMENT_REPORT);
			db.execSQL("DROP TABLE IF EXISTS " + SumData_PromotionReportTable.TABLE_SUMDATA_PROMOTIONREPORT);
			db.execSQL("DROP TABLE IF EXISTS " + ProductItemTable.TABLE_PRODUCT_ITEM);
			db.execSQL("DROP TABLE IF EXISTS " + ProductGroupTable.TABLE_PRODUCT_GROUP);
			db.execSQL("DROP TABLE IF EXISTS " + ProductDeptTable.TABLE_PRODUCT_DEPT);
			db.execSQL("DROP TABLE IF EXISTS " + SaleModeTable.TABLE_SALEMODE);
			db.execSQL("DROP TABLE IF EXISTS " + StaffsTable.TABLE_STAFFS);
			db.execSQL("DROP TABLE IF EXISTS " + SumData_ProductReportTable.TABLE_SUMDATA_PRODUCTREPORT);
			db.execSQL("DROP TABLE IF EXISTS " + SaleData_ProductReportTable.TABLE_SALE_DATA_PRODUCTREPORT);
			db.execSQL("DROP TABLE IF EXISTS " + SumData_TopProductReportTable.TABLE_SUMDATA_TOP_PRODUCT_REPORT);
			onCreate(db);

		}*/
		
	}

	/*public void dropAllTable() {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getWritableDatabase();

		db.execSQL("DROP TABLE IF EXISTS " + ShopPropertyTable.TABLE_SHOP);
		db.execSQL("DROP TABLE IF EXISTS " + GlobalPropertyTable.TABLE_GLOBAL_PROPERTY);
		db.execSQL("DROP TABLE IF EXISTS " + PayTypeTable.TABLE_PAYTYPE);
		db.execSQL("DROP TABLE IF EXISTS " + PromotionTable.TABLE_PROMOTION);
		db.execSQL("DROP TABLE IF EXISTS " + SumData_TransReportTable.TABLE_SUMDATA_TRANSPORTREPORT);
		db.execSQL("DROP TABLE IF EXISTS " + SumData_PaymentReportTable.TABLE_SUMDATA_PAYMENT_REPORT);
		db.execSQL("DROP TABLE IF EXISTS " + SumData_PromotionReportTable.TABLE_SUMDATA_PROMOTIONREPORT);
		db.execSQL("DROP TABLE IF EXISTS " + ProductItemTable.TABLE_PRODUCT_ITEM);
		db.execSQL("DROP TABLE IF EXISTS " + ProductGroupTable.TABLE_PRODUCT_GROUP);
		db.execSQL("DROP TABLE IF EXISTS " + ProductDeptTable.TABLE_PRODUCT_DEPT);
		db.execSQL("DROP TABLE IF EXISTS " + SaleModeTable.TABLE_SALEMODE);
		db.execSQL("DROP TABLE IF EXISTS " + StaffsTable.TABLE_STAFFS);
		db.execSQL("DROP TABLE IF EXISTS " + SumData_ProductReportTable.TABLE_SUMDATA_PRODUCTREPORT);
		db.execSQL("DROP TABLE IF EXISTS " + SaleData_ProductReportTable.TABLE_SALE_DATA_PRODUCTREPORT);
		db.execSQL("DROP TABLE IF EXISTS " + SumData_TopProductReportTable.TABLE_SUMDATA_TOP_PRODUCT_REPORT);
		onCreate(db);
	}

	private void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		ShopPropertyTable.onCreate(db);
		GlobalPropertyTable.onCreate(db);
		PayTypeTable.oncreate(db);
		PromotionTable.onCreate(db);
		SumData_TransReportTable.onCreate(db);
		SumData_PaymentReportTable.onCreate(db);
		SumData_PromotionReportTable.onCreate(db);
		ProductItemTable.onCreate(db);
		ProductGroupTable.onCreate(db);
		ProductDeptTable.onCreate(db);
		SaleModeTable.onCreate(db);
		StaffsTable.onCreate(db);
		SumData_ProductReportTable.onCreate(db);
		SaleData_ProductReportTable.onCreate(db);
		SumData_TopProductReportTable.onCreate(db);
	}*/

	
	

}
