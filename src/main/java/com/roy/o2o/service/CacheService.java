package com.roy.o2o.service;

public interface CacheService {

	/**
	 * 依据key前缀删除匹配该模式下的所有key-value 
	 * 如：传入shopcategory则shopcategory_allfirstlevel等
	 * 以shopcategory开头的key_value都会被清空
	 * 
	 * @param keyPrefix
	 */
	void removeFromCache(String keyPrefix);
}
