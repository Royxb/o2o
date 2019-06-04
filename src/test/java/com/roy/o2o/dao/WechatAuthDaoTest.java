package com.roy.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.roy.o2o.BaseTest;
import com.roy.o2o.entity.PersonInfo;
import com.roy.o2o.entity.WechatAuth;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WechatAuthDaoTest extends BaseTest {

	@Autowired
	private WechatAuthDao wechatAuthDao;
	
	@Test
	public void TestAInsertWechatAuth() throws Exception {
		//新增一个微信账户
		WechatAuth wechatAuth = new WechatAuth();
		PersonInfo personInfo = new PersonInfo();
		personInfo.setUserId(1L);
		//给微信账号绑定上用户信息
		wechatAuth.setPersonInfo(personInfo);
		//随意设置上openId
		wechatAuth.setOpenId("qazwsxedcrfvtgb");
		wechatAuth.setCreateTime(new Date());
		int effectedNum = wechatAuthDao.insertWechatAuth(wechatAuth);
		assertEquals(1, effectedNum);
	}
	
	@Test
	public void TestBQueryWechatInfoByOpenId() throws Exception {
		String openId = "qazwsxedcrfvtgb";
		WechatAuth wechatAuth = wechatAuthDao.queryWechatInfoByOpenId(openId);
		assertEquals("Roy", wechatAuth.getPersonInfo().getName());
	}
}
