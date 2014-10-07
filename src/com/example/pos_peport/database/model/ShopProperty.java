package com.example.pos_peport.database.model;

public class ShopProperty {
	
	private int ShopID;
	private String ShopCode;
	private String ShopName;
	private int ShopType;
	private int ShopGroupID;
	
	
	
    
	public ShopProperty() {
		// TODO Auto-generated constructor stub
	}


	public int getShopID() {
		return ShopID;
	}
	public String getShopCode() {
		return ShopCode;
	}
	public String getShopName() {
		return ShopName;
	}
	public int getShopType() {
		return ShopType;
	}
	public int getShopGroupID() {
		return ShopGroupID;
	}
	public void setShopID(int shopID) {
		ShopID = shopID;
	}
	public void setShopCode(String shopCode) {
		ShopCode = shopCode;
	}
	public void setShopName(String shopName) {
		ShopName = shopName;
	}
	public void setShopType(int shopType) {
		ShopType = shopType;
	}
	public void setShopGroupID(int shopGroupID) {
		ShopGroupID = shopGroupID;
	}
	
}
