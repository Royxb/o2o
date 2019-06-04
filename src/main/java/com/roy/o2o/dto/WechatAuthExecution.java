package com.roy.o2o.dto;

import java.util.List;

import com.roy.o2o.entity.WechatAuth;
import com.roy.o2o.enums.WechatAuthStateEnum;

public class WechatAuthExecution {
	// 结果状态
	private int state;

	// 状态标识
	private String stateInfo;

	// 数量
	private int count;
	
	//微信信息
	private WechatAuth wechatAuth;
	
	//微信信息列表
	private List<WechatAuth> wechatAuthList;
	

	
	//操作失败的构造器
	public WechatAuthExecution(WechatAuthStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	
	//操作成功的构造器
	public WechatAuthExecution(WechatAuthStateEnum stateEnum,WechatAuth wechatAuth) {
		this.state = stateEnum.getState();
		this.wechatAuth = wechatAuth;
		this.stateInfo = stateEnum.getStateInfo();
	}
	
	//操作成功的构造器
	public WechatAuthExecution(WechatAuthStateEnum stateEnum,List<WechatAuth> wechatAuthList) {
		this.state = stateEnum.getState();
		this.wechatAuthList = wechatAuthList;
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

	public WechatAuth getWechatAuth() {
		return wechatAuth;
	}

	public void setWechatAuth(WechatAuth wechatAuth) {
		this.wechatAuth = wechatAuth;
	}

	public List<WechatAuth> getWechatAuthList() {
		return wechatAuthList;
	}

	public void setWechatAuthList(List<WechatAuth> wechatAuthList) {
		this.wechatAuthList = wechatAuthList;
	}

	public WechatAuthExecution() {
		
	}
}
