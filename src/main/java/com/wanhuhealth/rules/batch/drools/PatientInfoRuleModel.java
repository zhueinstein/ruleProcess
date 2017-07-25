package com.wanhuhealth.rules.batch.drools;

/**
 * Created by admin on 2017/1/10.
 * 患者: { 年龄, 性别, 地区, 支付方式, 是否门慢, 疾病 [ ID ],  处方外用药 [ ID ], 身高, 体重 }
 */
public class PatientInfoRuleModel {
	private String patientId; // id
	private Integer age; // 年龄
	private Integer gender; // 性别
	private Float height; // 身高(cm)
	private Float weight; // 体重(g)

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Float getHeight() {
		return height;
	}

	public void setHeight(Float height) {
		this.height = height;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}
}
