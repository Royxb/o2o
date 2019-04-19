package com.roy.o2o.util;

import javax.servlet.http.HttpServletRequest;

public class CodeUtil {
	public static boolean checkVerifyCode(HttpServletRequest request) {
		// 获取图片中的校验码
		String verifyCodeExpexted = (String) request.getSession()
				.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		// 获取输入的校验码
		String verifyCodeActual = HttpServletRequestUtil.getString(request, "verifyCodeActual");
		// 对比
		if (verifyCodeActual == null || !verifyCodeActual.equalsIgnoreCase(verifyCodeExpexted)) {
			return false;
		}
		return true;
	}
}
