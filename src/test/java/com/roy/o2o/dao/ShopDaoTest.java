package com.roy.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.roy.o2o.BaseTest;
import com.roy.o2o.entity.Area;
import com.roy.o2o.entity.PersonInfo;
import com.roy.o2o.entity.Shop;
import com.roy.o2o.entity.ShopCategory;

//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShopDaoTest extends BaseTest {
	@Autowired
	private ShopDao shopDao;

	@Test
	public void testQuerShopList() {
		Shop shopCondition = new Shop();
		PersonInfo owner = new PersonInfo();
		owner.setUserId(1L);
		shopCondition.setOwner(owner);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 5);
		int shopCount = shopDao.queryShopCount(shopCondition);
		System.out.println("店铺列表的大小：" + shopList.size());
		System.out.println("店铺总数：" + shopCount);
		ShopCategory shopCategory = new ShopCategory();
		shopCategory.setShopCategoryId(2L);
		shopCondition.setShopCategory(shopCategory);
		shopList = shopDao.queryShopList(shopCondition, 0, 2);
		System.out.println("新店铺列表的大小：" + shopList.size());
		shopCount = shopDao.queryShopCount(shopCondition);
		System.out.println("新店铺总数：" + shopCount);
		
	}
	
	@Test
	@Ignore
	public void testQueryByShopId() {
		long shopId = 1;
		Shop shop = shopDao.queryByShopId(shopId);
		System.out.println("areaId:" + shop.getArea().getAreaId());
		System.out.println("areaName:" + shop.getArea().getAreaName());
	}
	
	@Test
	public void testInsertShop() throws Exception {
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
		shop.setShopName("测试的店铺");
		shop.setShopDesc("test");
		shop.setShopAddr("test");
		shop.setPhone("test");
		shop.setShopImg("test");
		shop.setPriority(1);
		shop.setCreateTime(new Date());
		shop.setLastEditTime(new Date());
		shop.setEnableStatus(1);
		shop.setAdvice("审核中");
		int effectedNum = shopDao.insertShop(shop);
		assertEquals(1, effectedNum);
	}
	
	
	@Test
	@Ignore
	public void testUpdateShop() throws Exception {
		Shop shop = new Shop();
		shop.setShopId(1L);
		shop.setShopDesc("测试描述");
		shop.setShopAddr("测试地址");
		shop.setLastEditTime(new Date());
		int effectedNum = shopDao.updateShop(shop);
		assertEquals(1, effectedNum);
	}
}