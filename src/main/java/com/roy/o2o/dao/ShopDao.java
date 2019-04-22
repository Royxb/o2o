package com.roy.o2o.dao;

import com.roy.o2o.entity.Shop;

public interface ShopDao {
	/**
	 * 通过shopId查询shop
	 * @param ShopId
	 * @return Shop
	 */
	Shop queryByShopId(long shopId);
	/**
	 * 新增店铺
	 * @param shop
	 * @return
	 */
	int insertShop(Shop shop);
	
	/**
	 * 更新店铺
	 * @param shop
	 * @return
	 */
	int updateShop(Shop shop);
}
