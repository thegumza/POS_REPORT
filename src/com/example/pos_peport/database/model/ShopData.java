package com.example.pos_peport.database.model;

import java.util.List;

public class ShopData {
	
	private List<ShopProperty> ShopProperty;
	private List<GlobalProperty> GlobalProperty;
	private List<PayType> PayType;
	private List<Staffs> Staffs;
	
	public List<ShopProperty> getShopProperty() {
		return ShopProperty;
	}
	public List<GlobalProperty> getGlobalProperty() {
		return GlobalProperty;
	}
	public List<PayType> getPayType() {
		return PayType;
	}
	public List<Staffs> getStaffs() {
		return Staffs;
	}
	public void setShopProperty(List<ShopProperty> shopProperty) {
		ShopProperty = shopProperty;
	}
	public void setGlobalProperty(List<GlobalProperty> globalProperty) {
		GlobalProperty = globalProperty;
	}
	public void setPayType(List<PayType> payType) {
		PayType = payType;
	}
	public void setStaffs(List<Staffs> staffs) {
		Staffs = staffs;
	}
	
	
}
