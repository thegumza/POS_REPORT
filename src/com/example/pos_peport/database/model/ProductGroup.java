package com.example.pos_peport.database.model;

public class ProductGroup {
	
	   	  int ProductGroupID;
	      String ProductGroupCode; 
	      String ProductGroupName;
	      int ProductGroupType;
	      int Ordering;
	      int IsComment;
	      int ShopID;
	      
		public int getProductGroupID() {
			return ProductGroupID;
		}
		public void setProductGroupID(int productGroupID) {
			ProductGroupID = productGroupID;
		}
		public String getProductGroupCode() {
			return ProductGroupCode;
		}
		public void setProductGroupCode(String productGroupCode) {
			ProductGroupCode = productGroupCode;
		}
		public String getProductGroupName() {
			return ProductGroupName;
		}
		public void setProductGroupName(String productGroupName) {
			ProductGroupName = productGroupName;
		}
		public int getProductGroupType() {
			return ProductGroupType;
		}
		public void setProductGroupType(int productGroupType) {
			ProductGroupType = productGroupType;
		}
		public int getOrdering() {
			return Ordering;
		}
		public void setOrdering(int ordering) {
			Ordering = ordering;
		}
		public int getIsComment() {
			return IsComment;
		}
		public void setIsComment(int isComment) {
			IsComment = isComment;
		}
		public int getShopID() {
			return ShopID;
		}
		public void setShopID(int shopID) {
			ShopID = shopID;
		}
	      
}
