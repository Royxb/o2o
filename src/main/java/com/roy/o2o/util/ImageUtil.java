package com.roy.o2o.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.roy.o2o.dto.ImageHolder;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {

	private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
	// 获取classpath的绝对值路径
	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	// 时间格式化的格式
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	// 随机数
	private static final Random r = new Random();

	/**
	 * 处理首页头图
	 * @param thumbnailInputStream Spring自带的文件处理对象
	 * @param fileName
	 * @param targetAddr 图片存储路径
	 * @return
	 */
	public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
		// 获取随机文件名，防止文件重名
		String realFileName = getRandomFileName();
		// 获取文件扩展名
		String extension = getFileExtension(thumbnail.getImageName());
		// 在文件夹不存在时创建
		makeDirPath(targetAddr);
		//获取文件存储的相对路径（带文件名）
		String relativeAddr = targetAddr + realFileName + extension;
		//获取文件要保存到的目录路径
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		//调用Thumbnails生产带有水印的图片
		try {
			logger.info(thumbnail.getImage().toString());
			logger.info(basePath);
			Thumbnails.of(thumbnail.getImage()).size(200, 200)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "watermark.png")), 0.25f)
					.outputQuality(0.8f).toFile(dest);
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		//返回图片相对路径地址
		return relativeAddr;
	}
	
	/**
	 * 处理详情图，并返回新生成图片的相对路径
	 * 
	 * @param productImgHolder
	 * @param dest
	 * @return
	 */
	public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) {
		// 获取不重复的随机名
		String realFileName = getRandomFileName();
		//获取文件的拓展名，如png、jpg等
		String extension = getFileExtension(thumbnail.getImageName());
		//如果目标路径不存在，则自动创建
		makeDirPath(targetAddr);
		//获取文件存储的相对路径（带文件名）
		String relativeAddr = targetAddr + realFileName + extension;
		logger.debug("current relativeAddr is:" + relativeAddr);
		//获取文件要保存到的目录路径
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		logger.debug("current complete ddr is:" + PathUtil.getImgBasePath() + relativeAddr);
		//调用Thumbnails生产带有水印的图片
		try {
			logger.info(thumbnail.getImage().toString());
			logger.info(basePath);
			Thumbnails.of(thumbnail.getImage()).size(337, 640)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "watermark.png")), 0.25f)
					.outputQuality(0.9f).toFile(dest);
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		//返回图片相对路径地址
		return relativeAddr;
	}

	/**
	 * 创建目标路径所涉及到的目录，即D:/projectdev/image/xxx.jpg 那么projectdev image这几个文件夹都得自动创建
	 * 
	 * @param targetAddr
	 */
	private static void makeDirPath(String targetAddr) {
		// 获取绝对路径
		String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}

	/**
	 * 获取输入文件流的拓展名
	 * 
	 * @param cFile
	 * @return
	 */
	private static String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	/**
	 * 生产随机文件名，当前年月日小时分钟秒钟+五位随机数
	 * 
	 * @return
	 */
	public static String getRandomFileName() {
		// 获取五位随机数
		int rannum = r.nextInt(89999) + 10000;
		String nowTimeStr = sDateFormat.format(new Date());
		return nowTimeStr + rannum;
	}

	public static void main(String[] args) throws IOException {
		Thumbnails.of(new File("C:/Users/DELL/Desktop/img26.jpg")).size(1920, 1200)
				.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "watermark.png")), 0.25f)
				.outputQuality(0.8f).toFile("C:/Users/DELL/Desktop/img26new.jpg");
	}

	/**
	 * 判断storePath是文件的路径还是目录路径，
	 * 如果storePath是文件路径则删除该文件，
	 * 如果storePath是目录路径则删除该目录下的所有文件，
	 * @param storePath
	 */
	public static void deleteFileOrPath(String storePath) {
		File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
		if(fileOrPath.exists()) {
			if (fileOrPath.isDirectory()) {
				File files[]= fileOrPath.listFiles();
				for (int i = 0; i < files.length; i++) {
					files[i].delete();
				}
			}
			fileOrPath.delete();
		}
	}
}
