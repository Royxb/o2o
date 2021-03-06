package com.roy.o2o.dto;

import java.util.List;

import com.roy.o2o.entity.Product;
import com.roy.o2o.enums.ProductStateEnum;

public class ProductExecution {
	//结果状态
	private int state;
	
	//状态标识
	private String stateInfo;
	
	//商品的数量
	private int count;
	
	//操作的product（增删改商品的时候使用）
	private Product product;
	
	//获取product列表（查询商品列表的时候使用）
	private List<Product> productList;
	
	public ProductExecution() {
		
	}
	
	//商品操作失败的时候的构造器
	public ProductExecution(ProductStateEnum sateEnum) {
		this.state = sateEnum.getState();
		this.stateInfo = sateEnum.getStateInfo();
	}
	
	//商品操作成功的时候的构造器
	public ProductExecution(ProductStateEnum sateEnum,Product product) {
		this.state = sateEnum.getState();
		this.stateInfo = sateEnum.getStateInfo();
		this.product = product;
	}
	
	//商品操作成功的时候的构造器
	public ProductExecution(ProductStateEnum sateEnum,List<Product> productList) {
		this.state = sateEnum.getState();
		this.stateInfo = sateEnum.getStateInfo();
		this.productList = productList;
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
}
