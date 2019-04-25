package com.roy.o2o.web.shopadmin;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.roy.o2o.service.ProductCategoryService;
import com.roy.o2o.service.impl.AreaServiceImpl;
import com.roy.o2o.util.HttpServletRequestUtil;
import com.roy.o2o.dto.Result;
import com.roy.o2o.dto.ShopExecution;
import com.roy.o2o.entity.Area;
import com.roy.o2o.entity.PersonInfo;
import com.roy.o2o.entity.ProductCategory;
import com.roy.o2o.entity.Shop;
import com.roy.o2o.enums.ProductCategoryStateEnum;

@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryManagementController {
	
	private static Logger logger = LoggerFactory.getLogger(ProductCategoryManagementController.class);
	
	@Autowired
	private ProductCategoryService productCategoryService;
	
	@RequestMapping(value = "/getproductcategorylist", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getProductCategoryList(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Shop shop = new Shop();
		shop.setShopId(1L);
		request.getSession().setAttribute("currentShop",shop);
		
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		List<ProductCategory> productCategorieList = null;
		if (currentShop != null && currentShop.getShopId() > 0) {
			try {
				productCategorieList = productCategoryService.getProductCategorieList(currentShop.getShopId());
				modelMap.put("productCategorieList", productCategorieList);
				modelMap.put("success", true);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopId");
		}
		return modelMap;
	}
	
//	@RequestMapping(value = "/getproductcategorylist",method = RequestMethod.GET)
//	@ResponseBody
//	private Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request){
//		//To be removed
//		Shop shop = new Shop();
//		shop.setShopId(1L);
//		request.getSession().setAttribute("currentShop",shop);
//		
//		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
//		List<ProductCategory> productCategorieList = null;
//		if (currentShop != null && currentShop.getShopId() > 0) {
//			productCategorieList = productCategoryService.getProductCategorieList(1L);
//			logger.info(productCategorieList.get(0).getShopCategoryName());
//			logger.info(productCategorieList.get(0).getPriority().toString());
//			logger.info(productCategorieList.get(1).getShopCategoryName());
//			return new Result<List<ProductCategory>>(true,productCategorieList);
//		} else {
//			ProductCategoryStateEnum productCategoryStateEnum = ProductCategoryStateEnum.INNER_ERROR;
//			return new Result<List<ProductCategory>>(false,productCategoryStateEnum.getState(),productCategoryStateEnum.getStateInfo());
//		}
		
//	}
}
