package com.example.pos_report.database.model;

public class SumPromotionShop {
int ShopID;
String SaleDate;
int PromotionID;
double Discount;
double LastPrice;
String PromotionName;
public int getShopID() {
	return ShopID;
}
public String getSaleDate() {
	return SaleDate;
}
public int getPromotionID() {
	return PromotionID;
}
public double getDiscount() {
	return Discount;
}
public double getLastPrice() {
	return LastPrice;
}
public void setShopID(int shopID) {
	ShopID = shopID;
}
public void setSaleDate(String saleDate) {
	SaleDate = saleDate;
}
public void setPromotionID(int promotionID) {
	PromotionID = promotionID;
}
public void setDiscount(double discount) {
	Discount = discount;
}
public void setLastPrice(double lastPrice) {
	LastPrice = lastPrice;
}
public String getPromotionName() {
	return PromotionName;
}
public void setPromotionName(String promotionName) {
	PromotionName = promotionName;
}


}
