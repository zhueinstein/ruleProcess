package com.wanhuhealth.rules.historic.globals;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by admin on 2017/5/31.
 */
public class ResResult {
	private Integer code;
	private List<RuleResult> ruleResultList = Lists.newArrayList();
	private String message;
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public List<RuleResult> getRuleResultList() {
		return ruleResultList;
	}

	public void setRuleResultList(List<RuleResult> ruleResultList) {
		this.ruleResultList = ruleResultList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
