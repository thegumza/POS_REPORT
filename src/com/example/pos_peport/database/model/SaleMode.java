package com.example.pos_peport.database.model;

public class SaleMode {

	int SaleModeID;
	String SaleModeName;
	int PositionPrefix;
	String PrefixText;	
	int PrefixQueue;
	
	public int getSaleModeID() {
		return SaleModeID;
	}
	public void setSaleModeID(int saleModeID) {
		SaleModeID = saleModeID;
	}
	public String getSaleModeName() {
		return SaleModeName;
	}
	public void setSaleModeName(String saleModeName) {
		SaleModeName = saleModeName;
	}
	public int getPositionPrefix() {
		return PositionPrefix;
	}
	public void setPositionPrefix(int positionPrefix) {
		PositionPrefix = positionPrefix;
	}
	public String getPrefixText() {
		return PrefixText;
	}
	public void setPrefixText(String prefixText) {
		PrefixText = prefixText;
	}
	public int getPrefixQueue() {
		return PrefixQueue;
	}
	public void setPrefixQueue(int prefixQueue) {
		PrefixQueue = prefixQueue;
	}
	
	
	
}
