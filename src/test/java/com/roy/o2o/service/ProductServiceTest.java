package com.roy.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.filechooser.FileSystemView;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.roy.o2o.BaseTest;
import com.roy.o2o.dto.ImageHolder;
import com.roy.o2o.dto.ProductExecution;
import com.roy.o2o.entity.Product;
import com.roy.o2o.entity.ProductCategory;
import com.roy.o2o.entity.Shop;
import com.roy.o2o.enums.ProductStateEnum;
import com.roy.o2o.exceptions.ProductOperationException;

public class ProductServiceTest extends BaseTest {
	@Autowired
	private ProductService productService;

	// 当前用户桌面
	File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
	String desktopPath = desktopDir.getAbsolutePath()+"/";

	@Test
	public void testAddProduct() throws ProductOperationException, FileNotFoundException {
		// 创建shopId为1且productCategoryId为1的商品实例并给其成员变量赋值
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(1L);
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryId(3L);
		product.setShop(shop);
		product.setProductCategory(productCategory);
		product.setProductName("U盘");
		product.setProductDesc("U盘");
		product.setPriority(20);
		product.setCreateTime(new Date());
		product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
		// 创建缩略图文件流
		File thumbnailFile = new File(desktopPath + "单反相机.jpg");
		InputStream inputStream = new FileInputStream(thumbnailFile);
		ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), inputStream);
		//创建两个商品详情图文件流并将他们添加到详情图列表中
		File productImg = new File(desktopPath + "推广图.jpg");
		InputStream inputStream1 = new FileInputStream(productImg);
		File productImg2 = new File(desktopPath + "推广图.jpg");
		InputStream inputStream2 = new FileInputStream(productImg2);
		List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
		productImgList.add(new ImageHolder(productImg.getName(), inputStream1));
		productImgList.add(new ImageHolder(productImg2.getName(), inputStream2));
		//添加商品并验证
		ProductExecution productExecution = productService.addProduct(product, thumbnail, productImgList);
		assertEquals(ProductStateEnum.SUCCESS.getState(), productExecution.getState());
		
	}
}
