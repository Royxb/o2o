package com.roy.o2o.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.roy.o2o.dao.LocalAuthDao;
import com.roy.o2o.dto.LocalAuthExecution;
import com.roy.o2o.entity.LocalAuth;
import com.roy.o2o.exceptions.LocalAuthOperationException;
import com.roy.o2o.service.LocalAuthService;

public class LocalAuthServiceImpl implements LocalAuthService {

	private static Logger log = LoggerFactory.getLogger(LocalAuthServiceImpl.class);
	
	@Autowired
	private LocalAuthDao localAuthDao;
	@Override
	public LocalAuth getLocalAuthByUsernameAndPwd(String username, String password) {
		// TODO Auto-generated method stub
		return localAuthDao.queryLocalByUsernameAndPwd(username, password);
	}

	@Override
	public LocalAuth getLocalAuthByUserId(long userId) {
		// TODO Auto-generated method stub
		return localAuthDao.queryLocalByUserId(userId);
	}

	@Override
	@Transactional
	public LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws LocalAuthOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public LocalAuthExecution modifyLocalAuth(Long userId, String username, String password, String newPassword)
			throws LocalAuthOperationException {
		// TODO Auto-generated method stub
		return null;
	}

}
