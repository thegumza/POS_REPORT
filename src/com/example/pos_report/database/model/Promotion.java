package com.example.pos_report.database.model;

public class Promotion {
	
	int PromotionID;
	int TypeID;
	String PromotionName;
	public int getPromotionID() {
		return PromotionID;
	}
	public void setPromotionID(int promotionID) {
		PromotionID = promotionID;
	}
	public int getTypeID() {
		return TypeID;
	}
	public void setTypeID(int typeID) {
		TypeID = typeID;
	}
	public String getPromotionName() {
		return PromotionName;
	}
	public void setPromotionName(String promotionName) {
		PromotionName = promotionName;
	}
	
	
}
