package com.roy.o2o.entity;
/**
 * 用户信息类
 * @author Haier
 *
 */

import java.util.Date;

public class PersonInfo {
	//用户ID
	private Long userId;
	//用户名称
	private String name;
	//用户头像图片地址
	private String profileImg;
	//用户电子邮箱
	private String email;
	//用户性别
	private Integer gender;
	//用户状态
	private Integer enableStatus;
	//用户身份标识：	1.顾客	2.店家	3.超级管理员
	private Integer userType;
	//创建时间
	private Date createTime;
	//更新时间
	private Date lastEditTime;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProfileImg() {
		return profileImg;
	}
	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Integer getEnableStatus() {
		return enableStatus;
	}
	public void setEnableStatus(Integer enableStatus) {
		this.enableStatus = enableStatus;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
	
}
