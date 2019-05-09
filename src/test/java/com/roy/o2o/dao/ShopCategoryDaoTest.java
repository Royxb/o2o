package com.roy.o2o.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.roy.o2o.BaseTest;
import com.roy.o2o.entity.ShopCategory;

public class ShopCategoryDaoTest extends BaseTest {

	@Autowired
	private ShopCategoryDao shopCategoryDao;

	@Test
	public void testQueryShopCategory() {
		List<ShopCategory> shopCategorieList = shopCategoryDao.queryShopCategory(null);
		System.out.println(shopCategorieList.size());
		
//		List<ShopCategory> shopCategorieList = shopCategoryDao.queryShopCategory(new ShopCategory());
//		assertEquals(2, shopCategorieList.size());
		
//		ShopCategory testCategory = new ShopCategory();
//		ShopCategory testParentCategory = new ShopCategory();
//		testParentCategory.setShopCategoryId(1L);;
//		testCategory.setParent(testParentCategory);
//		shopCategorieList = shopCategoryDao.queryShopCategory(testCategory);
//		assertEquals(1, shopCategorieList.size());
//		System.out.println(shopCategorieList.get(0).getShopCategoryName());
	}

}
