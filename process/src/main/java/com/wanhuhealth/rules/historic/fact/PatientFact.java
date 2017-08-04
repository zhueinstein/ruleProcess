package com.wanhuhealth.rules.historic.fact;

import java.util.List;

/**
 * Created by admin on 2017/5/31.
 */
public class PatientFact {
	private String patientId; // id
	private Integer age; // 年龄
	private Integer gender; // 性别
	private Integer paymentType; // 支付方式
	private Byte hasChronicDiseaseCard; //门慢卡
	private List<DiseaseFact> diseaseFacts; // 疾病
	private List<DrugFact> drugFacts; // 处方外用药
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

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public Byte getHasChronicDiseaseCard() {
		return hasChronicDiseaseCard;
	}

	public void setHasChronicDiseaseCard(Byte hasChronicDiseaseCard) {
		this.hasChronicDiseaseCard = hasChronicDiseaseCard;
	}

	public List<DiseaseFact> getDiseaseFacts() {
		return diseaseFacts;
	}

	public void setDiseaseFacts(List<DiseaseFact> diseaseFacts) {
		this.diseaseFacts = diseaseFacts;
	}

	public List<DrugFact> getDrugFacts() {
		return drugFacts;
	}

	public void setDrugFacts(List<DrugFact> drugFacts) {
		this.drugFacts = drugFacts;
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
