package com.roy.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.roy.o2o.BaseTest;
import com.roy.o2o.entity.LocalAuth;
import com.roy.o2o.entity.PersonInfo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalAuthDaoTest extends BaseTest {
	@Autowired
	private LocalAuthDao localAuthDao;
	private static final String username = "testRoy";
	private static final String password = "11111111";
	
	@Test
	public void TestAInsertLocalAuth() {
		//新增一条平台账号信息
		LocalAuth localAuth = new LocalAuth();
		PersonInfo personInfo = new PersonInfo();
		personInfo.setUserId(1L);
		//给平台账号绑定上用户信息
		localAuth.setPersonInfo(personInfo);
		localAuth.setUsername(username);
		localAuth.setPassword(password);
		localAuth.setCreateTime(new Date());
		localAuth.setLastEditTime(new Date());
		int effectedNum = localAuthDao.insertLocalAuth(localAuth);
		assertEquals(1, effectedNum);
	}
	
	@Test
	public void TestBQueryLocalByUserNameAndPwd() {
		//查询Id为1的用户信息
		LocalAuth localAuth = localAuthDao.queryLocalByUsernameAndPwd("Roy", "11111111");
		assertEquals("Roy", localAuth.getUsername());
	}
	
	@Test
	public void TestCQueryLocalByUserId() {
		Long userId = 2L;
		//查询Id为1的用户信息
		LocalAuth localAuth = localAuthDao.queryLocalByUserId(userId);
		assertEquals("testRoy", localAuth.getUsername());
	}
	
	@Test
	public void TestDUpdateLocalAuth() {
		Date now = new Date();

		int effectedNum = localAuthDao.updateLocalAuth(2L, username, password, password+"new", now);
		
		//查询出该条平台账号的最新信息
		LocalAuth localAuth = localAuthDao.queryLocalByUserId(1L);
		System.out.println(localAuth.getPassword());
	}
}
