package com.roy.o2o.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.roy.o2o.BaseTest;
import com.roy.o2o.entity.Product;
import com.roy.o2o.entity.ProductCategory;
import com.roy.o2o.entity.Shop;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest extends BaseTest {
	@Autowired
	private ProductDao productDao;

	@Test
	public void testAInsertProduct() {
		Shop shop = new Shop();
		shop.setShopId(1L);
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryId(1L);
		//初始化三个商品实例并添加进shopId为1的店铺里，同时商品类别Id为1
		Product product = new Product();
		product.setProductName("测试商品1");
		product.setProductDesc("测试商品1");
		product.setImgAddr("test1");
		product.setPriority(1);
		product.setCreateTime(new Date());
		product.setEnableStatus(1);
		product.setProductCategory(productCategory);
		product.setShop(shop);
		Product product2 = new Product();
		product2.setProductName("测试商品2");
		product2.setProductDesc("测试商品2");
		product2.setImgAddr("test2");
		product2.setPriority(1);
		product2.setCreateTime(new Date());
		product2.setEnableStatus(1);
		product2.setProductCategory(productCategory);
		product2.setShop(shop);
		int  effectedNum = productDao.insertProduct(product);
		assertEquals(1, effectedNum);
	}
	@Test
	public void testBProductList() {
		Product product = new Product();
		product.setProductId(1L);
		List<Product> productList = productDao.productList(product, 1, 99);
		System.out.println("productList：" + productList.size());
	}
	@Test
	public void testCQuerProductById() {
		long productId = 2;
		Product product = productDao.queryByProductId(productId);
		System.out.println("productImgSize：" + product.getProductImgList().size());
	}
	@Test
	public void testDUpdateProductById() throws Exception {
		Product product = new Product();
		ProductCategory productCategory = new ProductCategory();
		Shop shop = new Shop();
		shop.setShopId(1L);
		productCategory.setProductCategoryId(2L);
		product.setProductId(1L);
		product.setShop(shop);
		product.setProductName("佳能单反相机");
		product.setProductCategory(productCategory);
		//修改productId为1的商品名称
		//以及商品类别并校验影响的行数是否为1
		int effectedNum = productDao.updateProduct(product);
		assertEquals(1, effectedNum);
	}
	@Test
	public void testEUpdateProductCategoryToNull() {
		long productId = 2;
		Product product = productDao.queryByProductId(productId);
		System.out.println("productImgSize：" + product.getProductImgList().size());
	}
}