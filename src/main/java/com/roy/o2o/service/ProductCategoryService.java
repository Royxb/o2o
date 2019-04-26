package com.roy.o2o.service;

import java.util.List;

import com.roy.o2o.dto.ProductCategoryExecution;
import com.roy.o2o.entity.ProductCategory;
import com.roy.o2o.exceptions.ProductCategoryOperationException;

public interface ProductCategoryService {

	/**
	 * 查询指定某个店铺下的所有商品类别信息
	 * 
	 * @param long shopId
	 * @return List<ProductCategory>
	 */
	List<ProductCategory> getProductCategorieList(long shopId);

	/**
	 * 批量添加商品类别信息
	 * 
	 * @param productCategoryList
	 * @return
	 * @throws ProductCategoryOperationException
	 */
	ProductCategoryExecution batchInsertProductCategory(List<ProductCategory> productCategoryList)
			throws ProductCategoryOperationException;

	/**
	 * 将此类别下的商品里的类别id置为空，在删除该商品类别
	 * 
	 * @param productCategoryId
	 * @param shopId
	 * @return
	 * @throws ProductCategoryOperationException
	 */
	ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
			throws ProductCategoryOperationException;
}
