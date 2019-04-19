package com.roy.o2o.service;

import java.io.InputStream;

import com.roy.o2o.dto.ShopExecution;
import com.roy.o2o.entity.Shop;
import com.roy.o2o.exceptions.ShopOperationException;

/**
 * 
 * @author Haier
 *
 */
public interface ShopService {
	ShopExecution addShop(Shop shop,InputStream shopImgInputStream,String fileName) throws ShopOperationException;
}
