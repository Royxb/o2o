package com.roy.o2o.dao;

import java.util.List;

import com.roy.o2o.entity.ProductImg;

public interface ProductImgDao {
	/**
	 * 通过product id 查询店铺商品详情图片
	 * 
	 * @param long shopId
	 * @return List<ProductCategory>
	 */
	List<ProductImg> queryProductImgList(long productId);
	
	/**
	 * 批量添加商品详情图片
	 * 
	 * @param productImgList
	 * @return
	 */
	int batchInsertProductImg(List<ProductImg> productImgList);
	
	/**
	 * 删除指定商品详情图片
	 * 
	 * @param productId
	 * @return
	 */
	int deleteProductImgByProductId(long productId);
}
