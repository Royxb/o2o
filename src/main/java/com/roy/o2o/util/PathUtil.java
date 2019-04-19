package com.roy.o2o.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PathUtil {
	private static Logger logger = LoggerFactory.getLogger(PathUtil.class);
	// 获取操作系统文件的分隔符
	private static String separator = System.getProperty("file.separator");

	public static String getImgBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			basePath = "D:/projectdev/image/";
		} else {
			basePath = "/home/roy/image/";
		}
		basePath = basePath.replace("/", separator);
		return basePath;
	}

	public static String getShopImagePath(Long shopId) {
		logger.info(separator);
		logger.info("getShopImagePath Start");
		String imagePath = "/upload/item/shop/" + shopId + "/";
		logger.info(imagePath.replace("/", separator));
		return imagePath.replace("/", separator);
	}

}
