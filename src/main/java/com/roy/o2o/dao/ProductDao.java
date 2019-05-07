package com.roy.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.roy.o2o.entity.Product;

public interface ProductDao {

	/**
	 * 分页查询商品列表，可输入的条件有：商品名（模糊），商品状态，店铺Id，商品类别
	 * 
	 * @param productCondition
	 * @param beginIndex
	 * @param pageSize
	 * @return
	 */
	List<Product> queryProductList(@Param("productCondition") Product productCondition, @Param("rowIndex") int beginIndex,
			@Param("pageSize") int pageSize);

	/**
	 * 返回queryProductList总数
	 * @param ProductCondition
	 * @return
	 */
	int queryProductCount(@Param("productCondition") Product productCondition);
	
	/**
	 * 根据商品Id查询商品详情
	 * 
	 * @param ProductId
	 * @return Product
	 */
	Product queryByProductId(long productId);

	/**
	 * 插入商品
	 * 
	 * @param Product
	 * @return
	 */
	int insertProduct(Product product);

	/**
	 * 更新商品信息
	 * 
	 * @param Product
	 * @return
	 */
	int updateProduct(Product product);
	
	/**
	 * 删除商品类别时将商品记录中的类别项置空
	 * 
	 * @param productCategoryId
	 * @return
	 */
	int updateProductCategoryToNull(long productCategoryId);
}
