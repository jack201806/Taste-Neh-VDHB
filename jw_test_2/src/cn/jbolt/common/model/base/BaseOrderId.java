package cn.jbolt.common.model.base;
import cn.jbolt.base.JBoltBaseModel;

/**
 * 
 * Generated by JBolt, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseOrderId<M extends BaseOrderId<M>> extends JBoltBaseModel<M>{

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setInfo(java.lang.String info) {
		set("info", info);
		return (M)this;
	}
	
	public java.lang.String getInfo() {
		return getStr("info");
	}

	public M setStatus(java.lang.String status) {
		set("status", status);
		return (M)this;
	}
	
	public java.lang.String getStatus() {
		return getStr("status");
	}

	public M setTime(java.util.Date time) {
		set("time", time);
		return (M)this;
	}
	
	public java.util.Date getTime() {
		return get("time");
	}

}