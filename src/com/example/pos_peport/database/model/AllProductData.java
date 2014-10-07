package com.example.pos_peport.database.model;

import java.util.List;

public class AllProductData {
	
	private List<ProductGroup> ProductGroup;
	private List<ProductDept> ProductDept;
	private List<ProductItem> ProductItem;
	private List<Promotion> Promotion;
	//private List<SaleMode> SaleMode;
	
	
	
	public List<ProductGroup> getProductGroup() {
		return ProductGroup;
	}
	public void setProductGroup(List<ProductGroup> productGroup) {
		ProductGroup = productGroup;
	}
	public List<ProductDept> getProductDept() {
		return ProductDept;
	}
	public void setProductDept(List<ProductDept> productDept) {
		ProductDept = productDept;
	}
	public List<ProductItem> getProductItem() {
		return ProductItem;
	}
	public void setProductItem(List<ProductItem> productItem) {
		ProductItem = productItem;
	}
	//public List<SaleMode> getSaleMode() {
	//	return SaleMode;
	//}
	//public void setSaleMode(List<SaleMode> saleMode) {
	//	SaleMode = saleMode;
	//}
	public List<Promotion> getPromotion() {
		return Promotion;
	}
	public void setPromotion(List<Promotion> promotion) {
		Promotion = promotion;
	}
	
	
}
