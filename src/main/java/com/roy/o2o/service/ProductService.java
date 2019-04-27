package com.roy.o2o.service;

import java.util.List;

import com.roy.o2o.dto.ImageHolder;
import com.roy.o2o.dto.ProductExecution;
import com.roy.o2o.entity.Product;
import com.roy.o2o.exceptions.ProductOperationException;

public interface ProductService {

	/**
	 * 添加商品信息以及图片处理
	 * 
	 * @param product
	 * @param thumbnail
	 * @param productImgs
	 * @return
	 * @throws ProductOperationException
	 */
	ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList)
			throws ProductOperationException;
	/*
	 * ProductExecution addProduct(Product product, InputStream thumbnail, String
	 * thumbnailName, List<InputStream> productImgList, List<String>
	 * productImgNameList) throws ProductOperationException;
	 */}
