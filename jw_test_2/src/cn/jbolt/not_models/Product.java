package cn.jbolt.not_models;

public class Product {
	private int productId;
	private int shopId;
    private String productName;	
    private String title;
    private double productPrice;
    private int productSale;
    private String productImg;
    private String productIntro;
    
	public Product(int productId, int shopId, String productName, String title, double productPrice, int productSale,
			String productImg, String productIntro) {
		super();
		this.productId = productId;
		this.shopId = shopId;
		this.productName = productName;
		this.title = title;
		this.productPrice = productPrice;
		this.productSale = productSale;
		this.productImg = productImg;
		this.productIntro = productIntro;
	}

	public Product() {
		super();
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public int getProductSale() {
		return productSale;
	}

	public void setProductSale(int productSale) {
		this.productSale = productSale;
	}

	public String getProductImg() {
		return productImg;
	}

	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}

	public String getProductIntro() {
		return productIntro;
	}

	public void setProductIntro(String productIntro) {
		this.productIntro = productIntro;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", shopId=" + shopId + ", productName=" + productName + ", title="
				+ title + ", productPrice=" + productPrice + ", productSale=" + productSale + ", productImg="
				+ productImg + ", productIntro=" + productIntro + "]";
	}

}
