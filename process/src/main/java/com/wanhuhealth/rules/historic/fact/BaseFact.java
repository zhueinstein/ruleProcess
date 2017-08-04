package com.wanhuhealth.rules.historic.fact;

import com.wanhuhealth.rules.historic.globals.ResResult;

/**
 * 规则基础数据
 * Created by admin on 2017/5/31.
 */
public class BaseFact extends ResResult{
	private Integer sysCategory; // 来源系统编号
	private Integer innerSysCategory; // 系统内部编号
	private String provinceId; // 省
	private String cityId;	   // 市
	private String areaId;     // 区

	public Integer getSysCategory() {
		return sysCategory;
	}

	public void setSysCategory(Integer sysCategory) {
		this.sysCategory = sysCategory;
	}

	public Integer getInnerSysCategory() {
		return innerSysCategory;
	}

	public void setInnerSysCategory(Integer innerSysCategory) {
		this.innerSysCategory = innerSysCategory;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
}
