package com.roy.o2o.dto;

import java.util.List;

import com.roy.o2o.entity.LocalAuth;
import com.roy.o2o.enums.LocalAuthStateEnum;

public class LocalAuthExecution {
	// 结果状态
	private int state;

	// 状态标识
	private String stateInfo;

	// 数量
	private int count;
	
	//微信信息
	private LocalAuth localAuth;
	
	//微信信息列表
	private List<LocalAuth> localAuthList;
	
	public LocalAuthExecution() {
		
	}
	
	//操作失败的构造器
	public LocalAuthExecution(LocalAuthStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	
	//操作成功的构造器
	public LocalAuthExecution(LocalAuthStateEnum stateEnum,LocalAuth localAuth) {
		this.state = stateEnum.getState();
		this.localAuth = localAuth;
		this.stateInfo = stateEnum.getStateInfo();
	}
	
	//操作成功的构造器
	public LocalAuthExecution(LocalAuthStateEnum stateEnum,List<LocalAuth> localAuthList) {
		this.state = stateEnum.getState();
		this.localAuthList = localAuthList;
		this.stateInfo = stateEnum.getStateInfo();
	}
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public LocalAuth getLocalAuth() {
		return localAuth;
	}

	public void setLocalAuth(LocalAuth localAuth) {
		this.localAuth = localAuth;
	}

	public List<LocalAuth> getLocalAuthList() {
		return localAuthList;
	}

	public void setLocalAuthList(List<LocalAuth> localAuthList) {
		this.localAuthList = localAuthList;
	}
}
