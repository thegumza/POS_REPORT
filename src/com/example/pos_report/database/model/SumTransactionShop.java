package com.example.pos_report.database.model;



public class SumTransactionShop {

	int ShopID;
	String SaleDate;
	int TotalBill ;
	int TotalCust;
	double TransVAT;
	double RetailPrice;
	double Discount;
	double SalePrice;
	
	public int getShopID() {
		return ShopID;
	}
	public String getSaleDate() {
		return SaleDate;
	}
	public int getTotalBill() {
		return TotalBill;
	}
	public int getTotalCust() {
		return TotalCust;
	}
	public double getTransVAT() {
		return TransVAT;
	}
	public double getRetailPrice() {
		return RetailPrice;
	}
	public double getDiscount() {
		return Discount;
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
	public void setTotalBill(int totalBill) {
		TotalBill = totalBill;
	}
	public void setTotalCust(int totalCust) {
		TotalCust = totalCust;
	}
	public void setTransVAT(double transVAT) {
		TransVAT = transVAT;
	}
	public void setRetailPrice(double retailPrice) {
		RetailPrice = retailPrice;
	}
	public void setDiscount(double discount) {
		Discount = discount;
	}
	public void setSalePrice(double salePrice) {
		SalePrice = salePrice;
	}
	
}
