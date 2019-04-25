package com.roy.o2o.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.roy.o2o.BaseTest;
import com.roy.o2o.entity.ProductCategory;

public class ProductCategoryDaoTest extends BaseTest {

	@Autowired
	private ProductCategoryDao productCategoryDao;

	@Test
	public void testQueryByShopId() throws Exception {
		long shopId = 1;
		List<ProductCategory> productCategorieList = productCategoryDao.queryProductCategoryList(shopId);
		System.out.println("该店铺自定义类别数为:" + productCategorieList.size());
		for (ProductCategory productCategory : productCategorieList) {
			System.out.println(productCategory.getShopId());
			System.out.println(productCategory.getProductCategoryId());
			System.out.println(productCategory.getProductCategoryName());
			System.out.println(productCategory.getPriority());
			System.out.println(productCategory.getCreateTime());
		}
	}
}
