package com.wanhuhealth.rules.historic.model;

import com.google.common.collect.Lists;
import com.wanhuhealth.rules.utils.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/5/31.
 */
public class CfgOrderDrugRules extends BaseEntity implements Serializable{
	private  Long id;
	private String ruleName;
	private String ruleMark;
	private String rule;
	private Integer version;
	private Integer onOff;
	private String group;
	private List<CfgRegionRuleSystemCategory> hasRulesRegionCategory = Lists.newArrayList();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleMark() {
		return ruleMark;
	}

	public void setRuleMark(String ruleMark) {
		this.ruleMark = ruleMark;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getOnOff() {
		return onOff;
	}

	public void setOnOff(Integer onOff) {
		this.onOff = onOff;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public List<CfgRegionRuleSystemCategory> getHasRulesRegionCategory() {
		return hasRulesRegionCategory;
	}

	public void setHasRulesRegionCategory(List<CfgRegionRuleSystemCategory> hasRulesRegionCategory) {
		this.hasRulesRegionCategory = hasRulesRegionCategory;
	}
}
