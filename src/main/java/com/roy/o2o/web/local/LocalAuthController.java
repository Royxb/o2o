package com.roy.o2o.web.local;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.roy.o2o.dto.LocalAuthExecution;
import com.roy.o2o.entity.LocalAuth;
import com.roy.o2o.entity.PersonInfo;
import com.roy.o2o.enums.LocalAuthStateEnum;
import com.roy.o2o.service.LocalAuthService;
import com.roy.o2o.util.CodeUtil;
import com.roy.o2o.util.HttpServletRequestUtil;

@Controller
public class LocalAuthController {

	@Autowired
	private LocalAuthService localAuthService;

	/**
	 * 将用户信息与平台账号绑定
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bindlocalauth", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> bindLocalAuth(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 验证码验证
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		// 获取输入的账号
		String username = HttpServletRequestUtil.getString(request, "username");
		// 获取输入的密码
		String password = HttpServletRequestUtil.getString(request, "password");
		// 从session中获取当前用户信息(用户一旦通过微信登录后,使能获得到用户信息)
		PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
		// 非空判断,要求账号和密码以及当前的用户session非空
		if (username != null && password != null && user != null && user.getUserId() != null) {
			// 创建LocalAuth对象并赋值
			LocalAuth localAuth = new LocalAuth();
			localAuth.setUsername(username);
			localAuth.setPassword(password);
			localAuth.setPersonInfo(user);
			// 绑定账号
			LocalAuthExecution lae = localAuthService.bindLocalAuth(localAuth);
			if (lae.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
				modelMap.put("success", true);
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", lae.getStateInfo());
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户名和密码不能为空!");
		}
		return modelMap;
	}

	/**
	 * 修改密码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/changelocalpwd", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> changeLocalPwd(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 验证码验证
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		// 获取账号
		String username = HttpServletRequestUtil.getString(request, "username");
		// 获取原密码
		String password = HttpServletRequestUtil.getString(request, "password");
		// 获取新原密码
		String newPassword = HttpServletRequestUtil.getString(request, "newPassword");
		// 从session中获取当前用户信息(用户一旦通过微信登录后,使能获得到用户信息)
		PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
		// 非空判断,要求账号新旧密码以及当前的用户session非空,且新旧密码不相同
		if (username != null && password != null && newPassword != null && user != null && user.getUserId() != null
				&& !password.equals(newPassword)) {
			try {
				// 查看原账号,验证输入的账号是否一致,不一致则认为是非法操作
				LocalAuth localAuth = localAuthService.getLocalAuthByUserId(user.getUserId());
				if (localAuth == null || !localAuth.getUsername().equals(username)) {
					// 不一致则直接退出
					modelMap.put("success", false);
					modelMap.put("errMsg", "输入的账号非本次登录的账号");
					return modelMap;
				}
				// 修改平台账号的用户密码
				LocalAuthExecution lae = localAuthService.modifyLocalAuth(user.getUserId(), username, password,
						newPassword);
				if (lae.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", lae.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入密码");
		}
		return modelMap;
	}

	/**
	 * 登录校验
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logincheck", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> loginCheck(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 获取是否需要进行验证码校验的标识符
		boolean needVerify = HttpServletRequestUtil.getBoolean(request, "needVerify");
		if (needVerify && !CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		// 获取输入的账号
		String username = HttpServletRequestUtil.getString(request, "username");
		// 获取输入的密码
		String password = HttpServletRequestUtil.getString(request, "password");
		// 非空校验
		if (username != null && password != null) {
			// 传入账号和密码,获取平台账号信息
			LocalAuth localAuth = localAuthService.getLocalAuthByUsernameAndPwd(username, password);
			if (localAuth != null) {
				// 若能获取到账号信息则登录成功
				modelMap.put("success", true);
				// 同时在session里设置用户信息
				request.getSession().setAttribute("user", localAuth.getPersonInfo());
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "用户名或密码错误");
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户名或密码均不能为空!");
		}
		return modelMap;
	}

	/**
	 * 当用户点击登出按钮的时候注销session
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> logout(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//将用户session置为空
		request.getSession().setAttribute("user", null);
		modelMap.put("success", true);
		return modelMap;
	}
}
