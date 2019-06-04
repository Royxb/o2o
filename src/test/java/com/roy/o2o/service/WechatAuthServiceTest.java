package com.roy.o2o.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.roy.o2o.BaseTest;
import com.roy.o2o.dto.WechatAuthExecution;
import com.roy.o2o.entity.PersonInfo;
import com.roy.o2o.entity.WechatAuth;
import com.roy.o2o.enums.WechatAuthStateEnum;
import com.roy.o2o.exceptions.WechatAuthOperationException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WechatAuthServiceTest extends BaseTest {

	@Autowired
	private WechatAuthService wechatAuthService;
	
	@Test
	public void TestARegister() throws WechatAuthOperationException {
		//新增一条微信账号
		WechatAuth wechatAuth = new WechatAuth();
		PersonInfo personInfo = new PersonInfo();
		String openId = "dafahizhfdhaih";
		//给微信账号设置上用户信息，但不设置上用户Id
		//希望创建微信账号的时候自动创建用户信息
		personInfo.setCreateTime(new Date());
		personInfo.setName("测试");
		personInfo.setUserType(1);
		wechatAuth.setPersonInfo(personInfo);
		wechatAuth.setOpenId(openId);
		wechatAuth.setCreateTime(new Date());
		WechatAuthExecution wae = wechatAuthService.register(wechatAuth);
		assertEquals(WechatAuthStateEnum.SUCCESS.getState(),wae.getState());
	}
	
	@Test
	public void TestBGetWechatAuthByOpenId() {
		WechatAuth wechatAuth = new WechatAuth();
		String openId = "dafahizhfdhaih";
		//通过openId找到新增的wechatAuth
		wechatAuth = wechatAuthService.getWechatAuthByOpenId(openId);
		//打印用户名字看看根于其是否相符
		System.out.println(wechatAuth.getPersonInfo().getName());
		
	}
}
