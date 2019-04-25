package com.roy.o2o.web.shopadmin;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.roy.o2o.service.ProductCategoryService;
import com.roy.o2o.service.impl.AreaServiceImpl;
import com.roy.o2o.util.HttpServletRequestUtil;
import com.roy.o2o.dto.ProductCategoryExecution;
import com.roy.o2o.dto.Result;
import com.roy.o2o.dto.ShopExecution;
import com.roy.o2o.entity.Area;
import com.roy.o2o.entity.PersonInfo;
import com.roy.o2o.entity.ProductCategory;
import com.roy.o2o.entity.Shop;
import com.roy.o2o.enums.ProductCategoryStateEnum;
import com.roy.o2o.exceptions.ProductCategoryOperationException;

@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryManagementController {

	private static Logger logger = LoggerFactory.getLogger(ProductCategoryManagementController.class);

	@Autowired
	private ProductCategoryService productCategoryService;

//	@RequestMapping(value = "/getproductcategorylist", method = RequestMethod.GET)
//	@ResponseBody
//	private Map<String, Object> getProductCategoryList(HttpServletRequest request) {
//		Map<String, Object> modelMap = new HashMap<String, Object>();
//		Shop shop = new Shop();
//		shop.setShopId(1L);
//		request.getSession().setAttribute("currentShop",shop);
//		
//		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
//		List<ProductCategory> productCategorieList = null;
//		if (currentShop != null && currentShop.getShopId() > 0) {
//			try {
//				productCategorieList = productCategoryService.getProductCategorieList(currentShop.getShopId());
//				modelMap.put("productCategorieList", productCategorieList);
//				modelMap.put("success", true);
//			} catch (Exception e) {
//				modelMap.put("success", false);
//				modelMap.put("errMsg", e.toString());
//			}
//		} else {
//			modelMap.put("success", false);
//			modelMap.put("errMsg", "empty shopId");
//		}
//		return modelMap;
//	}
	/**
	 * 根据ShopId获取productCategory
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getproductcategorylist", method = RequestMethod.GET)
	@ResponseBody
	private Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request) {
		// To be removed
		Shop shop = new Shop();
		shop.setShopId(1L);
		request.getSession().setAttribute("currentShop", shop);
		// 在进入到shop管理页面（即调用getShopManageInfo方法时）,如果shopId合法，便将该shop信息放在了session中，key为currentShop
		// 这里我们不依赖前端的传入，因为不安全。 我们在后端通过session来做
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		List<ProductCategory> productCategorieList = null;
		if (currentShop != null && currentShop.getShopId() > 0) {
			productCategorieList = productCategoryService.getProductCategorieList(1L);
			logger.info(productCategorieList.get(0).getProductCategoryName());
			logger.info(productCategorieList.get(0).getPriority().toString());
			logger.info(productCategorieList.get(1).getProductCategoryName());
			return new Result<List<ProductCategory>>(true, productCategorieList);
		} else {
			ProductCategoryStateEnum productCategoryStateEnum = ProductCategoryStateEnum.INNER_ERROR;
			return new Result<List<ProductCategory>>(false, productCategoryStateEnum.getState(),
					productCategoryStateEnum.getStateInfo());
		}
	}
	
	/**
	 * 添加商铺目录 ，使用@RequestBody接收前端传递过来的productCategoryList
	 * 
	 * @param productCategoryList
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addproductcategorys", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList,
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 从session中获取店铺信息，尽量减少对前端的依赖
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		for (ProductCategory productCategory : productCategoryList) {
			productCategory.setShopId(currentShop.getShopId());
		}
		if (productCategoryList != null && productCategoryList.size() > 0) {
			try {
				// 批量插入
				ProductCategoryExecution productCategoryExecution = productCategoryService.batchInsertProductCategory(productCategoryList);
				if (productCategoryExecution.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", productCategoryExecution.getStateInfo());
				}				
			} catch (ProductCategoryOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请至少输入一个商品类别");
		}
		
		return modelMap;
	}
}
