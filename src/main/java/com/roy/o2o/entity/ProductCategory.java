package com.roy.o2o.entity;

import java.util.Date;

/**
 * 商品类别类
 * @author Haier
 *
 */
public class ProductCategory {
	//商品类别ID
	private Long shopCategoryId;
	//商品ID
	private Long shopId;
	//商品类别名称
	private String shopCategoryName;
	//权重（优先级）
	private Integer priority;
	// 创建时间
	private Date createTime;
	public Long getShopCategoryId() {
		return shopCategoryId;
	}
	public void setShopCategoryId(Long shopCategoryId) {
		this.shopCategoryId = shopCategoryId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public String getShopCategoryName() {
		return shopCategoryName;
	}
	public void setShopCategoryName(String shopCategoryName) {
		this.shopCategoryName = shopCategoryName;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
