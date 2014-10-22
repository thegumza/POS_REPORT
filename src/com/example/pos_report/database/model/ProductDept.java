package com.example.pos_report.database.model;

public class ProductDept {
	
	      int ProductDeptID;
	      int ProductGroupID ;
	      String ProductDeptCode ;
	      String ProductDeptName  ;
	      int Ordering ;
		public int getProductDeptID() {
			return ProductDeptID;
		}
		public int getProductGroupID() {
			return ProductGroupID;
		}
		public String getProductDeptCode() {
			return ProductDeptCode;
		}
		public String getProductDeptName() {
			return ProductDeptName;
		}
		public int getOrdering() {
			return Ordering;
		}
		public void setProductDeptID(int productDeptID) {
			ProductDeptID = productDeptID;
		}
		public void setProductGroupID(int productGroupID) {
			ProductGroupID = productGroupID;
		}
		public void setProductDeptCode(String productDeptCode) {
			ProductDeptCode = productDeptCode;
		}
		public void setProductDeptName(String productDeptName) {
			ProductDeptName = productDeptName;
		}
		public void setOrdering(int ordering) {
			Ordering = ordering;
		}
	      
		
	      
	      
}
