package com.wanhuhealth.rules.utils;

import java.util.Date;

/**
 * Created by admin on 2017/5/31.
 */
public class BaseEntity {
	/**
	 * 创建者
	 */
	private String createBy;

	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 更新者
	 */
	private String updateBy;

	/**
	 * 更新时间
	 */
	private Date updateDate;

	/**
	 * 备注
	 */
	private String remarks;

	/**
	 * 删除标记
	 */
	private Integer delFlag;

	// 删除标记（0：正常；1：删除；2：审核；）
	public static final String FIELD_DEL_FLAG = "delFlag";
	public static final Integer DEL_FLAG_NORMAL = 0;
	public static final Integer DEL_FLAG_DELETE = 1;
	public static final Integer DEL_FLAG_AUDIT = 2;

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
}
