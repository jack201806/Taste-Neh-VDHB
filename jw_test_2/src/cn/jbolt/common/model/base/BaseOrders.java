package cn.jbolt.common.model.base;
import cn.jbolt.base.JBoltBaseModel;

/**
 * 
 * Generated by JBolt, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseOrders<M extends BaseOrders<M>> extends JBoltBaseModel<M>{

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setOrderId(java.lang.Integer orderId) {
		set("order_id", orderId);
		return (M)this;
	}
	
	public java.lang.Integer getOrderId() {
		return getInt("order_id");
	}

	public M setProductId(java.lang.Integer productId) {
		set("product_id", productId);
		return (M)this;
	}
	
	public java.lang.Integer getProductId() {
		return getInt("product_id");
	}

	public M setUserId(java.lang.Integer userId) {
		set("user_id", userId);
		return (M)this;
	}
	
	public java.lang.Integer getUserId() {
		return getInt("user_id");
	}

	public M setProductNum(java.lang.Integer productNum) {
		set("product_num", productNum);
		return (M)this;
	}
	
	public java.lang.Integer getProductNum() {
		return getInt("product_num");
	}

}
