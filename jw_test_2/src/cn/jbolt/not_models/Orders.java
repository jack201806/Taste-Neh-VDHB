package cn.jbolt.not_models;

import java.util.List;

public class Orders {
	
	private int storeId;
    private String storeHeader;
    private String storeName;
    private String orderState;
    private List<UserProduct> products;
    private String orderId;
    private String orderTime;
//    private int orderNum;
//    private double orderAmount;
    
	public Orders(int storeId, String storeHeader, String storeName, String orderState, List<UserProduct> products,
			String orderId, String orderTime) {
		super();
		this.storeId = storeId;
		this.storeHeader = storeHeader;
		this.storeName = storeName;
		this.orderState = orderState;
		this.products = products;
		this.orderId = orderId;
		this.orderTime = orderTime;
//		this.orderNum = orderNum;
//		this.orderAmount = orderAmount;
	}

	public Orders() {
		super();
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getStoreHeader() {
		return storeHeader;
	}

	public void setStoreHeader(String storeHeader) {
		this.storeHeader = storeHeader;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public List<UserProduct> getProducts() {
		return products;
	}

	public void setProducts(List<UserProduct> products) {
		this.products = products;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	@Override
	public String toString() {
		return "Orders [storeId=" + storeId + ", storeHeader=" + storeHeader + ", storeName=" + storeName
				+ ", orderState=" + orderState + ", products=" + products + ", orderId=" + orderId + ", orderTime="
				+ orderTime + "]";
	}

}
