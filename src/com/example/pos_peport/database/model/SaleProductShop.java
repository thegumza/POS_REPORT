package com.example.pos_peport.database.model;


public class SaleProductShop {

	String ProductGroupCode;
	String ProductGroupName;
	String ProductDeptName;
	String ProductName;
	int SumAmount;
	double SumSalePrice;
	
	public String getProductGroupCode() {
		return ProductGroupCode;
	}
	public String getProductGroupName() {
		return ProductGroupName;
	}
	public String getProductDeptName() {
		return ProductDeptName;
	}
	public void setProductGroupCode(String productGroupCode) {
		ProductGroupCode = productGroupCode;
	}
	public void setProductGroupName(String productGroupName) {
		ProductGroupName = productGroupName;
	}
	public void setProductDeptName(String productDeptName) {
		ProductDeptName = productDeptName;
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
	public void setProductName(String productName) {
		ProductName = productName;
	}
	public void setSumAmount(int sumAmount) {
		SumAmount = sumAmount;
	}
	public void setSumSalePrice(double sumSalePrice) {
		SumSalePrice = sumSalePrice;
	}

	
	
	
}
