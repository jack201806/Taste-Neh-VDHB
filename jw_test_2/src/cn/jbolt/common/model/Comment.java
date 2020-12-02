package cn.jbolt.common.model;

import cn.jbolt.common.model.base.BaseComment;

/**
 * 
 * Generated by JBolt.
 */
@SuppressWarnings("serial")
public class Comment extends BaseComment<Comment> {
	//建议将dao放在Service中只用作查询 
	public static final Comment dao = new Comment().dao();
	//在Service中声明 可直接复制过去使用
	//private Comment dao = new Comment().dao();  
}
