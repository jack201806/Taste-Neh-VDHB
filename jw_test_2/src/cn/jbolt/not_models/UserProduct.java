package cn.jbolt.not_models;

public class UserProduct {
	private int productId;
    private int storeId;
    private String productImg;
    private String productName;
    private double productPrice;
    private String productStandards;
    private int productNum;
    
	public UserProduct(int productId, int storeId, String productImg, String productName, double productPrice,
			String productStandards, int productNum) {
		super();
		this.productId = productId;
		this.storeId = storeId;
		this.productImg = productImg;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productStandards = productStandards;
		this.productNum = productNum;
	}
	
	public UserProduct() {
		super();
	}

	public int getProductId() {
		return productId;
	}
	
	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	public int getStoreId() {
		return storeId;
	}
	
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	
	public String getProductImg() {
		return productImg;
	}
	
	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public double getProductPrice() {
		return productPrice;
	}
	
	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
	
	public String getProductStandards() {
		return productStandards;
	}
	
	public void setProductStandards(String productStandards) {
		this.productStandards = productStandards;
	}
	
	public int getProductNum() {
		return productNum;
	}
	
	public void setProductNum(int productNum) {
		this.productNum = productNum;
	}
	
	@Override
	public String toString() {
		return "UserProduct [productId=" + productId + ", storeId=" + storeId + ", productImg=" + productImg
				+ ", productName=" + productName + ", productPrice=" + productPrice + ", productStandards="
				+ productStandards + ", productNum=" + productNum + "]";
	}

}
