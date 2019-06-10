package com.roy.o2o.entity;

import java.util.Date;

/*
 * 顾客消费的商品映射
 */
public class ProductSellDaily {

	//哪一天的销量，精确到天
	private Date createTime;
	//销量
	private Integer toal;
	//商品信息实体类
	private Product product;
	//店铺信息实体类
	private Shop shop;
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getToal() {
		return toal;
	}
	public void setToal(Integer toal) {
		this.toal = toal;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
}
