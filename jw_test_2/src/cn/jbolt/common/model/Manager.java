package cn.jbolt.common.model;

import cn.jbolt.common.model.base.BaseManager;

/**
 * 
 * Generated by JBolt.
 */
@SuppressWarnings("serial")
public class Manager extends BaseManager<Manager> {
	//建议将dao放在Service中只用作查询 
	public static final Manager dao = new Manager().dao();
	//在Service中声明 可直接复制过去使用
	//private Manager dao = new Manager().dao();  
}