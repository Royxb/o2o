package com.roy.o2o.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roy.o2o.cache.JedisUtil;
import com.roy.o2o.dao.AreaDao;
import com.roy.o2o.entity.Area;
import com.roy.o2o.exceptions.AreaOperationException;
import com.roy.o2o.service.AreaService;


@Service
public class AreaServiceImpl implements AreaService {

	private static Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);
	@Autowired
	private AreaDao areaDao;
	@Autowired
	private JedisUtil.Keys jedisKeys;
	@Autowired
	private JedisUtil.Strings jedisStrings;
	
	@Override
	public List<Area> getAreaList() {
		String key = AREALISTKEY;
		List<Area> areaList = null;
		ObjectMapper mapper = new ObjectMapper();
		//判断key是否存在
		if (!jedisKeys.exists(key)) {
			areaList = areaDao.queryArea();
			String jsonString;
			try {
				jsonString = mapper.writeValueAsString(areaList);
			} catch (JsonProcessingException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
				throw new AreaOperationException(e.getMessage());
			}
			jedisStrings.set(key, jsonString);
		} else {
			String jsonString = jedisStrings.get(key);
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
			try {
				areaList = mapper.readValue(jsonString, javaType);
			} catch (JsonParseException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
				throw new AreaOperationException(e.getMessage());
			} catch (JsonMappingException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
				throw new AreaOperationException(e.getMessage());
			} catch (IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
				throw new AreaOperationException(e.getMessage());
			}
		}
		return areaList;
	}

}
