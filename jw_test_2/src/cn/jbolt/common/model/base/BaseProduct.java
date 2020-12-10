package cn.jbolt.common.model.base;
import cn.jbolt.base.JBoltBaseModel;

/**
 * 
 * Generated by JBolt, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseProduct<M extends BaseProduct<M>> extends JBoltBaseModel<M>{

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setShopId(java.lang.Integer shopId) {
		set("shop_id", shopId);
		return (M)this;
	}
	
	public java.lang.Integer getShopId() {
		return getInt("shop_id");
	}

	public M setName(java.lang.String name) {
		set("name", name);
		return (M)this;
	}
	
	public java.lang.String getName() {
		return getStr("name");
	}

	public M setPrice(java.lang.String price) {
		set("price", price);
		return (M)this;
	}
	
	public java.lang.String getPrice() {
		return getStr("price");
	}

	public M setStandard(java.lang.String standard) {
		set("standard", standard);
		return (M)this;
	}
	
	public java.lang.String getStandard() {
		return getStr("standard");
	}

	public M setIntro(java.lang.String intro) {
		set("intro", intro);
		return (M)this;
	}
	
	public java.lang.String getIntro() {
		return getStr("intro");
	}

	public M setSale(java.lang.Integer sale) {
		set("sale", sale);
		return (M)this;
	}
	
	public java.lang.Integer getSale() {
		return getInt("sale");
	}

	public M setProductPhotoSrc(java.lang.String productPhotoSrc) {
		set("product_photo_src", productPhotoSrc);
		return (M)this;
	}
	
	public java.lang.String getProductPhotoSrc() {
		return getStr("product_photo_src");
	}

}
