package com.roy.o2o.service;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.roy.o2o.dto.ShopExecution;
import com.roy.o2o.entity.Shop;

/**
 * 
 * @author Haier
 *
 */
public interface ShopService {
	ShopExecution addShop(Shop shop,CommonsMultipartFile shopImg);
}
