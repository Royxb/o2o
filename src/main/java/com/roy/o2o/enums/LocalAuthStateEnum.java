package com.roy.o2o.enums;

public enum LocalAuthStateEnum {
	LOGINFAIL(-1,"openId输入有误"),
	SUCCESS(1,"操作成功"),
	PASS(2,"通过认证"),
	INNER_ERROR(-1001,"内部系统错误"),
	NULL_AUTH_INFO_ID(-5002,"localAuthId为空"),
	NULL_AUTH_INFO(-5003,"localAuth信息为空"), 
	ONLY_ONE_ACCOUNT(-5004,"wechatAuth账号已绑定");
	
	private int state;
	private String stateInfo;
	
	private LocalAuthStateEnum(int state,String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	
	public static LocalAuthStateEnum stateOf(int state) {
		for (LocalAuthStateEnum stateEnum : values()) {
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
