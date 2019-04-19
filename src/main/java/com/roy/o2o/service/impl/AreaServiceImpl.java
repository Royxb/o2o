package com.roy.o2o.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roy.o2o.dao.AreaDao;
import com.roy.o2o.entity.Area;
import com.roy.o2o.service.AreaService;


@Service
public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaDao areaDao;
	
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);
	
	@Override
	public List<Area> getAreaList() {
		return areaDao.queryArea();
	}

}
