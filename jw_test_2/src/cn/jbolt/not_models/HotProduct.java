package cn.jbolt.not_models;

public class HotProduct {
    private int shop_id;
    private int sale;
    private double price;
    private String name;
    private int id;
    private float stars;
    private String product_photo_src;
    private String store_name;
    
    public HotProduct() {
		super();
	}

	public HotProduct(int shop_id, int sale, double price, String name, int id, float stars,
			String product_photo_src, String store_name) {
		super();
		this.shop_id = shop_id;
		this.sale = sale;
		this.price = price;
		this.name = name;
		this.id = id;
		this.stars = stars;
		this.product_photo_src = product_photo_src;
		this.store_name = store_name;
	}

	public String getStoreName() {
        return store_name;
    }

    public void setStoreName(String store_name) {
        this.store_name = store_name;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public String getProduct_photo_src() {
        return product_photo_src;
    }

    public void setProduct_photo_src(String product_photo_src) {
        this.product_photo_src = product_photo_src;
    }


    @Override
    public String toString() {
        return "HotProduct{" +
                "shop_id='" + shop_id + '\'' +
                ", sale='" + sale + '\'' +
                ", price='" + price + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", stars=" + stars +
                ", product_photo_src='" + product_photo_src + '\'' +
                ", store_name='" + store_name + '\'' +
                '}';
    }
}
