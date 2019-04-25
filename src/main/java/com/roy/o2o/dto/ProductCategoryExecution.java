package com.roy.o2o.dto;

import java.util.List;

import com.roy.o2o.entity.ProductCategory;
import com.roy.o2o.enums.ProductCategoryStateEnum;

public class ProductCategoryExecution {
	//结果状态
	private int state;
	//状态标识
	private String stateInfo;
	//ProductCategory列表（查询商品类别列表的时候使用）
	private List<ProductCategory> productCategoryList;
	
	public ProductCategoryExecution() {
		
	}
	
	//商品类别操作失败的时候的构造器
	public ProductCategoryExecution(ProductCategoryStateEnum sateEnum) {
		this.state = sateEnum.getState();
		this.stateInfo = sateEnum.getStateInfo();
	}
	
	//商品类别操作成功的时候的构造器
	public ProductCategoryExecution(ProductCategoryStateEnum sateEnum,List<ProductCategory> productCategoryList) {
		this.state = sateEnum.getState();
		this.stateInfo = sateEnum.getStateInfo();
		this.productCategoryList = productCategoryList;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public List<ProductCategory> getProductCategoryList() {
		return productCategoryList;
	}

	public void setProductCategoryList(List<ProductCategory> productCategoryList) {
		this.productCategoryList = productCategoryList;
	}
}
