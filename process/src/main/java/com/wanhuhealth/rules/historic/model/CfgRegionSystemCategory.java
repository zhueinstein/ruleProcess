package com.wanhuhealth.rules.historic.model;

import com.wanhuhealth.rules.utils.BaseEntity;

import java.io.Serializable;

/**
 * Created by admin on 2017/5/31.
 */
public class CfgRegionSystemCategory extends BaseEntity implements Serializable {
	private Long id;
	private Integer sysCategory;
	private Integer innerCategory;
	private String level;
	private Long regionId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public Integer getSysCategory() {
		return sysCategory;
	}

	public void setSysCategory(Integer sysCategory) {
		this.sysCategory = sysCategory;
	}

	public Integer getInnerCategory() {
		return innerCategory;
	}

	public void setInnerCategory(Integer innerCategory) {
		this.innerCategory = innerCategory;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}
}
