package com.roy.o2o.service;

import java.util.List;

import com.roy.o2o.entity.Area;

/**
 * 
 * @author Haier
 *
 */
public interface AreaService {
	
	public static final String AREALISTKEY = "arealist";
	List<Area> getAreaList();
}
