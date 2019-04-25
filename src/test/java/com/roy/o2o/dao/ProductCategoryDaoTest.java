package com.roy.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.roy.o2o.BaseTest;
import com.roy.o2o.entity.ProductCategory;

public class ProductCategoryDaoTest extends BaseTest {

	@Autowired
	private ProductCategoryDao productCategoryDao;

	@Test
	@Ignore
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
	
	@Test
	public void testBatchInsertProductCategory(){
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryName("商品类别1");
		productCategory.setPriority(1);
		productCategory.setCreateTime(new Date());
		productCategory.setShopId(1L);
		ProductCategory productCategory2 = new ProductCategory();
		productCategory2.setProductCategoryName("商品类别2");
		productCategory2.setPriority(2);
		productCategory2.setCreateTime(new Date());
		productCategory2.setShopId(1L);
		List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
		productCategoryList.add(productCategory);
		productCategoryList.add(productCategory2);
		int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
		assertEquals(2, effectedNum);
	}
}
