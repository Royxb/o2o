package com.roy.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roy.o2o.dao.ShopCategoryDao;
import com.roy.o2o.entity.ShopCategory;
import com.roy.o2o.service.ShopCategoryService;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

	@Autowired
	private ShopCategoryDao shopCategoryDao;
	
	@Override
	public List<ShopCategory> getShopCategory(ShopCategory shopCategory) {
		
		return shopCategoryDao.queryShopCategory(shopCategory);
	}

}
