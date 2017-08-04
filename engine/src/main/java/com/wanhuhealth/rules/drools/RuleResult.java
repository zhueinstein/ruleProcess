package com.wanhuhealth.rules.drools;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by admin on 2017/1/11.
 */
public class RuleResult {

	private String message;
	private Integer sysCategory;
	private Integer ruleCheckObjectType;
	private String ruleMark;
	private Map<String, Object> objectMap = Maps.newHashMap();
	private String level;// 级别 （warning:警告，forbidden: 禁止，ignore:忽略）
	private String markName;
	private Long drugId;
	private Double saveCount = 0.0;
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getSysCategory() {
		return sysCategory;
	}

	public void setSysCategory(Integer sysCategory) {
		this.sysCategory = sysCategory;
	}

	public String getRuleMark() {
		return ruleMark;
	}

	public void setRuleMark(String ruleMark) {
		this.ruleMark = ruleMark;
	}

	public Integer getRuleCheckObjectType() {
		return ruleCheckObjectType;
	}

	public void setRuleCheckObjectType(Integer ruleCheckObjectType) {
		this.ruleCheckObjectType = ruleCheckObjectType;
	}

	public Map<String, Object> getObjectMap() {
		return objectMap;
	}

	public void setObjectMap(Map<String, Object> objectMap) {
		this.objectMap = objectMap;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getMarkName() {
		return markName;
	}

	public void setMarkName(String markName) {
		this.markName = markName;
	}

    public Long getDrugId() {
        return drugId;
    }

    public void setDrugId(Long drugId) {
        this.drugId = drugId;
    }

	public Double getSaveCount() {
		return saveCount;
	}

	public void setSaveCount(Double saveCount) {
		this.saveCount = saveCount;
	}
}
