package com.example.pos_report.database.model;

public class PayType {
	
int PayTypeID;
String PayTypeCode;
String PayTypeName;
int Ordering;

public int getPayTypeID() {
	return PayTypeID;
}
public String getPayTypeCode() {
	return PayTypeCode;
}
public String getPayTypeName() {
	return PayTypeName;
}
public int getOrdering() {
	return Ordering;
}
public void setPayTypeID(int payTypeID) {
	PayTypeID = payTypeID;
}
public void setPayTypeCode(String payTypeCode) {
	PayTypeCode = payTypeCode;
}
public void setPayTypeName(String payTypeName) {
	PayTypeName = payTypeName;
}
public void setOrdering(int ordering) {
	Ordering = ordering;
}



}
