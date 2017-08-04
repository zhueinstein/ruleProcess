package com.wanhuhealth.rules.historic.fact;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by admin on 2017/5/31.
 */
public class OrderFact extends BaseFact{
	private String id;
	private Integer status;
	private List<DiseaseFact> diseaseFacts = Lists.newArrayList(); // 疾病
	private Integer cycle; // 疗程
	private String doctorHospital; // 取药点
	private List<DrugFact> drugFacts = Lists.newArrayList(); // 药品
	private List<OrderFact> hisOrderFacts = Lists.newArrayList(); // 历史订单
	private List<Long> hisDrugIds = Lists.newArrayList(); // 历史用药
	private List<String> hisDiseases = Lists.newArrayList(); // 历史疾病
	private PatientFact patientInfoRuleModel; // 当前病人信息

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<DiseaseFact> getDiseaseFacts() {
		return diseaseFacts;
	}

	public void setDiseaseFacts(List<DiseaseFact> diseaseFacts) {
		this.diseaseFacts = diseaseFacts;
	}

	public Integer getCycle() {
		return cycle;
	}

	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}

	public String getDoctorHospital() {
		return doctorHospital;
	}

	public void setDoctorHospital(String doctorHospital) {
		this.doctorHospital = doctorHospital;
	}

	public List<DrugFact> getDrugFacts() {
		return drugFacts;
	}

	public void setDrugFacts(List<DrugFact> drugFacts) {
		this.drugFacts = drugFacts;
	}

	public List<OrderFact> getHisOrderFacts() {
		return hisOrderFacts;
	}

	public void setHisOrderFacts(List<OrderFact> hisOrderFacts) {
		this.hisOrderFacts = hisOrderFacts;
	}

	public List<Long> getHisDrugIds() {
		return hisDrugIds;
	}

	public void setHisDrugIds(List<Long> hisDrugIds) {
		this.hisDrugIds = hisDrugIds;
	}

	public List<String> getHisDiseases() {
		return hisDiseases;
	}

	public void setHisDiseases(List<String> hisDiseases) {
		this.hisDiseases = hisDiseases;
	}

	public PatientFact getPatientInfoRuleModel() {
		return patientInfoRuleModel;
	}

	public void setPatientInfoRuleModel(PatientFact patientInfoRuleModel) {
		this.patientInfoRuleModel = patientInfoRuleModel;
	}
}
