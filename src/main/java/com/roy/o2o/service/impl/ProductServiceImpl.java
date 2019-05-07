package com.roy.o2o.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.roy.o2o.dao.ProductDao;
import com.roy.o2o.dao.ProductImgDao;
import com.roy.o2o.dto.ImageHolder;
import com.roy.o2o.dto.ProductExecution;
import com.roy.o2o.entity.Product;
import com.roy.o2o.entity.ProductImg;
import com.roy.o2o.enums.ProductStateEnum;
import com.roy.o2o.exceptions.ProductOperationException;
import com.roy.o2o.service.ProductService;
import com.roy.o2o.util.ImageUtil;
import com.roy.o2o.util.PageCalculator;
import com.roy.o2o.util.PathUtil;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;

	@Autowired
	private ProductImgDao productImgDao;

	private static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Override
	@Transactional
	// 1.处理缩略图，获取缩略图相对路径并赋值给product
	// 2.往tb_product写入商品信息，获取productId
	// 3.结合productId批量处理商品详情图
	// 4.将商品详情图列表批量插入tb_product_img中
	public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList)
			throws ProductOperationException {
		// 空值判断
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			// 给商品设置上默认属性
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
			// 默认为上架状态
			product.setEnableStatus(1);
			// 若商品缩略图不为空则添加
			if (thumbnail != null) {
				addThumbnail(product, thumbnail);
			}
			try {
				// 创建商品信息
				int effectedNum = productDao.insertProduct(product);
				if (effectedNum <= 0) {
					logger.error("创建商品失败:");
					throw new ProductOperationException("创建商品失败:");
				}
			} catch (Exception e) {
				logger.error(e.toString());
				throw new ProductOperationException("创建商品失败:" + e.toString());
			}
			// 若商品详情图不为空则添加
			if (productImgHolderList != null && productImgHolderList.size() > 0) {
				addProductImgList(product, productImgHolderList);
			}
			return new ProductExecution(ProductStateEnum.SUCCESS, product);
		} else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}

	/**
	 * 查询商品列表并分页，可输入的条件有：商品名（模糊），商品状态，店铺Id，商品类别
	 * 
	 * @param productCondition 查询条件
	 * @param pageIndex        页码
	 * @param pageSize         每页条数
	 * @return
	 */
	@Override
	public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
		// 将页码转换为数据库的行数
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		// 获取商品列表分页信息
		List<Product> productList = productDao.productList(productCondition, rowIndex, pageSize);
		// 获取商品总数
		int productCount = productDao.queryProductCount(productCondition);
		// 构建返回对象,并设值
		ProductExecution productExecution = new ProductExecution();
		productExecution.setCount(productCount);
		productExecution.setProductList(productList);
		return productExecution;
	}

	/**
	 * 根据商品Id查询商品详情
	 * 
	 * @param productId 商品ID
	 * @return
	 */
	@Override
	public Product getProductById(long productId) {
		return productDao.queryByProductId(productId);
	}

	/**
	 * 修改商品信息以及图片处理
	 * 
	 * @param product        商品信息
	 * @param productImg     商品缩略图
	 * @param productImgList 商品图片列表
	 * @return
	 * @throws ProductOperationException
	 */
	@Override
	@Transactional
	// 1.若缩略图参数有值，则处理缩略图；若原先存在缩略图则先删除再添加新图，之后获取缩略图相对路径并赋值给product
	// 2.若商品详情图列表参数有值，对商品详情图片列表进行同样的操作
	// 3.将product_img下面的该商品原先的商品详情图记录全部清除
	// 4.更新tb_product_img以及tb_product的信息
	public ProductExecution modifyProduct(Product product, ImageHolder thumbnail,
			List<ImageHolder> productImgHolderList) throws ProductOperationException {
		// 空值判断
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			// 设置默认更新时间
			product.setLastEditTime(new Date());
			// 若商品缩略图不为空且原有缩略图不为空，则先删除原有缩略图并添加
			if (thumbnail != null && thumbnail.getImage() != null) {
				// 先获取原有信息,得到原有图片地址
				Product tempProduct = productDao.queryByProductId(product.getProductId());
				if (tempProduct.getImgAddr() != null) {
					ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
				}
				addThumbnail(product, thumbnail);
			}

			// 若存在新的商品详情图且原详情图不为空,则先删除原有详情图并添加
			if (productImgHolderList != null && productImgHolderList.size() > 0) {
				deleteProductImgHolderList(product.getProductId());
				addProductImgList(product, productImgHolderList);
			}

			// 更新商品信息
			try {
				int effectNum = productDao.updateProduct(product);
				if (effectNum <= 0) {
					throw new ProductOperationException("更新商品信息失败");
				}
				return new ProductExecution(ProductStateEnum.SUCCESS, product);
			} catch (ProductOperationException e) {
				throw new ProductOperationException("更新商品信息失败" + e.getMessage());
			}
		} else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}

	/**
	 * 批量添加图片
	 * 
	 * @param product
	 * @param productImgHolderList
	 * @throws ProductOperationException
	 */
	private void addProductImgList(Product product, List<ImageHolder> productImgHolderList)
			throws ProductOperationException {
		// 获取图片存储路径，这里直接存放到相应店铺的文件夹下
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		// 遍历图片一次处理，并添加进productImg实体里
		for (ImageHolder productImgHolder : productImgHolderList) {
			String imgAddr = ImageUtil.generateNormalImg(productImgHolder, dest);
			ProductImg productImg = new ProductImg();
			productImg.setImgAddr(imgAddr);
			productImg.setProductId(product.getProductId());
			productImg.setCreateTime(new Date());
			productImgList.add(productImg);
		}
		// 如果确实是有图片需要添加的，就执行批量添加操作
		if (productImgList.size() > 0) {
			try {
				int effectedNum = productImgDao.batchInsertProductImg(productImgList);
				if (effectedNum <= 0) {
					logger.info("创建商品详情图片失败");
					throw new ProductOperationException("创建商品详情图片失败");
				}
			} catch (Exception e) {
				logger.error(e.toString());
				throw new ProductOperationException("创建商品详情图片失败:" + e.toString());
			}
		}
	}

	/**
	 * 删除某个商品下的所有详情图
	 * 
	 * @param productId
	 */
	private void deleteProductImgHolderList(Long productId) {
		// 根据productId获取原有的图片
		List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
		if (productImgList != null && !productImgList.isEmpty()) {
			for (ProductImg productImg : productImgList) {
				// 删除存的图片
				ImageUtil.deleteFileOrPath(productImg.getImgAddr());
			}
			// 删除数据库中图片
			productImgDao.deleteProductImgByProductId(productId);
		}
	}

	/**
	 * 添加缩略图
	 * 
	 * @param product
	 * @param thumbnail
	 */
	private void addThumbnail(Product product, ImageHolder thumbnail) {
		logger.info("商品的ID:" + product.getShop().getShopId().toString());
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		logger.info("获取缩略图路径：" + dest);
		String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
		logger.info("获取缩略图相对路径：" + thumbnailAddr);
		product.setImgAddr(thumbnailAddr);
	}
}
