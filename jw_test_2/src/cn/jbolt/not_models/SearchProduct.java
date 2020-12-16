package cn.jbolt.not_models;

public class SearchProduct {
	
	private int storeId;
    private String storePhoto;
    private String storeName;
    private double storeScore;
    private int storeSale;
    private double allowDelivery;
    private double deliveryCast;
    private String productName;
    private double productPrice;
    
	public SearchProduct(int storeId, String storePhoto, String storeName, double storeScore, int storeSale,
			double allowDelivery, double deliveryCast, String productName, double productPrice) {
		super();
		this.storeId = storeId;
		this.storePhoto = storePhoto;
		this.storeName = storeName;
		this.storeScore = storeScore;
		this.storeSale = storeSale;
		this.allowDelivery = allowDelivery;
		this.deliveryCast = deliveryCast;
		this.productName = productName;
		this.productPrice = productPrice;
	}

	public SearchProduct() {
		super();
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getStorePhoto() {
		return storePhoto;
	}

	public void setStorePhoto(String storePhoto) {
		this.storePhoto = storePhoto;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public double getStoreScore() {
		return storeScore;
	}

	public void setStoreScore(double storeScore) {
		this.storeScore = storeScore;
	}

	public int getStoreSale() {
		return storeSale;
	}

	public void setStoreSale(int storeSale) {
		this.storeSale = storeSale;
	}

	public double getAllowDelivery() {
		return allowDelivery;
	}

	public void setAllowDelivery(double allowDelivery) {
		this.allowDelivery = allowDelivery;
	}

	public double getDeliveryCast() {
		return deliveryCast;
	}

	public void setDeliveryCast(double deliveryCast) {
		this.deliveryCast = deliveryCast;
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

	@Override
	public String toString() {
		return "SearchProduct [storeId=" + storeId + ", storePhoto=" + storePhoto + ", storeName=" + storeName
				+ ", storeScore=" + storeScore + ", storeSale=" + storeSale + ", allowDelivery=" + allowDelivery
				+ ", deliveryCast=" + deliveryCast + ", productName=" + productName + ", productPrice=" + productPrice + "]";
	}

}
