package com.roy.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import javax.swing.filechooser.FileSystemView;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.roy.o2o.BaseTest;
import com.roy.o2o.dto.ImageHolder;
import com.roy.o2o.dto.ShopExecution;
import com.roy.o2o.entity.Area;
import com.roy.o2o.entity.PersonInfo;
import com.roy.o2o.entity.Shop;
import com.roy.o2o.entity.ShopCategory;
import com.roy.o2o.enums.ShopStateEnum;
import com.roy.o2o.exceptions.ShopOperationException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShopServiceTest extends BaseTest {
	@Autowired
	private ShopService shopService;
	
	//当前用户桌面
	File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
	String desktopPath = desktopDir.getAbsolutePath();
	
	@Test
	public void testGetShopList() {
		Shop shopCondition = new Shop();
		ShopCategory shopCategory = new ShopCategory();
		shopCategory.setShopCategoryId(2L);
		shopCondition.setShopCategory(shopCategory);
		ShopExecution shopExecution = shopService.getShopList(shopCondition, 2, 2);
		System.out.println("店铺列表数为：" + shopExecution.getShopList().size());
		System.out.println("店铺总数为：" + shopExecution.getCount());
	}

	@Test
	public void testModifyShop() throws ShopOperationException, FileNotFoundException {
		Shop shop = new Shop();
		shop.setShopId(1L);
		shop.setShopName("修改后的店铺名称");
		File shopImg = new File(desktopDir + "/1.jpg");
		InputStream inputStream = new FileInputStream(shopImg);
		ImageHolder imageHolder = new ImageHolder("1.jpg", inputStream);
		ShopExecution shopExecution = shopService.modifyShop(shop, imageHolder);
		System.out.println("新的图片地址为：" + shopExecution.getShop().getShopImg());
	}

	@Test
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
		File shopImg = new File(desktopDir + "/img26.jpg");
		InputStream inputStream = new FileInputStream(shopImg);
		ImageHolder imageHolder = new ImageHolder("img26.jpg", inputStream);
		ShopExecution shopExecution = shopService.addShop(shop, imageHolder);
		assertEquals(ShopStateEnum.CHECK.getState(), shopExecution.getState());
	}
}
