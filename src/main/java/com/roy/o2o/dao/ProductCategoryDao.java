package com.roy.o2o.dao;

import java.util.List;

import com.roy.o2o.entity.ProductCategory;

public interface ProductCategoryDao {
	/**
	 * 通过shop id 查询店铺商品类别
	 * 
	 * @param long shopId
	 * @return List<ProductCategory>
	 */
	List<ProductCategory> queryProductCategoryList(long shopId);
	
	/**
	 * 批量新增商品类别
	 * 
	 * @param productCategorieList
	 * @return
	 */
	int batchInsertProductCategory(List<ProductCategory> productCategoryList);
}
