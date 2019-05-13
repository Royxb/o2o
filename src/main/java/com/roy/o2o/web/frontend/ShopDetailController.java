package com.roy.o2o.web.frontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.roy.o2o.dto.ProductExecution;
import com.roy.o2o.entity.Product;
import com.roy.o2o.entity.ProductCategory;
import com.roy.o2o.entity.Shop;
import com.roy.o2o.service.ProductCategoryService;
import com.roy.o2o.service.ProductService;
import com.roy.o2o.service.ShopService;
import com.roy.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class ShopDetailController {
	
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private ProductService productservice;
	
	@Autowired
	private ProductCategoryService productCategoryService;
	/**
	 * 获取店铺信息以及该店铺下面的商品类别列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/listshopdetailpageinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShopDetailPageInfo(HttpServletRequest request) {
		Map<String, Object> modleMap = new HashMap<String,Object>();
		//获取前台传过来的shopId
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		Shop shop = null;
		List<ProductCategory> productCategoryList = null;
		if (shopId != -1) {
			//获取店铺Id为shopId的店铺信息
			shop = shopService.getByShopId(shopId);
			//获取店铺下的商品类别列表
			productCategoryList = productCategoryService.getProductCategoryList(shopId);
			modleMap.put("shop", shop);
			modleMap.put("productCategoryList", productCategoryList);
			modleMap.put("success", true);
		} else {
			modleMap.put("success", false);
			modleMap.put("errMsg", "empty shopId");
		}
		return modleMap;
	}
	
	/**
	 * 依据查询条件分页列出该店铺下的所有商品
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/listproductsbyshop", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listProductsByShop(HttpServletRequest request) {
		Map<String, Object> modleMap = new HashMap<String,Object>();
		//获取页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		//获取一页需要显示的条数
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		//获取店铺ID
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		//空值判断
		if ((pageIndex > -1) && (pageSize > -1) && (shopId > -1)) {
			//获取商品类别ID
			long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
			//获取模糊查询的商品名
			String productName = HttpServletRequestUtil.getString(request, "productName");
			//组合查询条件
			Product productCondition = compactProductCondition4Search(shopId, productCategoryId,productName);
			//按照传入的查询条件以及分页信息返回相应商品列表以及总数
			ProductExecution pExecution = productservice.getProductList(productCondition, pageIndex, pageSize);
			modleMap.put("productList", pExecution.getProductList());
			modleMap.put("count", pExecution.getCount());
			modleMap.put("seccess", true);
		} else {
			modleMap.put("seccess", false);
			modleMap.put("errMsg", "empty pageSize or pageIndex or shopId");
		}		
		return modleMap;
	}

	/**
	 * 组合查询条件，并将条件封装到ProductCondition对象重新返回
	 * 
	 * @param shopId
	 * @param productCategoryId
	 * @param productName
	 * @return
	 */
	private Product compactProductCondition4Search(long shopId, long productCategoryId, String productName) {
		Product productCondition = new Product();
		Shop shop = new Shop();
		shop.setShopId(shopId);
		productCondition.setShop(shop);
		if (productCategoryId != -1L) {
			//查询某个商品类别下面的商品列表
			ProductCategory productCategory = new ProductCategory();
			productCategory.setProductCategoryId(productCategoryId);
			productCondition.setProductCategory(productCategory);
		}
		if (productName != null) {
			//查询名字里包含productName的商品列表
			productCondition.setProductName(productName);
		}
		//只允许选出状态为上架的商品
		productCondition.setEnableStatus(1);
		return productCondition;
	}
}
