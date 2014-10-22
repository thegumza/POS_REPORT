package com.example.pos_report.database.model;

public class GlobalProperty {
	private String CurrencySymbol;
    private String CurrencyCode;
    private String CurrencyName;
    private String CurrencyFormat;
    private String DateFormat;
    private String TimeFormat;
    private String QtyFormat;
    private String PrefixTextTW;
    private int PositionPrefix;
	public String getCurrencySymbol() {
		return CurrencySymbol;
	}
	public String getCurrencyCode() {
		return CurrencyCode;
	}
	public String getCurrencyName() {
		return CurrencyName;
	}
	public String getCurrencyFormat() {
		return CurrencyFormat;
	}
	public String getDateFormat() {
		return DateFormat;
	}
	public String getTimeFormat() {
		return TimeFormat;
	}
	public String getQtyFormat() {
		return QtyFormat;
	}
	public String getPrefixTextTW() {
		return PrefixTextTW;
	}
	public int getPositionPrefix() {
		return PositionPrefix;
	}
	public void setCurrencySymbol(String currencySymbol) {
		CurrencySymbol = currencySymbol;
	}
	public void setCurrencyCode(String currencyCode) {
		CurrencyCode = currencyCode;
	}
	public void setCurrencyName(String currencyName) {
		CurrencyName = currencyName;
	}
	public void setCurrencyFormat(String currencyFormat) {
		CurrencyFormat = currencyFormat;
	}
	public void setDateFormat(String dateFormat) {
		DateFormat = dateFormat;
	}
	public void setTimeFormat(String timeFormat) {
		TimeFormat = timeFormat;
	}
	public void setQtyFormat(String qtyFormat) {
		QtyFormat = qtyFormat;
	}
	public void setPrefixTextTW(String prefixTextTW) {
		PrefixTextTW = prefixTextTW;
	}
	public void setPositionPrefix(int positionPrefix) {
		PositionPrefix = positionPrefix;
	}
	
}
