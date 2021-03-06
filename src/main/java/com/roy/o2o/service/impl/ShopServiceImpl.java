package com.roy.o2o.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.roy.o2o.dao.ShopDao;
import com.roy.o2o.dto.ImageHolder;
import com.roy.o2o.dto.ShopExecution;
import com.roy.o2o.entity.Shop;
import com.roy.o2o.enums.ShopStateEnum;
import com.roy.o2o.exceptions.ShopOperationException;
import com.roy.o2o.service.ShopService;
import com.roy.o2o.util.ImageUtil;
import com.roy.o2o.util.PageCalculator;
import com.roy.o2o.util.PathUtil;

@Service
public class ShopServiceImpl implements ShopService {

	@Autowired
	private ShopDao shopDao;

	private static Logger logger = LoggerFactory.getLogger(ShopServiceImpl.class);


	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
		//pageIndex通过PageCalculator.calculateRowIndex()转换rowIndex值
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
		int shopCount = shopDao.queryShopCount(shopCondition);
		ShopExecution shopExecution = new ShopExecution();
		if (shopList != null) {
			shopExecution.setShopList(shopList);
			shopExecution.setCount(shopCount);
		} else {
			shopExecution.setState(ShopStateEnum.INNER_ERROR.getState());
		}
		return shopExecution;
	}
	
	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, ImageHolder thumbnail) {

		// 空值判断
		if (shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try {
			// 给店铺信息赋初始值
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			// 添加店铺信息
			int effectedNum = shopDao.insertShop(shop);

			if (effectedNum <= 0) {
				throw new ShopOperationException("店铺创建失败");
			} else {
				if (thumbnail.getImage() != null) {
					// 存储图片
					try {
						logger.info("Start ShopImg Add");
						addShopImg(shop, thumbnail);
						logger.info("End ShopImg Add");
					} catch (Exception e) {
						logger.error(e.getMessage());
						throw new ShopOperationException("addShopImg error:" + e.getMessage());
					}
					// 更新店铺的图片地址
					effectedNum = shopDao.updateShop(shop);
					if (effectedNum <= 0) {
						throw new ShopOperationException("更新图片地址失败");
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ShopOperationException("addShop error:" + e.getMessage());
		}
		return new ShopExecution(ShopStateEnum.CHECK, shop);
	}

	@Override
	public Shop getByShopId(long shopId) {
		return shopDao.queryByShopId(shopId);
	}

	@Override
	public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail)
			throws ShopOperationException {
		if (shop == null || shop.getShopId() == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		} else {
			try {
				//1.判断是否需要处理图片
				if (thumbnail.getImage() != null && thumbnail.getImageName() != null && !"".equals(thumbnail.getImageName())) {
					Shop tempShop = shopDao.queryByShopId(shop.getShopId());
					if (tempShop.getShopImg() != null) {
						ImageUtil.deleteFileOrPath(tempShop.getShopImg());
					}
					addShopImg(shop, thumbnail);
				}
				//2.更新店铺信息
				shop.setLastEditTime(new Date());
				int effectedNum = shopDao.updateShop(shop);
				if (effectedNum <= 0) {
					return new ShopExecution(ShopStateEnum.INNER_ERROR);
				} else {
					shop = shopDao.queryByShopId(shop.getShopId());
					return new ShopExecution(ShopStateEnum.SUCCESS,shop);
				}
			} catch (Exception e) {
				throw new ShopOperationException("modifyShop error:" + e.getMessage());
			}
		}
	}


	private void addShopImg(Shop shop, ImageHolder thumbnail) {
		logger.info("Start addShopImg Add");
		// 获取shop图片目录的相对值路径
		String dest = PathUtil.getShopImagePath(shop.getShopId());
		logger.info(dest);
		String shopImgAddr = ImageUtil.generateThumbnail(thumbnail, dest);
		shop.setShopImg(shopImgAddr);
	}

}
