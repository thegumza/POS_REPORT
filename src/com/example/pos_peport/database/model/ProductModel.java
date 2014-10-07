package com.example.pos_peport.database.model;

import java.util.List;


public class ProductModel {

	 String productGroupCode;
	  public String productGroupName;
	 public String productDeptName;
	 public List<ProductNameModel> productNameModel;
	

	public String getProductGroupCode() {
		return productGroupCode;
	}
	public String getProductGroupName() {
		return productGroupName;
	}
	public String getProductDeptName() {
		return productDeptName;
	}
	public List<ProductNameModel> getProductNameModel() {
		return productNameModel;
	}
	public void setProductGroupCode(String productGroupCode) {
		this.productGroupCode = productGroupCode;
	}
	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}
	public void setProductDeptName(String productDeptName) {
		this.productDeptName = productDeptName;
	}
	public void setProductNameModel(List<ProductNameModel> productNameModel) {
		this.productNameModel = productNameModel;
	}

	public static class ProductNameModel{
		
		public String ProductName;
		public int SumAmount;
		public double SumSalePrice;
		public double TotalPercent;
		
		public String getProductName() {
			return ProductName;
		}
		public int getSumAmount() {
			return SumAmount;
		}
		public double getSumSalePrice() {
			return SumSalePrice;
		}
		public void setProductName(String productName) {
			ProductName = productName;
		}
		public void setSumAmount(int sumAmount) {
			SumAmount = sumAmount;
		}
		public void setSumSalePrice(double sumSalePrice) {
			SumSalePrice = sumSalePrice;
		}
		public double getTotalPercent() {
			return TotalPercent;
		}
		public void setTotalPercent(double totalPercent) {
			TotalPercent = totalPercent;
		}
		
	}

}

