package com.example.pos_report.database.model;

public class SumProductShop {
	
int ShopID;
String SaleDate;
int ProductID;
int SaleMode;
int Qty;
double SalePrice;
String ProductName;
int ProductGroupID;
String ProductGroupName;
int ProductDeptID;
String ProductDeptName;
int ProductTypeID;

public String getProductName() {
	return ProductName;
}
public void setProductName(String productName) {
	ProductName = productName;
}
public int getShopID() {
	return ShopID;
}
public String getSaleDate() {
	return SaleDate;
}
public int getProductID() {
	return ProductID;
}
public int getSaleMode() {
	return SaleMode;
}
public int getQty() {
	return Qty;
}
public double getSalePrice() {
	return SalePrice;
}
public void setShopID(int shopID) {
	ShopID = shopID;
}
public void setSaleDate(String saleDate) {
	SaleDate = saleDate;
}
public void setProductID(int productID) {
	ProductID = productID;
}
public void setSaleMode(int saleMode) {
	SaleMode = saleMode;
}
public void setQty(int qty) {
	Qty = qty;
}
public void setSalePrice(double salePrice) {
	SalePrice = salePrice;
}
public int getProductGroupID() {
	return ProductGroupID;
}
public String getProductGroupName() {
	return ProductGroupName;
}
public int getProductDeptID() {
	return ProductDeptID;
}
public String getProductDeptName() {
	return ProductDeptName;
}
public int getProductTypeID() {
	return ProductTypeID;
}
public void setProductGroupID(int productGroupID) {
	ProductGroupID = productGroupID;
}
public void setProductGroupName(String productGroupName) {
	ProductGroupName = productGroupName;
}
public void setProductDeptID(int productDeptID) {
	ProductDeptID = productDeptID;
}
public void setProductDeptName(String productDeptName) {
	ProductDeptName = productDeptName;
}
public void setProductTypeID(int productTypeID) {
	ProductTypeID = productTypeID;
}


}
