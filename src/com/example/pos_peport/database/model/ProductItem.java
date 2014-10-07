package com.example.pos_peport.database.model;

public class ProductItem {

	   	  int ProductID ;
	      int ProductDeptID;
	      String ProductCode;
	      String ProductBarCode ;
	      String ProductName ;
	      int ProductTypeID;
	      String ProductUnitName ;
	      
		public int getProductID() {
			return ProductID;
		}
		public void setProductID(int productID) {
			ProductID = productID;
		}
		public int getProductDeptID() {
			return ProductDeptID;
		}
		public void setProductDeptID(int productDeptID) {
			ProductDeptID = productDeptID;
		}
		public String getProductCode() {
			return ProductCode;
		}
		public void setProductCode(String productCode) {
			ProductCode = productCode;
		}
		public String getProductBarCode() {
			return ProductBarCode;
		}
		public void setProductBarCode(String productBarCode) {
			ProductBarCode = productBarCode;
		}
		public String getProductName() {
			return ProductName;
		}
		public void setProductName(String productName) {
			ProductName = productName;
		}
		public int getProductTypeID() {
			return ProductTypeID;
		}
		public void setProductTypeID(int productTypeID) {
			ProductTypeID = productTypeID;
		}
		public String getProductUnitName() {
			return ProductUnitName;
		}
		public void setProductUnitName(String productUnitName) {
			ProductUnitName = productUnitName;
		}
	      
	      
}
