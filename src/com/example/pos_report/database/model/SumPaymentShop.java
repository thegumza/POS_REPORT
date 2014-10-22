package com.example.pos_report.database.model;

public class SumPaymentShop {
	
int ShopID;
String SaleDate;
int PayTypeID;
String PayTypeName;

public String getPayTypeName() {
	return PayTypeName;
}
public void setPayTypeName(String payTypeName) {
	PayTypeName = payTypeName;
}
double TotalPay;

public int getShopID() {
	return ShopID;
}
public String getSaleDate() {
	return SaleDate;
}
public int getPayTypeID() {
	return PayTypeID;
}
public double getTotalPay() {
	return TotalPay;
}
public void setShopID(int shopID) {
	ShopID = shopID;
}
public void setSaleDate(String saleDate) {
	SaleDate = saleDate;
}
public void setPayTypeID(int payTypeID) {
	PayTypeID = payTypeID;
}
public void setTotalPay(double totalPay) {
	TotalPay = totalPay;
}

}
