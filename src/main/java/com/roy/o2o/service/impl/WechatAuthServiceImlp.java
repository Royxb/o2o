package com.roy.o2o.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.roy.o2o.dao.PersonInfoDao;
import com.roy.o2o.dao.WechatAuthDao;
import com.roy.o2o.dto.WechatAuthExecution;
import com.roy.o2o.entity.PersonInfo;
import com.roy.o2o.entity.WechatAuth;
import com.roy.o2o.enums.WechatAuthStateEnum;
import com.roy.o2o.exceptions.WechatAuthOperationException;
import com.roy.o2o.service.WechatAuthService;

@Service
public class WechatAuthServiceImlp implements WechatAuthService {

	@Autowired
	private WechatAuthDao wechatAuthDao;

	@Autowired
	private PersonInfoDao personInfoDao;

	private static Logger logger = LoggerFactory.getLogger(WechatAuthServiceImlp.class);

	@Override
	public WechatAuth getWechatAuthByOpenId(String openId) {
		return wechatAuthDao.queryWechatInfoByOpenId(openId);
	}

	@Override
	@Transactional
	public WechatAuthExecution register(WechatAuth wechatAuth) throws WechatAuthOperationException {
		// 空值判断
		if (wechatAuth == null) {
			return new WechatAuthExecution(WechatAuthStateEnum.NULL_WECHATAUTH);
		}
		try {
			//设置创建时间
			wechatAuth.setCreateTime(new Date());
			//如果微信账号里夹带着用户信息并且用户Id为空，则认为该用户第一次使用平台（且通过微信登录）
			//则自动创建用户信息
			if (wechatAuth.getPersonInfo() != null && wechatAuth.getPersonInfo().getUserId() == null) {
				try {
					wechatAuth.getPersonInfo().setCreateTime(new Date());
					wechatAuth.getPersonInfo().setEnableStatus(1);
					PersonInfo personInfo = wechatAuth.getPersonInfo();
					int effectedNum = personInfoDao.insertPersonInfo(personInfo);
					if (effectedNum <= 0) {
						throw new WechatAuthOperationException("添加用户信息失败");
					} 
					wechatAuth.setPersonInfo(personInfo);
					logger.debug("微信openId:" + wechatAuth.getOpenId());
					logger.debug("微信userId:" + wechatAuth.getPersonInfo().getUserId());
				} catch (Exception e) {
					logger.error("insertPersonInfo error: " + e.toString());
					throw new WechatAuthOperationException("insertPersonInfo error: " + e.getMessage());
				}
			}
			//创建专属于本平台的微信账号
			int effectedNum = wechatAuthDao.insertWechatAuth(wechatAuth);
			if (effectedNum <= 0) {
				throw new WechatAuthOperationException("账号创建失败");
			} else {
				return new WechatAuthExecution(WechatAuthStateEnum.SUCCESS,wechatAuth);
			}
		} catch (Exception e) {
			logger.error("insertWechatAuth error: " + e.toString());
			throw new WechatAuthOperationException("insertWechatAuth error: " + e.getMessage());
		}
	}

}
