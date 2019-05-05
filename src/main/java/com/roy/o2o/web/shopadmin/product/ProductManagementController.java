package com.roy.o2o.web.shopadmin.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roy.o2o.dto.ImageHolder;
import com.roy.o2o.dto.ProductExecution;
import com.roy.o2o.entity.Product;
import com.roy.o2o.entity.Shop;
import com.roy.o2o.enums.ProductStateEnum;
import com.roy.o2o.exceptions.ProductOperationException;
import com.roy.o2o.service.ProductService;
import com.roy.o2o.util.CodeUtil;
import com.roy.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("shopadmin")
public class ProductManagementController {

	@Autowired
	private ProductService productService;

	// 支持上传商品详情图片的最大数量
	private static final int IMAGEMAXCOUNT = 6;

	@RequestMapping(value = "/addproduct", method = RequestMethod.POST)
	@ResponseBody
	/**
	 * 添加商品
	 * 
	 * 1. 验证码校验
	 * 
	 * 2. 接收前端参数：包括 商品、 商品缩略图、商品详情图片实体类
	 * 
	 * 前端页面通过post方式传递一个包含文件上传的Form会以multipart/form-data请求发送给服务器，
	 * 需要告诉DispatcherServlet如何处理MultipartRequest，我们在spring-web.xml中定义了multipartResolver。
	 * 
	 * 如果某个Request是一个MultipartRequest，它就会首先被MultipartResolver处理， 然后再转发相应的Controller。
	 * 在Controller中，
	 * 将HttpServletRequest转型为MultipartHttpServletRequest，可以非常方便的得到文件名和文件内容
	 *
	 * @param request
	 * @return
	 */
	public Map<String, Object> addProdcut(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 验证码校验
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		// 接受前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		try {
			String productStr = HttpServletRequestUtil.getString(request, "productStr");
			// 尝试获取前端传过来的表单string流并将其转换成Product实体类
			product = mapper.readValue(productStr, Product.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		//创建MultipartHttpServletRequest对象用来处理数据流
		MultipartHttpServletRequest multipartRequest = null;
		ImageHolder thumbnail = null;
		List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		try {
			// 若请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
			if (multipartResolver.isMultipart(request)) {
				multipartRequest = (MultipartHttpServletRequest) request;
				// 取出缩略图并构建ImageHolder对象
				CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
				thumbnail = new ImageHolder(thumbnailFile.getName(), thumbnailFile.getInputStream());
				// 取出详情图列表并构建List<ImageHolder>列表对象，最多支持六张图片上传
				for (int i = 0; i < IMAGEMAXCOUNT; i++) {
					CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest
							.getFile("productImg" + i);
					if (productImgFile != null) {
						// 若取出的第i个详情图片文件流不为空，则惊奇加入详情图列表
						ImageHolder productImg = new ImageHolder(productImgFile.getName(),
								productImgFile.getInputStream());
						productImgList.add(productImg);
					} else {
						// 若取出的第i个详情图片文件流为空，则终止循环
						break;
					}
				}
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "上传图片不能为空");
				return modelMap;
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		
		// 若Product信息、缩略图以及详情图列表不为空，则开始进行商品添加操作
		if (product != null && thumbnail != null && productImgList.size() > 0) {
			try {
				// 从session中获取当前店铺的Id并赋值给product，减少对前端数据的依赖
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				Shop shop = new Shop();
				shop.setShopId(currentShop.getShopId());
				product.setShop(shop);
				// 执行添加操作
				ProductExecution pExecution = productService.addProduct(product, thumbnail, productImgList);
				if (pExecution.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pExecution.getStateInfo());
				}
			} catch (ProductOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入商品信息");
		}
		return modelMap;
	}

	@RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modifyProdcut(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 验证码校验
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		// 接受前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		try {
			String productStr = HttpServletRequestUtil.getString(request, "productStr");
			// 尝试获取前端传过来的表单string流并将其转换成Product实体类
			product = mapper.readValue(productStr, Product.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		//创建MultipartHttpServletRequest对象用来处理数据流
		MultipartHttpServletRequest multipartRequest = null;
		ImageHolder thumbnail = null;
		List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		try {
			// 若请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
			if (multipartResolver.isMultipart(request)) {
				multipartRequest = (MultipartHttpServletRequest) request;
				// 取出缩略图并构建ImageHolder对象
				CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
				thumbnail = new ImageHolder(thumbnailFile.getName(), thumbnailFile.getInputStream());
				// 取出详情图列表并构建List<ImageHolder>列表对象，最多支持六张图片上传
				for (int i = 0; i < IMAGEMAXCOUNT; i++) {
					CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest
							.getFile("productImg" + i);
					if (productImgFile != null) {
						// 若取出的第i个详情图片文件流不为空，则惊奇加入详情图列表
						ImageHolder productImg = new ImageHolder(productImgFile.getName(),
								productImgFile.getInputStream());
						productImgList.add(productImg);
					} else {
						// 若取出的第i个详情图片文件流为空，则终止循环
						break;
					}
				}
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "上传图片不能为空");
				return modelMap;
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		
		// 若Product信息、缩略图以及详情图列表不为空，则开始进行商品添加操作
		if (product != null && thumbnail != null && productImgList.size() > 0) {
			try {
				// 从session中获取当前店铺的Id并赋值给product，减少对前端数据的依赖
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				Shop shop = new Shop();
				shop.setShopId(currentShop.getShopId());
				product.setShop(shop);
				// 执行添加操作
				ProductExecution pExecution = productService.addProduct(product, thumbnail, productImgList);
				if (pExecution.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pExecution.getStateInfo());
				}
			} catch (ProductOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入商品信息");
		}
		return modelMap;
	}
}
