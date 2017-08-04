package com.wanhuhealth.rules.historic.model;

import com.wanhuhealth.rules.utils.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/5/31.
 */
public class CfgRegionRuleSystemCategory extends BaseEntity implements Serializable {
	private Long id;
	private String provinceId;
	private String cityId;
	private String areaId ;
	private Integer onOff;
	private Long ruleId;
	private List<CfgRegionSystemCategory> hasRegionSystemCategories;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Integer getOnOff() {
		return onOff;
	}

	public void setOnOff(Integer onOff) {
		this.onOff = onOff;
	}

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public List<CfgRegionSystemCategory> getHasRegionSystemCategories() {
		return hasRegionSystemCategories;
	}

	public void setHasRegionSystemCategories(List<CfgRegionSystemCategory> hasRegionSystemCategories) {
		this.hasRegionSystemCategories = hasRegionSystemCategories;
	}
}
