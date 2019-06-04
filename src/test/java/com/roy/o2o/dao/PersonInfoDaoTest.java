package com.roy.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.roy.o2o.BaseTest;
import com.roy.o2o.entity.PersonInfo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersonInfoDaoTest extends BaseTest {
	@Autowired
	PersonInfoDao personInfoDao;
	
	@Test
	public void TestAInsertPersonInfo() {
		//设置新增的用户信息
		PersonInfo personInfo = new PersonInfo();
		personInfo.setName("Roy");
		personInfo.setGender("男");
		personInfo.setUserType(1);
		personInfo.setCreateTime(new Date());
		personInfo.setLastEditTime(new Date());
		personInfo.setEnableStatus(1);
		int effectedNum = personInfoDao.insertPersonInfo(personInfo);
		assertEquals(1, effectedNum);
	}
	
	@Test
	public void TestBQueryPersonInfoById() {
		long userId = 2;
		//查询Id为1的用户信息
		PersonInfo personInfo = personInfoDao.queryPersonInfoById(userId);
		System.out.println(personInfo.getName());
	}
}
