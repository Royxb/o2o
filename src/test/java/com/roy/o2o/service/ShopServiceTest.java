package com.roy.o2o.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.roy.o2o.BaseTest;
import com.roy.o2o.dto.ShopExecution;
import com.roy.o2o.entity.Area;
import com.roy.o2o.entity.PersonInfo;
import com.roy.o2o.entity.Shop;
import com.roy.o2o.entity.ShopCategory;
import com.roy.o2o.enums.ShopStateEnum;
import com.roy.o2o.exceptions.ShopOperationException;

public class ShopServiceTest extends BaseTest {
	@Autowired
	private ShopService shopService;

	@Test
	public void testModifyShop() throws ShopOperationException, FileNotFoundException {
		Shop shop = new Shop();
		shop.setShopId(1L);
		shop.setShopName("修改后的店铺名称");
		File shopImg = new File("C:/Users/Haier/Desktop/1.jpg");
		InputStream inputStream = new FileInputStream(shopImg);
		ShopExecution shopExecution = shopService.modifyShop(shop, inputStream, "1.jpg");
		System.out.println("新的图片地址为：" + shopExecution.getShop().getShopImg());
	}

	@Test
	@Ignore
	public void testAddShop() throws Exception {
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		owner.setUserId(1L);
		area.setAreaId(2);
		shopCategory.setShopCategoryId(1L);

		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setShopName("测试的店铺1");
		shop.setShopDesc("test1");
		shop.setShopAddr("test1");
		shop.setPhone("test1");
		shop.setCreateTime(new Date());
		shop.setLastEditTime(new Date());
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		shop.setAdvice("审核中");
		File shopImg = new File("C:/Users/DELL/Desktop/img26.jpg");
		InputStream inputStream = new FileInputStream(shopImg);
		ShopExecution shopExecution = shopService.addShop(shop, inputStream, shopImg.getName());
		assertEquals(ShopStateEnum.CHECK.getState(), shopExecution.getState());
	}
}
