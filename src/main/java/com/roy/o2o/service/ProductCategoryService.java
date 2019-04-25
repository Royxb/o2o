package com.roy.o2o.service;

import java.util.List;

import com.roy.o2o.entity.ProductCategory;

public interface ProductCategoryService {

	List<ProductCategory> getProductCategorieList(long shopId);

}
