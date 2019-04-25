package com.roy.o2o.enums;

public enum ProductCategoryStateEnum {
	SUCCESS(1, "创建成功"), 
	INNER_ERROR(-1001, "操作失败"), 
	NULL_SHOP(-2001, "Shop信息为空"), 
	EMPETY_LIST(-2002, "请输入商品目录信息"), 
	DELETE_ERROR(-2003, "商品类别删除失败"),
	EDIT_ERROR(-2004, "商品类别编辑失败");

	private int state;
	private String stateInfo;

	private ProductCategoryStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public static ProductCategoryStateEnum stateOf(int state) {
		for (ProductCategoryStateEnum stateEnum : values()) {
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
