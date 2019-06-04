package com.roy.o2o.dto;

import java.util.List;

import com.roy.o2o.entity.PersonInfo;
import com.roy.o2o.enums.PersonInfoStateEnum;

public class PersonInfoExecution {
	// 结果状态
	private int state;

	// 状态标识
	private String stateInfo;

	// 数量
	private int count;
	
	//微信信息
	private PersonInfo personInfo;
	
	//微信信息列表
	private List<PersonInfo> personInfoList;
	

	
	//操作失败的构造器
	public PersonInfoExecution(PersonInfoStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	
	//操作成功的构造器
	public PersonInfoExecution(PersonInfoStateEnum stateEnum,PersonInfo personInfo) {
		this.state = stateEnum.getState();
		this.personInfo = personInfo;
		this.stateInfo = stateEnum.getStateInfo();
	}
	
	//操作成功的构造器
	public PersonInfoExecution(PersonInfoStateEnum stateEnum,List<PersonInfo> personInfoList) {
		this.state = stateEnum.getState();
		this.personInfoList = personInfoList;
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

	public PersonInfo getPersonInfo() {
		return personInfo;
	}

	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}

	public List<PersonInfo> getPersonInfoList() {
		return personInfoList;
	}

	public void setPersonInfoList(List<PersonInfo> personInfoList) {
		this.personInfoList = personInfoList;
	}

	public PersonInfoExecution() {
		
	}
}
