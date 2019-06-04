package com.roy.o2o.enums;

public enum WechatAuthStateEnum {
	LOGINFAIL(-1,"openId输入有误"),
	SUCCESS(1,"操作成功"),
	PASS(2,"通过认证"),
	INNER_ERROR(-1001,"内部系统错误"),
	NULL_WECHATAUTHID(-4002,"wechatAuthId为空"),
	NULL_WECHATAUTH(-4003,"wechatAuth信息为空");
	
	private int state;
	private String stateInfo;
	
	private WechatAuthStateEnum(int state,String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	
	public static WechatAuthStateEnum stateOf(int state) {
		for (WechatAuthStateEnum stateEnum : values()) {
			if (stateEnum.getState() == state) {
				return stateEnum;
			}
		}
		return null;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}
	
	
}
