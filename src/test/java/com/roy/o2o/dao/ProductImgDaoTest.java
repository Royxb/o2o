package com.roy.o2o.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.roy.o2o.BaseTest;
import com.roy.o2o.entity.ProductImg;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest {
	@Autowired
	private ProductImgDao productImgDao;

	@Test
	public void testABatchInsertProductImg() {
		//productId为1的商品里添加两个详情图片记录
		ProductImg productImg = new ProductImg();
		productImg.setImgAddr("图片1");
		productImg.setImgDesc("测试图片1");
		productImg.setPriority(1);
		productImg.setCreateTime(new Date());
		productImg.setProductId(1L);
		ProductImg productImg2 = new ProductImg();
		productImg2.setImgAddr("图片2");
		productImg2.setImgDesc("测试图片2");
		productImg2.setPriority(1);
		productImg2.setCreateTime(new Date());
		productImg2.setProductId(1L);
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		productImgList.add(productImg);
		productImgList.add(productImg2);
		int effedtedNum = productImgDao.batchInsertProductImg(productImgList);
		assertEquals(2, effedtedNum);
	}
	@Test
	public void testBQueryProductImgList() {
		//productId为1的商品里添加两个详情图片记录
		List<ProductImg> productImgList = productImgDao.queryProductImgList(1L);
		assertEquals(4, productImgList.size());
	}
	@Test
	public void testCDeleteProductImgByProductId() {
		//productId为1的商品里添加两个详情图片记录
		long productId =1;
		int effedtedNum = productImgDao.deleteProductImgByProductId(productId);
		assertEquals(2, effedtedNum);
	}
}