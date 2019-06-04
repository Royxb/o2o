package com.roy.o2o.enums;

public enum PersonInfoStateEnum {
	LOGINFAIL(-1,"openId输入有误"),
	SUCCESS(1,"操作成功"),
	PASS(2,"通过认证"),
	INNER_ERROR(-1001,"内部系统错误"),
	NULL_PERSONINFOID(-4002,"personInfoId为空"),
	NULL_PERSONINFO(-4003,"personInfo信息为空");
	
	private int state;
	private String stateInfo;
	
	private PersonInfoStateEnum(int state,String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	
	public static PersonInfoStateEnum stateOf(int state) {
		for (PersonInfoStateEnum stateEnum : values()) {
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
