package com.wanhuhealth.rules.drools;


/**
 * Created by admin on 2017/1/12.
 */
public enum RulesCheckTypeEnum {
	RULE_ORDER("订单校验结果", 0),
	RULE_PATINET("患者检验结果",1),
	RULE_DRUG("药品校验结果",2);

	// 成员变量
	private String name;
	private int index;

	// 构造方法
	private RulesCheckTypeEnum(String name, int index) {
		this.name = name;
		this.index = index;
	}

	// 普通方法
	public static String getName(int index) {
		for (RulesCheckTypeEnum c : RulesCheckTypeEnum.values()) {
			if (c.getIndex() == index) {
				return c.name;
			}
		}
		return null;
	}

	public static RulesCheckTypeEnum get(int index) {
		for (RulesCheckTypeEnum c : RulesCheckTypeEnum.values()) {
			if (c.getIndex() == index) {
				return c;
			}
		}
		return null;
	}

	// get set 方法
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
}
