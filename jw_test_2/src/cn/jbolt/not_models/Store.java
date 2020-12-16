package cn.jbolt.not_models;

public class Store {

    private int id;
    private int sellerId;
	private double score;
	private int sale;
    private String name;
    private String storePhotoSrc;
    private String location;
    private double allowDelivery;
    private double deliveryCast;
    private String startDeliveryTime;
    private String endDeliveryTime;
    
	public Store(int id, int sellerId, double score, int sale, String name, String storePhotoSrc, String location,
			double allowDelivery, double deliveryCast, String startDeliveryTime, String endDeliveryTime) {
		super();
		this.id = id;
		this.sellerId = sellerId;
		this.score = score;
		this.sale = sale;
		this.name = name;
		this.storePhotoSrc = storePhotoSrc;
		this.location = location;
		this.allowDelivery = allowDelivery;
		this.deliveryCast = deliveryCast;
		this.startDeliveryTime = startDeliveryTime;
		this.endDeliveryTime = endDeliveryTime;
	}

	public Store() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public int getSale() {
		return sale;
	}

	public void setSale(int sale) {
		this.sale = sale;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStorePhotoSrc() {
		return storePhotoSrc;
	}

	public void setStorePhotoSrc(String storePhotoSrc) {
		this.storePhotoSrc = storePhotoSrc;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public String getStartDeliveryTime() {
		return startDeliveryTime;
	}

	public void setStartDeliveryTime(String startDeliveryTime) {
		this.startDeliveryTime = startDeliveryTime;
	}

	public String getEndDeliveryTime() {
		return endDeliveryTime;
	}

	public void setEndDeliveryTime(String endDeliveryTime) {
		this.endDeliveryTime = endDeliveryTime;
	}

	@Override
	public String toString() {
		return "Store [id=" + id + ", sellerId=" + sellerId + ", score=" + score + ", sale=" + sale + ", name=" + name
				+ ", storePhotoSrc=" + storePhotoSrc + ", location=" + location + ", allowDelivery=" + allowDelivery
				+ ", deliveryCast=" + deliveryCast + ", startDeliveryTime=" + startDeliveryTime + ", endDeliveryTime="
				+ endDeliveryTime + "]";
	}
    
}
