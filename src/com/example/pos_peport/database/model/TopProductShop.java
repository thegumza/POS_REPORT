package com.example.pos_peport.database.model;

public class TopProductShop {
	
		String ProductGroupName;
		String ProductDeptName;
		String ProductName;
		int SumAmount;
		double SumSalePrice;
		int Qty;
		
		public String getProductGroupName() {
			return ProductGroupName;
		}
		public String getProductDeptName() {
			return ProductDeptName;
		}
		public String getProductName() {
			return ProductName;
		}
		public int getSumAmount() {
			return SumAmount;
		}
		public double getSumSalePrice() {
			return SumSalePrice;
		}
		public void setProductGroupName(String productGroupName) {
			ProductGroupName = productGroupName;
		}
		public void setProductDeptName(String productDeptName) {
			ProductDeptName = productDeptName;
		}
		public void setProductName(String productName) {
			ProductName = productName;
		}
		public void setSumAmount(int sumAmount) {
			SumAmount = sumAmount;
		}
		public void setSumSalePrice(double sumSalePrice) {
			SumSalePrice = sumSalePrice;
		}
		public int getQty() {
			return Qty;
		}
		public void setQty(int qty) {
			Qty = qty;
		}
		
		
}
