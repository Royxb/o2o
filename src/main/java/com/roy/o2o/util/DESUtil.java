package com.roy.o2o.util;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import sun.misc.BASE64Encoder;

public class DESUtil {

	private static Key key;
	//设置密钥Key
	private static String KEY_STR = "myKey";
	private static String CHARSETNAME = "UTF_8";
	private static String ALGORITHM = "DES";
	
	static {
		try {
			//生成DES算法对象
			KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
			//运用SHA1安全策略
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			//设置上密钥种子
			secureRandom.setSeed(KEY_STR.getBytes());
			//初始化基于SHA1的算法对象
			generator.init(secureRandom);
			//生成密钥对象
			key = generator.generateKey();
			generator = null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 获取加密后的信息
	 * 
	 * @param str
	 * @return
	 */
	public static String getEncryptString(String str) {
		//基于BASE64编码，接收byte[]并转换成String
		BASE64Encoder base64Encoder = new BASE64Encoder();
		try {
			//按UTF-8编码
			byte[] bytes = str.getBytes(CHARSETNAME);
			//获取加密对象
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			//初始化密码信息
			cipher.init(Cipher.ENCRYPT_MODE, key);
			//加密
			byte[] doFinal = cipher.doFinal(bytes);
			//byte[] to encode好的String并返回
			return base64Encoder.encode(doFinal);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}		
	}
}
