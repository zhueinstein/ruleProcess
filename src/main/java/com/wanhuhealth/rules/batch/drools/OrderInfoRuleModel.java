package com.wanhuhealth.rules.batch.drools;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by admin on 2017/1/10.
 * 当前处方: { 来源系统编号, 疾病 [ ID ], 疗程, 取药点, 药品: [ ID ] }

 患者: { 年龄, 性别, 地区, 支付方式, 是否门慢, 疾病 [ ID ],  处方外用药 [ ID ], 身高, 体重 }

 药品: { ID, 购买量, 单次用量, 频次, 规格, 剂型, 适应症 [ ID ], 推荐用量, 推荐单次剂量, 推荐频次, 单次使用最大量, 日使用最大量, 单次最大剂量, 日最大剂量, 通用名, 商品名, 药品分类-大类, 药品分类-中类, 药品分类-小类, 药理学分类, 历史药品余量 }

 历史处方: [ { ID, 状态 } ]

 历史用药: [ ID ]

 历史疾病: [ ID ]

 */
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderInfoRuleModel extends ResResult{
	private Integer sysCategory; // 来源系统编号
	private Integer innerSysCategory; // 系统内部编号
	private List<DiseaseInfoRuleModel> diseaseInfoRuleModels = Lists.newArrayList(); // 疾病
	private List<String> diseaseInfoList = Lists.newArrayList(); // 疾病id
	private Integer cycle; // 疗程
	private String doctorHospital; // 取药点
	private List<DrugInfoRuleModel> drugInfoList = Lists.newArrayList(); // 药品
	private List<Long> hisDrugIds = Lists.newArrayList(); // 历史用药
	private List<String> hisDiseases = Lists.newArrayList(); // 历史疾病
	private PatientInfoRuleModel patientInfoRuleModel; // 当前病人信息
	private String ruleMark;

	public Integer getSysCategory() {
		return sysCategory;
	}

	public void setSysCategory(Integer sysCategory) {
		this.sysCategory = sysCategory;
	}

	public List<DiseaseInfoRuleModel> getDiseaseInfoRuleModels() {
		return diseaseInfoRuleModels;
	}

	public void setDiseaseInfoRuleModels(List<DiseaseInfoRuleModel> diseaseInfoRuleModels) {
		this.diseaseInfoRuleModels = diseaseInfoRuleModels;
	}

	public List<String> getDiseaseInfoList() {
		return diseaseInfoList;
	}

	public void setDiseaseInfoList(List<String> diseaseInfoList) {
		this.diseaseInfoList = diseaseInfoList;
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

	public List<DrugInfoRuleModel> getDrugInfoList() {
		return drugInfoList;
	}

	public void setDrugInfoList(List<DrugInfoRuleModel> drugInfoList) {
		this.drugInfoList = drugInfoList;
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

	public PatientInfoRuleModel getPatientInfoRuleModel() {
		return patientInfoRuleModel;
	}

	public void setPatientInfoRuleModel(PatientInfoRuleModel patientInfoRuleModel) {
		this.patientInfoRuleModel = patientInfoRuleModel;
	}


	public Integer getInnerSysCategory() {
		return innerSysCategory;
	}

	public void setInnerSysCategory(Integer innerSysCategory) {
		this.innerSysCategory = innerSysCategory;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer buff = new StringBuffer();
		buff.append("-----OrderInfoRuleModel-----\n");
		/*buff.append("系统来源  = " + JSON.toJSONString(this.getSysCategory()) + "\n");
		buff.append("当前数据模用药周期 = "  + JSON.toJSONString(this.getCycle()) + "\n");
		buff.append("当前数据模型病人信息 = "  + JSON.toJSONString(this.getPatientInfoRuleModel()) + "\n");
		buff.append("当前数据模型药品信息 = "  + JSON.toJSONString(this.getDrugInfoList()) + "\n");
		buff.append("当前数据模型药品（无最大购买量和最大说明书量）信息 = "  + JSON.toJSONString(this.getDrugInfoWithoutMaxCountList()) + "\n");
		buff.append("当前数据模型历史订单信息 = "  + JSON.toJSONString(this.getHisOrderInfoRuleModels()) + "\n");
		buff.append("当前数据模型疾病信息 = "  + JSON.toJSONString(this.getDiseaseInfoRuleModels()) + "\n");
		buff.append("当前数据模型历史疾病信息 = "  + JSON.toJSONString(this.getHisDiseases()) + "\n");
		buff.append("当前数据模型取药点信息 = "  + JSON.toJSONString(this.getDoctorHospital()) + "\n");*/
		buff.append(JSON.toJSONString(this) + "\n");
		buff.append("-----OrderInfoRuleModel end--");
		return buff.toString();
	}

	public static ArrayList<DrugInfoRuleModel> removeZero(ArrayList $dupList) {

		Collections.sort($dupList, new Comparator<DrugInfoRuleModel>(){
			public int compare(DrugInfoRuleModel arg0, DrugInfoRuleModel arg1) {
				Double d0 = arg0.getBuyAmount() * arg0.getPrice();
				Double d1 = arg1.getBuyAmount() * arg1.getPrice();
				return d0.compareTo(d1);
			}
		});
		CopyOnWriteArrayList<DrugInfoRuleModel> arrayList = new CopyOnWriteArrayList<DrugInfoRuleModel>($dupList);
		for (DrugInfoRuleModel model : arrayList) {
			if(model.getBuyAmount() == 0.0){
				arrayList.remove(model);
			}
		}
		return Lists.newArrayList(arrayList);
	}
}
